package com.quest94.demo.sca.openapi.dto;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
public class ResponseEntity<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1;

    private final boolean status;
    private final String code;
    private final String message;
    private final T data;

    private ResponseEntity(HttpStatusEnum httpStatusEnum, String message) {
        this.status = httpStatusEnum.isStatus();
        this.code = httpStatusEnum.getCode();
        this.message = message;
        this.data = null;
    }

    public static <T> ResponseEntity<T> buildBadRequest(String message) {
        return new ResponseEntity<>(HttpStatusEnum.BAD_REQUEST, message);
    }

    public static <T> ResponseEntity<T> buildSystemBusy(String message) {
        return new ResponseEntity<>(HttpStatusEnum.SYSTEM_BUSY, message);
    }

    public static <T> ResponseEntity<T> buildSystemError(String message) {
        return new ResponseEntity<>(HttpStatusEnum.SYSTEM_ERROR, message);
    }

}
