package com.ps.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends BaseException {

    private Integer httpStatus;

    public BusinessException(String code, String message) {
        super(code, message);
        this.httpStatus = 400;
    }

    public BusinessException(String code, String message, Integer httpStatus) {
        super(code, message);
        this.httpStatus = httpStatus;
    }

    public BusinessException(String message) {
        super("BUSINESS_ERROR", message);
        this.httpStatus = 400;
    }

    public BusinessException(String message, Integer httpStatus) {
        super("BUSINESS_ERROR", message);
        this.httpStatus = httpStatus;
    }

    public static BusinessException of(String code, String message) {
        return new BusinessException(code, message);
    }

    public static BusinessException of(String message) {
        return new BusinessException(message);
    }
}
