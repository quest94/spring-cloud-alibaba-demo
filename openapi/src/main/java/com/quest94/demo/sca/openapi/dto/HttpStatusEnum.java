package com.quest94.demo.sca.openapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HttpStatusEnum {

    SUCCESS(Boolean.TRUE, "200", "操作成功"),
    ACCEPTED(Boolean.TRUE, "202", "请求已经被接受"),
    BAD_REQUEST(Boolean.FALSE, "300", "参数列表错误（缺少，格式不匹配）"),
    SYSTEM_BUSY(Boolean.FALSE, "400", "系统繁忙"),
    SYSTEM_ERROR(Boolean.FALSE, "500", "系统异常");

    private final boolean status;
    private final String code;
    private final String message;


}
