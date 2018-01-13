package com.xpleemoon.xmodulable.compiler.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.xpleemoon.xmodulable.annotation.XModule;
import com.xpleemoon.xmodulable.compiler.Constants;
import com.xpleemoon.xmodulable.compiler.exception.ProcessException;
import com.xpleemoon.xmodulable.compiler.utils.Logger;

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
@SupportedOptions(Constants.KEY_XMODULE)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes(Constants.ANNOTATION_TYPE_XMODULE)
public class XModuleProcessor extends AbstractProcessor {
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
    /**
     * 组件模块名
     */
    private String mModuleName;
    private TypeElement mXModuleLoader;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mLogger = new Logger(processingEnvironment.getMessager());
        mElementUtils = processingEnvironment.getElementUtils();
        mFiler = processingEnvironment.getFiler();

        Map<String, String> options = processingEnvironment.getOptions();
        if (options != null && !options.isEmpty()) {
            mModuleName = options.get(Constants.KEY_XMODULE);
        }
        if (mModuleName != null && !mModuleName.isEmpty()) {
            mModuleName = mModuleName.replaceAll("[^0-9a-zA-Z_]+", "");
            mLogger.info("The user has configuration the XModule, it was [" + mModuleName + "]");
        } else {
            mLogger.error("These no XModule, at 'build.gradle', like :\n" +
                    "javaCompileOptions {\n" +
                    "            annotationProcessorOptions {\n" +
                    "                arguments = [\n" +
                    "                        XModule   : moduleName\n" +
                    "                ]\n" +
                    "            }\n" +
                    "        }");
            throw new RuntimeException(Constants.PREFIX_OF_LOGGER + "No XModule, for more information, look at gradle log.");
        }

        mXModuleLoader = mElementUtils.getTypeElement(Constants.XMODULE_LOADER);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set != null && !set.isEmpty()) {
            Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(XModule.class);
            if (elements == null || elements.size() <= 0) {
                return true;
            }

            mLogger.info(String.format("解析注解：%s", XModule.class.getSimpleName()));
            try {
                // key：组件名；value：携带@XModule的Element
                Map<String, Element> moduleElements = new LinkedHashMap<>();
                // 扫描注解@XModule
                for (Element module : elements) {
                    verify(module);
                    moduleElements.put(module.getAnnotation(XModule.class).name(), module);
                }

                // 生成代码
                generateCode(moduleElements);
            } catch (ProcessException e) {
                mLogger.error(e.fillInStackTrace());
            }
            return true;
        }

        return false;
    }

    /**
     * 校验{@code moduleElement}的合法性
     *
     * @param moduleElement 携带@XModule的Element
     * @throws ProcessException
     */
    private void verify(Element moduleElement) throws ProcessException {
        XModule XModule = moduleElement.getAnnotation(XModule.class);
        // 检验XModule注解
        if (XModule == null) {
            throw new ProcessException(
                    String.format("当前的element（%s）未携带%s注解",
                            moduleElement.toString(),
                            XModule.class.getSimpleName()));
        } else if (XModule.name() == null || XModule.name().isEmpty()) {
            throw new ProcessException(String.format("%s的组件名不能为空", moduleElement.toString()));
        }

        // 检验被XModule注解的是否为class
        if (moduleElement.getKind() != ElementKind.CLASS) {
            throw new ProcessException(
                    String.format("%s不是类，只有类才能使用%s",
                            moduleElement.toString(),
                            XModule.class.getSimpleName()));
        }

        TypeElement classElement = (TypeElement) moduleElement;

        // 检验类修饰符
        Set<Modifier> modifiers = classElement.getModifiers();
        if (!modifiers.contains(Modifier.PUBLIC)) {
            throw new ProcessException(
                    String.format("被%s注解的%s的权限修饰符必须为public",
                            XModule.class.getSimpleName(),
                            classElement.getQualifiedName().toString()));
        }
        if (modifiers.contains(Modifier.ABSTRACT)) {
            throw new ProcessException(
                    String.format("%s是抽象类，不能被%s注解",
                            classElement.getQualifiedName().toString(),
                            XModule.class.getSimpleName()));
        }
    }

    /**
     * 生成源码，主要由三个步骤
     * <ol>
     * <li>创建方法</li>
     * <li>创建类</li>
     * <li>输出源文件</li>
     * </ol>
     *
     * @param moduleElements key：组件名；value：携带@XModule的Element
     */
    private void generateCode(Map<String, Element> moduleElements) throws ProcessException {
        if (moduleElements == null || moduleElements.isEmpty()) {
            return;
        }

        /*
        1、创建方法
        public void loadInto(XModulableOptions options) {
               if (options == null) {
                   return;
               }
               options.addModule(, );
         }
         */
        MethodSpec.Builder loadIntoMethod = MethodSpec.methodBuilder(Constants.METHOD_OF_LOAD_INTO)
                .addJavadoc("向$N添加组件", Constants.PARAMETER_OF_XMODULABLE_OPTIONS)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ClassName.get(mElementUtils.getTypeElement(Constants.XMODULABLE_OPTIONS)), Constants.PARAMETER_OF_XMODULABLE_OPTIONS)
                .returns(void.class)
                .beginControlFlow("if ($N == null)", Constants.PARAMETER_OF_XMODULABLE_OPTIONS)
                .addStatement("return")
                .endControlFlow();
        for (Map.Entry<String, Element> entry : moduleElements.entrySet()) {
            String moduleName = entry.getKey();
            TypeName moduleTypeName = TypeName.get(entry.getValue().asType()); // 注解的宿主类型名
            loadIntoMethod.addStatement("$N.addModule($S, new $T())", Constants.PARAMETER_OF_XMODULABLE_OPTIONS, moduleName, moduleTypeName);
        }
        mLogger.info(String.format("创建方法：%s", Constants.METHOD_OF_LOAD_INTO));

        /*
        2、创建类
        public final class XModule$$Loader$$组件名 implements XModuleLoader
         */
        String className = new StringBuilder()
                .append(Constants.SDK_NAME)
                .append(Constants.SEPARATOR_OF_CLASS_NAME)
                .append(Constants.CLASS_OF_LOADER)
                .append(Constants.SEPARATOR_OF_CLASS_NAME)
                .append(mModuleName)
                .toString();
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
                        mModuleName,
                        XModuleProcessor.class.getSimpleName())
                .addSuperinterface(ClassName.get(mXModuleLoader))
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(loadIntoMethod.build());
        mLogger.info(String.format("创建类：%s", className));

        /*
        3、输出源文件
        XModule$$Loader$$组件名.java
         */
        try {
            JavaFile.builder(Constants.PACKAGE_OF_GENERATE, componentLoader.build())
                    .build().writeTo(mFiler);
            mLogger.info(String.format("输出源文件：%s", Constants.PACKAGE_OF_GENERATE + "." + className + ".java"));
        } catch (IOException e) {
            throw new ProcessException(e.fillInStackTrace());
        }
    }
}
