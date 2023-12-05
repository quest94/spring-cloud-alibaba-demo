package com.quest94.demo.sca.global.exception;

import com.quest94.demo.sca.openapi.dto.global.GlobalResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WebControllerExceptionHandler {

    @Autowired
    SentinelBlockExceptionHandler sentinelBlockExceptionHandler;

    @ExceptionHandler(value = Throwable.class)
    public GlobalResponseWrapper<Void> handleException(Throwable exception) {
        exception = sentinelBlockExceptionHandler.handle(exception);
        return ExceptionHandlerUtils.convertToGlobalResponse(exception);
    }

}
