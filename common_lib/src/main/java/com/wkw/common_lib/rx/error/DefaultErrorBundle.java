package com.wkw.common_lib.rx.error;

/**
 * Created by wukewei on 16/8/17.
 */
public class DefaultErrorBundle implements ErrorBundle {
    private static final String DEFAULT_ERROR_MSG = "Unknown error";

    private final Exception exception;

    public DefaultErrorBundle(Exception exception) {
        this.exception = exception;
    }

    @Override
    public Exception getException() {
        return exception;
    }

    @Override
    public String getErrorMessage() {
        return (exception != null) ? this.exception.getMessage() : DEFAULT_ERROR_MSG;
    }
}
