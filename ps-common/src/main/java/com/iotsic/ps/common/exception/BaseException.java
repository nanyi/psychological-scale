package com.iotsic.ps.common.exception;

import com.iotsic.ps.common.enums.ErrorCodeEnum;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final int code;
    private final String message;

    public BaseException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BaseException(String message) {
        this(ErrorCodeEnum.FAILURE.getCode(), message);
    }

    public BaseException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }
}
