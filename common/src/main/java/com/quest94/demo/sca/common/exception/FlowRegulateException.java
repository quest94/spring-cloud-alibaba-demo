package com.quest94.demo.sca.common.exception;

public class FlowRegulateException extends RuntimeException {

    public FlowRegulateException(String resourceName, Exception exception) {
        super(resourceName + " 被限流了", exception);
    }

    public FlowRegulateException(String resourceName) {
        super(resourceName + " 被限流了");
    }

}
