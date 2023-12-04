package com.quest94.demo.sca.global.exception;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.AbstractRule;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.quest94.demo.sca.common.exception.FlowRegulateException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SentinelBlockExceptionHandler implements BlockExceptionHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(SentinelBlockExceptionHandler.class);

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                       BlockException blockException) {
        AbstractRule rule = blockException.getRule();
        String resource = rule.getResource();
        String message = null;
        if (blockException instanceof FlowException) {
            message = resource + " 限流了";
        } else if (blockException instanceof DegradeException) {
            message = resource + " 降级了";
        } else if (blockException instanceof ParamFlowException) {
            message = resource + " 热点参数限流了";
        } else if (blockException instanceof SystemBlockException) {
            message = resource + "触发系统保护规则了";
        } else if (blockException instanceof AuthorityException) {
            message = resource + " 授权校验不通过拒绝请求了";
        }
        LOGGER.warn(message, blockException);
        throw new FlowRegulateException("BlockException");
    }

}
