package com.xpleemoon.component.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.xpleemoon.component.annotations.Component;
import com.xpleemoon.component.compiler.exception.ProcessException;
import com.xpleemoon.component.compiler.utils.Logger;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * 组件注解处理器
 *
 * @author xpleemoon
 */
@AutoService(Processor.class)
@SupportedOptions(Constants.KEY_COMPONENT_NAME)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes(Constants.ANNOTATION_TYPE_COMPONENT)
public class ComponentProcessor extends AbstractProcessor {
    private Map<String, Element> mComponents = new LinkedHashMap<>();
    /**
     * 可用于编译时，信息打印
     */
    private Logger mLogger;
    /**
     * 用于{@link Element}处理的工具类
     */
    private Elements mElementUtils;
    /**
     * 用于文件创建
     */
    private Filer mFiler;
    private String mComponentName;
    private TypeElement mIComponentLoader;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mLogger = new Logger(processingEnvironment.getMessager());
        mElementUtils = processingEnvironment.getElementUtils();
        mFiler = processingEnvironment.getFiler();

        Map<String, String> options = processingEnvironment.getOptions();
        if (options != null && !options.isEmpty()) {
            mComponentName = options.get(Constants.KEY_COMPONENT_NAME);
        }
        if (mComponentName != null && !mComponentName.isEmpty()) {
            mComponentName = mComponentName.replaceAll("[^0-9a-zA-Z_]+", "");
            mLogger.info("The user has configuration the component name, it was [" + mComponentName + "]");
        } else {
            mLogger.error("These no component name, at 'build.gradle', like :\n" +
                    "apt {\n" +
                    "    arguments {\n" +
                    "        componentName project.getName();\n" +
                    "    }\n" +
                    "}\n");
            throw new RuntimeException(Constants.PREFIX_OF_LOGGER + "No component name, for more information, look at gradle log.");
        }

        mIComponentLoader = mElementUtils.getTypeElement(Constants.ICOMPONENT_LOADER);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set != null && !set.isEmpty()) {
            Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Component.class);
            if (elements == null || elements.size() <= 0) {
                return true;
            }

            mLogger.info(String.format("解析注解：%s", Component.class.getSimpleName()));
            try {
                // 扫描注解Component
                for (Element componentElement : elements) {
                    verify(componentElement);
                    mComponents.put(componentElement.getAnnotation(Component.class).name(), componentElement);
                }

                // 生成代码
                generateCode();
            } catch (ProcessException e) {
                mLogger.error(e.fillInStackTrace());
            }
            return true;
        }

        return false;
    }

    private void verify(Element element) throws ProcessException {
        Component component = element.getAnnotation(Component.class);
        // 检验Component注解
        if (component == null) {
            throw new ProcessException(String.format("当前的element（%s）未携带%s注解", element.toString(), Component.class.getSimpleName()));
        } else if (component.name() == null || component.name().isEmpty()) {
            throw new ProcessException(String.format("%s的组件名不能为空", element.toString()));
        }

        // 检验被Component注解的是否为class
        if (element.getKind() != ElementKind.CLASS) {
            throw new ProcessException(String.format("%s不是类，只有类才能使用%s", element.toString(), Component.class.getSimpleName()));
        }

        TypeElement classElement = (TypeElement) element;

        // 检验类修饰符
        Set<Modifier> modifiers = classElement.getModifiers();
        if (!modifiers.contains(Modifier.PUBLIC)) {
            throw new ProcessException(String.format("被%s注解的%s的权限修饰符必须为public", Component.class.getSimpleName(), classElement.getQualifiedName().toString()));
        }
        if (modifiers.contains(Modifier.ABSTRACT)) {
            throw new ProcessException(String.format("%s是抽象类，不能被%s注解", classElement.getQualifiedName().toString(), Component.class.getSimpleName()));
        }
    }

    private void generateCode() throws ProcessException {
        if (mComponents == null || mComponents.isEmpty()) {
            return;
        }

        /* 创建方法对象
         * public void loadInto(ComponentOptions componentOptions) {
         *      if (componentOptions == null) {
         *          return;
         *      }
         *      componentOptions.addComponent(, );
         * }
         */
        mLogger.info(String.format("创建方法：%s", Constants.METHOD_LOAD_INTO));
        MethodSpec.Builder loadIntoMethod = MethodSpec.methodBuilder(Constants.METHOD_LOAD_INTO)
                .addJavadoc("向$N添加组件", Constants.PARAMETER_OF_COMPONENT_OPTIONS)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ClassName.get(mElementUtils.getTypeElement(Constants.COMPONENT_OPTIONS)), Constants.PARAMETER_OF_COMPONENT_OPTIONS)
                .returns(void.class)
                .beginControlFlow("if ($N == null)", Constants.PARAMETER_OF_COMPONENT_OPTIONS)
                .addStatement("return")
                .endControlFlow();
        for (Map.Entry<String, Element> entry : mComponents.entrySet()) {
            String componentName = entry.getKey();
            TypeName componentTypeName = TypeName.get(entry.getValue().asType()); // 注解的宿主类型名
            loadIntoMethod.addStatement("$N.addComponent($S, new $T())", Constants.PARAMETER_OF_COMPONENT_OPTIONS, componentName, componentTypeName);
        }

        // 创建类
        String className = new StringBuilder()
                .append(Constants.SDK_NAME)
                .append(Constants.SEPARATOR_OF_CLASS_NAME)
                .append(Constants.CLASS_NAME_LOADER)
                .append(Constants.SEPARATOR_OF_CLASS_NAME)
                .append(mComponentName)
                .toString();
        mLogger.info(String.format("创建类型：%s", className));
        TypeSpec.Builder componentLoader = TypeSpec.classBuilder(className)
                .addJavadoc(
                        new StringBuilder()
                                .append("$N组件加载器")
                                .append("\n")
                                .append("<ul><li>")
                                .append(Constants.WARNING_TIPS)
                                .append("</li></ul>")
                                .append("\n@author $N")
                                .toString(),
                        mComponentName,
                        ComponentProcessor.class.getSimpleName())
                .addSuperinterface(ClassName.get(mIComponentLoader))
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(loadIntoMethod.build());

        // 输出源文件
        mLogger.info(String.format("输出源文件：%s", className));
        try {
            JavaFile.builder(Constants.PACKAGE_OF_GENERATE, componentLoader.build())
                    .build().writeTo(mFiler);
        } catch (IOException e) {
            throw new ProcessException(e.fillInStackTrace());
        }
    }

//    private void generateCode() throws ProcessException {
//        if (mComponents == null || mComponents.isEmpty()) {
//            return;
//        }
//
//        // 创建方法
//        mLogger.info(String.format("创建方法：%s", Constants.METHOD_INIT));
//        MethodSpec.Builder initMethodBuilder = MethodSpec.methodBuilder(Constants.METHOD_INIT)
//                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
//                .returns(void.class); // 方法签名
//        String varOptionsBuilder = "optionsBuilder";
//        ClassName optionsBuilderClassName = ClassName.get(mElementUtils.getTypeElement(Constants.COMPONENT_OPTIONS_BUILDER));
//        initMethodBuilder.addStatement("$T $N = new $T()", optionsBuilderClassName, varOptionsBuilder, optionsBuilderClassName);
//        for (Map.Entry<String, Element> entry : mComponents.entrySet()) {
//            String componentName = entry.getKey();
//            TypeName componentTypeName = TypeName.get(entry.getValue().asType()); // 获取注解的宿主类型名h
//            initMethodBuilder.addStatement("$N.addComponent($S, new $T())", varOptionsBuilder, componentName, componentTypeName);
//        }
//        ClassName componentManagerClassName = ClassName.get(mElementUtils.getTypeElement(Constants.COMPONENT_MANAGER));
//        initMethodBuilder.addStatement("$T.getInstance().init($N.build())", componentManagerClassName, varOptionsBuilder);
//
//        // 创建类
//        String className = new StringBuilder()
//                .append(Constants.SDK_NAME)
//                .append(Constants.SEPARATOR_OF_CLASS_NAME)
//                .append(mComponentName)
//                .append(Constants.SEPARATOR_OF_CLASS_NAME)
//                .append(Constants.SUFFIX_OF_CLASS_NAME_INITIALIZE)
//                .toString();
//        mLogger.info(String.format("创建类：%s", className));
//        TypeSpec.Builder initializeTypeBuilder = TypeSpec.classBuilder(className)
//                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
//                .addMethod(initMethodBuilder.build());
//
//        // 创建源文件
//        mLogger.info(String.format("创建源文件：%s", className));
//        try {
//            JavaFile.builder(Constants.PACKAGE_OF_GENERATE, initializeTypeBuilder.build())
//                    .build().writeTo(mFiler);
//        } catch (IOException e) {
//            throw new ProcessException(e.fillInStackTrace());
//        }
//    }
}
