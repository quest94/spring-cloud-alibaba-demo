package com.quest94.demo.sca.openapi.dto.global;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
public class GlobalResponseWrapper<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1;

    private final boolean success;
    private final String code;
    private final String message;
    private final T data;

    private GlobalResponseWrapper(ResponseStatusEnum responseStatusEnum, String message) {
        this.success = responseStatusEnum.isSuccess();
        this.code = responseStatusEnum.getCode();
        this.message = message;
        this.data = null;
    }

    private GlobalResponseWrapper(T data) {
        this.success = ResponseStatusEnum.SUCCESS.isSuccess();
        this.code = ResponseStatusEnum.SUCCESS.getCode();
        this.message = null;
        this.data = data;
    }

    public static <T> GlobalResponseWrapper<T> success(T data) {
        return new GlobalResponseWrapper<>(data);
    }

    public static <T> GlobalResponseWrapper<T> badRequest(String message) {
        return new GlobalResponseWrapper<>(ResponseStatusEnum.BAD_REQUEST, message);
    }

    public static <T> GlobalResponseWrapper<T> flowLimited(String message) {
        return new GlobalResponseWrapper<>(ResponseStatusEnum.FLOW_LIMITED, message);
    }

    public static <T> GlobalResponseWrapper<T> businessError(String message) {
        return new GlobalResponseWrapper<>(ResponseStatusEnum.BUSINESS_ERROR, message);
    }

    public static <T> GlobalResponseWrapper<T> systemError(String message) {
        return new GlobalResponseWrapper<>(ResponseStatusEnum.SYSTEM_ERROR, message);
    }

}
