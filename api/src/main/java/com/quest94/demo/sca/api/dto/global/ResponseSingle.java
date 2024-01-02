package com.quest94.demo.sca.api.dto.global;

import lombok.Getter;

import java.io.Serial;

/**
 * 单个数据统一响应结果
 **/
@Getter
public class ResponseSingle<T> extends Response {

    @Serial
    private static final long serialVersionUID = 1;

    /**
     * 数据
     */
    private final T data;

    private ResponseSingle(T data) {
        super(ResponseStatusEnum.SUCCESS, null);
        this.data = data;
    }

    public static <T> ResponseSingle<T> success(T data) {
        return new ResponseSingle<>(data);
    }


}
