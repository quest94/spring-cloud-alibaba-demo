package com.quest94.demo.sca.global.advice;

import com.quest94.demo.sca.common.exception.FlowRegulateException;
import com.quest94.demo.sca.openapi.dto.ResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@RestControllerAdvice
public class ControllerExceptionHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(value = FlowRegulateException.class)
    public ResponseEntity<Void> handleFlowRegulateException(FlowRegulateException flowRegulateException) {
        String uuid = UUID.randomUUID().toString();
        LOGGER.warn(uuid, flowRegulateException);
        return ResponseEntity.buildSystemBusy("系统繁忙，请稍后再试；" + uuid);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Void> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                                    HttpServletRequest request) {
        LOGGER.warn("请求地址'{}',不支持'{}'请求", request.getRequestURI(), e.getMethod());
        return ResponseEntity.buildBadRequest(e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Void> handleException(Exception exception) {
        String uuid = UUID.randomUUID().toString();
        LOGGER.error(uuid, exception);
        return ResponseEntity.buildSystemError("当前系统异常，请稍后再试；" + uuid);
    }

}
