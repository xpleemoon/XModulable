package com.xpleemoon.xmodulable.compiler.exception;

public class ProcessException extends Exception {
    public ProcessException(String msg) {
        super(msg);
    }

    public ProcessException(Throwable throwable) {
        super(throwable);
    }
}
