package com.quest94.demo.sca.global.exception;

import com.quest94.demo.sca.api.dto.global.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WebControllerExceptionHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(WebControllerExceptionHandler.class);

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                        HttpServletRequest request,
                                                        HttpServletResponse httpServletResponse) {
        LOGGER.warn("请求地址'{}',不支持'{}'请求", request.getRequestURI(), e.getMethod());
        httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return Response.badRequest(e.getMessage());
    }

    @ExceptionHandler(value = Throwable.class)
    public Response handleException(Throwable exception,
                                    HttpServletRequest request,
                                    HttpServletResponse httpServletResponse) {
        // Sentinel 异常处理
        exception = SentinelBlockExceptionHandler.getInstance().handle(exception);
        // 统一异常处理
        Response response = ExceptionHandlerUtils.convertToGlobalResponse(exception);
        if (!response.isSuccess()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return response;
    }

}
