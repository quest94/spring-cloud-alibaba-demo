package com.quest94.demo.sca.global.exception;

import com.quest94.demo.sca.openapi.dto.GlobalResponseWrapper;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WebControllerExceptionHandler {

    @ExceptionHandler(value = Throwable.class)
    public GlobalResponseWrapper<Void> handleException(Exception exception) {
        return ExceptionHandlerUtils.convertToGlobalResponse(exception);
    }

}
