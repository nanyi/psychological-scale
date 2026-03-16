package com.iotsic.ps.common.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.iotsic.ps.common.enums.ErrorCodeEnum;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;
    private String message;
    private T data;
    private Long timestamp;

    public RestResult() {
        this.timestamp = System.currentTimeMillis();
    }

    public RestResult(int code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public RestResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> RestResult<T> success() {
        return new RestResult<>(0, "Success");
    }

    public static <T> RestResult<T> success(T data) {
        return new RestResult<>(0, "Success", data);
    }

    public static <T> RestResult<T> success(String message, T data) {
        return new RestResult<>(0, message, data);
    }

    public static <T> RestResult<T> fail(int code, String message) {
        return new RestResult<>(code, message);
    }

    public static <T> RestResult<T> fail(String message) {
        return new RestResult<>(ErrorCodeEnum.FAILURE.getCode(), message);
    }

    public static <T> RestResult<T> fail(int code, String message, T data) {
        return new RestResult<>(code, message, data);
    }

    @JsonIgnore
    public boolean isSuccess() {
        return 0 == this.code;
    }
}
