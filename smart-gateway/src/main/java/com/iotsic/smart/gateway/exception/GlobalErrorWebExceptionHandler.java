package com.iotsic.smart.gateway.exception;

import com.alibaba.fastjson.JSON;
import com.iotsic.smart.framework.common.exception.BusinessException;
import com.iotsic.smart.framework.common.exception.base.BaseException;
import com.iotsic.smart.framework.common.exception.enums.GlobalResultCode;
import com.iotsic.smart.framework.common.result.RestResult;
import com.iotsic.smart.framework.common.utils.json.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * Gateway 的全局异常处理器
 *
 * @author Ryan
 */
@Slf4j
public class GlobalErrorWebExceptionHandler extends DefaultErrorWebExceptionHandler {

    public GlobalErrorWebExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources, ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resources, errorProperties, applicationContext);
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    @Override
    protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        Throwable throwable = getError(request);

        log.error("[renderErrorResponse][uri({}/{}) 发生异常]: {}", request.uri(), request.method(), throwable.getMessage());

        if (log.isDebugEnabled()) {
            log.debug("[renderErrorResponse][uri({}/{}) 发生异常]: ", request.uri(), request.method(), throwable);
        }

        HttpStatus httpStatus;

        RestResult<?> result;
        switch (throwable) {
            case BaseException ex -> {
                httpStatus = HttpStatus.OK;
                if (throwable instanceof BusinessException) {
                    httpStatus = HttpStatus.valueOf(((BusinessException) ex).getHttpStatus());
                }

                result = RestResult.fail(httpStatus.value(), throwable.getMessage());
            }
            case NotFoundException ex -> {
                httpStatus = (HttpStatus) ex.getStatusCode();

                result = RestResult.fail(ex.getStatusCode().value(), ex.getReason());
            }
            case ResponseStatusException ex -> {
                httpStatus = (HttpStatus) ex.getStatusCode();

                result = RestResult.fail(ex.getStatusCode().value(), ex.getReason());
                // result = JSON.parseObject(ex.getReason(), RestResult.class);
                // result = RestResult.fail(status.value(), throwable.getMessage());
            }
            case WebClientResponseException ex -> {
                switch (ex.getStatusCode()) {
                    case HttpStatus.SERVICE_UNAVAILABLE:
                        // result = RestResult.fail(GlobalResultCode.SERVER_ERROR.getCode(), ((WebClientResponseException.ServiceUnavailable) ex).getResponseBodyAsString());
                        log.warn("[renderErrorResponse]ServiceUnavailable: {}",  ex.getResponseBodyAsString());
                        result = RestResult.fail(GlobalResultCode.SERVICE_UNAVAILABLE.getCode(), GlobalResultCode.SERVICE_UNAVAILABLE.getMessage());
                        break;
                    case HttpStatus.BAD_REQUEST:
                    case HttpStatus.INTERNAL_SERVER_ERROR:
                    default:
                        result = JSON.parseObject(ex.getResponseBodyAsString(), RestResult.class);
                }

                httpStatus = (HttpStatus) ex.getStatusCode();
            }
            default -> {
                // 兜底异常处理
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

                result = RestResult.fail(GlobalResultCode.SERVER_ERROR.getCode(), GlobalResultCode.SERVER_ERROR.getMessage());
            }
        }

        return ServerResponse.status(httpStatus).contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(result));
    }
}
