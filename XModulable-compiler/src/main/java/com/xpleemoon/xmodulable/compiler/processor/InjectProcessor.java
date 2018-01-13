package com.xpleemoon.xmodulable.compiler.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;
import com.xpleemoon.xmodulable.annotation.InjectXModule;
import com.xpleemoon.xmodulable.compiler.Constants;
import com.xpleemoon.xmodulable.compiler.exception.ProcessException;
import com.xpleemoon.xmodulable.compiler.utils.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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
 * 依赖注入处理器
 *
 * @author xpleemoon
 */
@AutoService(Processor.class)
@SupportedOptions(Constants.KEY_XMODULE)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes(Constants.ANNOTATION_TYPE_INJECT_XMODULE)
public class InjectProcessor extends AbstractProcessor {
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

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mLogger = new Logger(processingEnvironment.getMessager());
        mElementUtils = processingEnvironment.getElementUtils();
        mFiler = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set != null && !set.isEmpty()) {
            Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(InjectXModule.class);
            if (elements == null || elements.size() <= 0) {
                return true;
            }

            mLogger.info(String.format("解析注解：%s", InjectXModule.class.getSimpleName()));
            try {
                // key：依赖注入变量的目标类全限定名；value：目标类中携带@InjectXModule的Element的map集合
                Map<String, List<Element>> targetInjectElements = new LinkedHashMap<>();
                // 扫描注解InjectXModule
                for (Element injectElement : elements) {
                    verify(injectElement);

                    String fullClzName = injectElement.getEnclosingElement().toString();
                    List<Element> injectElements = targetInjectElements.get(fullClzName);
                    if (injectElements == null) {
                        injectElements = new ArrayList<>();
                        targetInjectElements.put(fullClzName, injectElements);
                    }
                    injectElements.add(injectElement);
                }

                // 生成代码
                generateCode(targetInjectElements);
            } catch (ProcessException e) {
                mLogger.error(e.fillInStackTrace());
            }
            return true;
        }
        return false;
    }

    private void verify(Element injectElement) throws ProcessException {
        InjectXModule injectXModule = injectElement.getAnnotation(InjectXModule.class);
        // 检验InjectXModule注解
        if (injectXModule == null) {
            throw new ProcessException(
                    String.format("当前的element（%s）未携带%s注解",
                            injectElement.toString(),
                            InjectXModule.class.getSimpleName()));
        } else if (injectXModule.name() == null || injectXModule.name().isEmpty()) {
            throw new ProcessException(String.format("%s的组件名不能为空", injectElement.toString()));
        }

        // 检验被InjectXModule注解的是否为成员变量
        if (injectElement.getKind() != ElementKind.FIELD) {
            throw new ProcessException(
                    String.format("%s不是类成员变量，只有类才成员变量能使用%s",
                            injectElement.toString(),
                            InjectXModule.class.getSimpleName()));
        }
    }

    private void generateCode(Map<String, List<Element>> targetInjectElements) throws ProcessException {
        if (targetInjectElements == null || targetInjectElements.isEmpty()) {
            return;
        }

        for (Map.Entry<String, List<Element>> entry : targetInjectElements.entrySet()) {
            String targetFullClzName = entry.getKey();
            List<Element> injectElements = entry.getValue();
            if (injectElements == null || injectElements.isEmpty()) {
                continue;
            }

            // 创建注入方法
            TypeElement target = mElementUtils.getTypeElement(targetFullClzName);
            ParameterSpec injectParameter = ParameterSpec.builder(ClassName.get(target), Constants.PARAMETER_OF_TARGET).build();
            MethodSpec.Builder injectMethod = MethodSpec.methodBuilder(Constants.METHOD_OF_INJECT)
                    .addJavadoc("向{@code $N}注入组件\n@param $N",
                            Constants.PARAMETER_OF_TARGET,
                            Constants.PARAMETER_OF_TARGET)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .addParameter(injectParameter)
                    .returns(void.class);
            for (Element injectElement : injectElements) {
                injectMethod
                        .beginControlFlow("try")
                        .addStatement("$N.$N = ($T) $T.getInstance().getModule($S)",
                                Constants.PARAMETER_OF_TARGET,
                                injectElement.getSimpleName(),
                                injectElement.asType(),
                                mElementUtils.getTypeElement(Constants.XMODULABLE),
                                injectElement.getAnnotation(InjectXModule.class).name())
                        .nextControlFlow("catch ($T e)",
                                ClassName.get(mElementUtils.getTypeElement(Constants.UNKNOWN_MODULE_EXCEPTION)))
                        .addStatement("e.printStackTrace()")
                        .endControlFlow();
            }
            mLogger.info(String.format("创建方法：%s", Constants.METHOD_OF_INJECT));

            // 创建类
            String injectorClzName = new StringBuilder()
                    .append(target.getSimpleName())
                    .append(Constants.SEPARATOR_OF_CLASS_NAME)
                    .append(Constants.CLASS_OF_INJECTOR)
                    .toString();
            TypeSpec.Builder targetInjector = TypeSpec.classBuilder(injectorClzName)
                    .addJavadoc(
                            new StringBuilder()
                                    .append("组件注入器")
                                    .append("\n")
                                    .append("<ul><li>")
                                    .append(Constants.WARNING_TIPS)
                                    .append("</li></ul>")
                                    .append("\n@author $N")
                                    .toString(),
                            InjectProcessor.class.getSimpleName())
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethod(injectMethod.build());
            mLogger.info(String.format("创建类：%s", injectorClzName));

            // 输出源文件
            try {
                String pkgName = mElementUtils.getPackageOf(injectElements.get(0)).getQualifiedName().toString();
                JavaFile.builder(pkgName, targetInjector.build())
                        .build()
                        .writeTo(mFiler);
                mLogger.info(String.format("输出源文件：%s", pkgName + "." + injectorClzName + ".java"));
            } catch (IOException e) {
                throw new ProcessException(e.fillInStackTrace());
            }
        }
    }
}
