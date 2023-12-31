package com.quest94.demo.sca.global.exception;

import com.alibaba.csp.sentinel.adapter.dubbo3.config.DubboAdapterGlobalConfig;
import com.alibaba.csp.sentinel.adapter.dubbo3.fallback.DubboFallback;
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
import org.apache.dubbo.rpc.AsyncRpcResult;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
public class SentinelBlockExceptionHandler implements DubboFallback, BlockExceptionHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(SentinelBlockExceptionHandler.class);

    @PostConstruct
    public void init() {
        DubboAdapterGlobalConfig.setConsumerFallback(this);
        DubboAdapterGlobalConfig.setProviderFallback(this);
    }

    @Override
    public Result handle(Invoker<?> invoker, Invocation invocation, BlockException ex) {
        return AsyncRpcResult.newDefaultAsyncResult(ex, invocation);
    }

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                       BlockException blockException) {
        WebControllerExceptionHandler.handleThrowable(blockException, httpServletResponse);
    }

    public static Throwable handle(Throwable exception) {
        if (Objects.nonNull(exception) && exception.getCause() instanceof BlockException) {
            exception = exception.getCause();
        }
        if (!(exception instanceof BlockException)) {
            return exception;
        }
        return resolveBlockException((BlockException) exception);
    }

    private static FlowRegulateException resolveBlockException(BlockException blockException) {
        String message = null;
        if (blockException instanceof FlowException) {
            message = "限流了";
        } else if (blockException instanceof DegradeException) {
            message = "降级了";
        } else if (blockException instanceof ParamFlowException) {
            message = "热点参数限流了";
        } else if (blockException instanceof SystemBlockException) {
            message = "触发系统保护规则了";
        } else if (blockException instanceof AuthorityException) {
            message = "授权校验不通过拒绝请求了";
        }
        AbstractRule rule = blockException.getRule();
        String resource = rule.getResource();
        LOGGER.warn(resource + " " + message, blockException);
        return new FlowRegulateException(BlockException.BLOCK_EXCEPTION_MSG_PREFIX + blockException.getClass().getSimpleName());
    }

}
