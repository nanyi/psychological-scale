package com.ps.common.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final String code;
    private final String message;

    public BaseException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BaseException(String message) {
        this("500", message);
    }

    public BaseException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }
}
