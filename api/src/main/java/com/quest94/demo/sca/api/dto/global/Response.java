package com.quest94.demo.sca.api.dto.global;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 统一响应结果
 **/
@Getter
public class Response implements Serializable {

    @Serial
    private static final long serialVersionUID = 1;

    /**
     * 状态信息
     */
    private final boolean success;
    /**
     * 状态码
     */
    private final String code;
    /**
     * 提示信息
     */
    private final String message;

    public Response(ResponseStatusEnum responseStatusEnum, String message) {
        this.success = responseStatusEnum.isSuccess();
        this.code = responseStatusEnum.getCode();
        this.message = message;
    }

    public static Response badRequest(String message) {
        return new Response(ResponseStatusEnum.BAD_REQUEST, message);
    }

    public static Response flowLimited(String message) {
        return new Response(ResponseStatusEnum.FLOW_LIMITED, message);
    }

    public static Response businessError(String message) {
        return new Response(ResponseStatusEnum.BUSINESS_ERROR, message);
    }

    public static Response systemError(String message) {
        return new Response(ResponseStatusEnum.SYSTEM_ERROR, message);
    }

}
