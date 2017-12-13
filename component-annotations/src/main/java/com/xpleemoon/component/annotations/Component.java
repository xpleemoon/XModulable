package com.xpleemoon.component.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 组件注解
 *
 * @author xpleemoon
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Component {
    /**
     * 组件名
     */
    String name() default "";
}
