package com.quest94.demo.sca.global.exception;

import com.quest94.demo.sca.common.exception.FlowRegulateException;
import com.quest94.demo.sca.common.exception.ServiceException;
import com.quest94.demo.sca.openapi.dto.global.GlobalResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class ExceptionHandlerUtils {

    public static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerUtils.class);

    public static GlobalResponseWrapper<Void> convertToGlobalResponse(Throwable exception) {
        String uuid = UUID.randomUUID().toString();
        if (exception instanceof FlowRegulateException) {
            LOGGER.warn("{}，{}", exception.getMessage(), uuid);
            return GlobalResponseWrapper.flowLimited("系统繁忙，请稍后再试；" + uuid);
        }
        if (exception instanceof ServiceException) {
            LOGGER.warn("{}，{}", exception.getMessage(), uuid);
            return GlobalResponseWrapper.businessError("系统繁忙，请稍后再试；" + uuid);
        }
        LOGGER.error(uuid, exception);
        return GlobalResponseWrapper.systemError("系统异常，请稍后再试；" + uuid);
    }

}
