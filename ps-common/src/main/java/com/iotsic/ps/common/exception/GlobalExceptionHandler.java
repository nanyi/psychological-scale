package com.iotsic.ps.common.exception;

import com.iotsic.ps.common.enums.ErrorCodeEnum;
import com.iotsic.ps.common.result.RestResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<RestResult<String>> handleBaseException(BaseException e, HttpServletRequest request) {
        log.error("BaseException: {} - {}", e.getCode(), e.getMessage(), e);
        RestResult<String> result = new RestResult<>(e.getCode(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<RestResult<String>> handleBusinessException(BusinessException e, HttpServletRequest request) {
        log.error("BusinessException: {} - {}", e.getCode(), e.getMessage(), e);
        RestResult<String> result = new RestResult<>(e.getCode(), e.getMessage(), request.getRequestURI());
        HttpStatus status = e.getHttpStatus() != null ? HttpStatus.resolve(e.getHttpStatus()) : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(result);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestResult<Void>> handleValidationException(MethodArgumentNotValidException e) {
        StringBuilder message = new StringBuilder();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            message.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
        }
        RestResult<Void> result = new RestResult<>(ErrorCodeEnum.VALIDATION_ERROR.getCode(), "Validation error: " + message.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestResult<Void>> handleConstraintViolationException(ConstraintViolationException e) {
        RestResult<Void> result = new RestResult<>(ErrorCodeEnum.VALIDATION_ERROR.getCode(), "Validation error: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestResult<Void>> handleBindException(BindException e) {
        StringBuilder message = new StringBuilder();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            message.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
        }
        RestResult<Void> result = new RestResult<>(ErrorCodeEnum.VALIDATION_ERROR.getCode(), "Validation error: " + message.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestResult<Void>> handleMissingParameterException(MissingServletRequestParameterException e) {
        RestResult<Void> result = new RestResult<>(ErrorCodeEnum.MISSING_PARAMETER.getCode(), "Missing parameter: " + e.getParameterName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestResult<Void>> handleTypeMismatchException(MethodArgumentTypeMismatchException e) {
        RestResult<Void> result = new RestResult<>(ErrorCodeEnum.TYPE_MISMATCH.getCode(), "Parameter type mismatch: " + e.getName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<RestResult<Void>> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        RestResult<Void> result = new RestResult<>(ErrorCodeEnum.METHOD_NOT_ALLOWED.getCode(), "HTTP method not supported: " + e.getMethod());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(result);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RestResult<Void>> handleNoHandlerFoundException(NoHandlerFoundException e) {
        RestResult<Void> result = new RestResult<>(ErrorCodeEnum.NOT_FOUND.getCode(), "API not found: " + e.getRequestURL());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResult<String>> handleException(Exception e, HttpServletRequest request) {
        log.error("Internal server error: {}", e.getMessage(), e);
        RestResult<String> result = new RestResult<>(ErrorCodeEnum.INTERNAL_SERVER_ERROR.getCode(), "Internal server error", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }
}
