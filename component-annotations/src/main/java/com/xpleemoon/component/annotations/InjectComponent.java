package com.xpleemoon.component.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 组件注入
 *
 * @author xpleemoon
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface InjectComponent {
    /**
     * 组件名
     */
    String name() default "";

    String desc() default "若组件未注册或未知，则会引起依赖注入失败";
}
