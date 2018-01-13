package com.xpleemoon.xmodulable.api.exception;

/**
 * 组件未知
 *
 * @author xpleemoon
 */
public class UnknownModuleException extends Exception {
    public UnknownModuleException(String name) {
        super(String.format("组件%1s未注册或不存在", name));
    }
}
