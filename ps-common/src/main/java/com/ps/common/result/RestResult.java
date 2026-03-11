package com.ps.common.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;
    private String message;
    private T data;
    private Long timestamp;

    public RestResult() {
        this.timestamp = System.currentTimeMillis();
    }

    public RestResult(String code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public RestResult(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> RestResult<T> success() {
        return new RestResult<>("0000", "Success");
    }

    public static <T> RestResult<T> success(T data) {
        return new RestResult<>("0000", "Success", data);
    }

    public static <T> RestResult<T> success(String message, T data) {
        return new RestResult<>("0000", message, data);
    }

    public static <T> RestResult<T> fail(String code, String message) {
        return new RestResult<>(code, message);
    }

    public static <T> RestResult<T> fail(String message) {
        return new RestResult<>("9999", message);
    }

    public static <T> RestResult<T> fail(String code, String message, T data) {
        return new RestResult<>(code, message, data);
    }

    public boolean isSuccess() {
        return "0000".equals(this.code);
    }
}
