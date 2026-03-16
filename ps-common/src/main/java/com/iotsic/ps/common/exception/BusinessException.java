package com.iotsic.ps.common.exception;

import com.iotsic.ps.common.enums.ErrorCodeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends BaseException {

    private Integer httpStatus;

    public BusinessException(int code, String message) {
        super(code, message);
        this.httpStatus = 400;
    }

    public BusinessException(int code, String message, Integer httpStatus) {
        super(code, message);
        this.httpStatus = httpStatus;
    }

    public BusinessException(String message) {
        super(ErrorCodeEnum.BUSINESS_ERROR.getCode(), "Business error: " + message);
        this.httpStatus = 400;
    }

    public BusinessException(String message, Integer httpStatus) {
        super(ErrorCodeEnum.BUSINESS_ERROR.getCode(), "Business error: " + message);
        this.httpStatus = httpStatus;
    }

    public static BusinessException of(int code, String message) {
        return new BusinessException(code, message);
    }

    public static BusinessException of(String message) {
        return new BusinessException(message);
    }
}
