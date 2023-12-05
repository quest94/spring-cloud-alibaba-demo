package com.quest94.demo.sca.global.exception;

import com.alibaba.csp.sentinel.adapter.dubbo3.config.DubboAdapterGlobalConfig;
import com.alibaba.csp.sentinel.adapter.dubbo3.fallback.DubboFallback;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.apache.dubbo.rpc.AsyncRpcResult;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DubboFallbackHandler implements DubboFallback {

    @PostConstruct
    public void init() {
        DubboAdapterGlobalConfig.setConsumerFallback(this);
        DubboAdapterGlobalConfig.setProviderFallback(this);
    }

    @Override
    public Result handle(Invoker<?> invoker, Invocation invocation, BlockException ex) {
        return AsyncRpcResult.newDefaultAsyncResult(ex, invocation);
    }

}
