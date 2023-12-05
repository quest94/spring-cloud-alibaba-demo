package com.quest94.demo.sca.global.exception;

import com.quest94.demo.sca.openapi.dto.global.GlobalResponseWrapper;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WebControllerExceptionHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(WebControllerExceptionHandler.class);

    @Autowired
    SentinelBlockExceptionHandler sentinelBlockExceptionHandler;

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public GlobalResponseWrapper<Void> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                                           HttpServletRequest request) {
        LOGGER.warn("请求地址'{}',不支持'{}'请求", request.getRequestURI(), e.getMethod());
        return GlobalResponseWrapper.badRequest(e.getMessage());
    }

    @ExceptionHandler(value = Throwable.class)
    public GlobalResponseWrapper<Void> handleException(Throwable exception) {
        exception = sentinelBlockExceptionHandler.handle(exception);
        return ExceptionHandlerUtils.convertToGlobalResponse(exception);
    }

}
