package com.xpleemoon.component.api.exception;

/**
 * 组件未知
 *
 * @author xpleemoon
 */
public class UnknownComponentException extends Exception {
    public UnknownComponentException(String name) {
        super(String.format("组件%1s未注册或不存在", name));
    }
}
