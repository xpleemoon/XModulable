package com.xpleemoon.xmodulable.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 组件声明
 *
 * @author xpleemoon
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface XModule {
    /**
     * 组件名
     */
    String name() default "";
}
