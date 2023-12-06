package com.quest94.demo.sca.global.exception;

import com.quest94.demo.sca.config.sentinel.SentinelConfiguration;
import com.quest94.demo.sca.api.dto.global.GlobalResponseWrapper;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.apache.dubbo.rpc.model.MethodDescriptor;
import org.apache.dubbo.rpc.model.ServiceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@Activate(group = CommonConstants.PROVIDER, order = 1)
public class DubboServiceExceptionHandler implements Filter, Filter.Listener {

    public static final Logger LOGGER = LoggerFactory.getLogger(DubboServiceExceptionHandler.class);

    private SentinelBlockExceptionHandler sentinelBlockExceptionHandler;

    /**
     * 方法名 set 后面的字符， 除了开头字母大写，
     * 后续字符要和{@link SentinelConfiguration#SENTINEL_BLOCK_EXCEPTION_HANDLER}保持一致
     */
    public void setSentinelBlockExceptionHandler(SentinelBlockExceptionHandler sentinelBlockExceptionHandler) {
        this.sentinelBlockExceptionHandler = sentinelBlockExceptionHandler;
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        return invoker.invoke(invocation);
    }

    @Override
    public void onResponse(Result appResponse, Invoker<?> invoker, Invocation invocation) {
        // 判断是否有异常
        if (!appResponse.hasException()) {
            return;
        }
        // 判断是否是通用返回值
        Class<?> returnType = resolveReturnType(invocation);
        if (!GlobalResponseWrapper.class.isAssignableFrom(returnType)) {
            return;
        }
        // 处理全局异常返回值
        Throwable exception = extractException(appResponse);
        GlobalResponseWrapper<Void> globalResponseWrapper = ExceptionHandlerUtils.convertToGlobalResponse(exception);
        appResponse.setException(null);
        appResponse.setValue(globalResponseWrapper);
    }

    private Throwable extractException(Result appResponse) {
        Throwable exception = appResponse.getException();
        exception = resolveSentinelException(exception);
        return exception;
    }

    private Throwable resolveSentinelException(Throwable exception) {
        if (Objects.isNull(sentinelBlockExceptionHandler)) {
            LOGGER.error("没有取到 blockExceptionHandler，" +
                    "blockExceptionHandler 的 BeanName 为 " +
                    SentinelConfiguration.SENTINEL_BLOCK_EXCEPTION_HANDLER
                    + " 请保持一致");
            return exception;
        }
        return sentinelBlockExceptionHandler.handle(exception);
    }

    private static Class<?> resolveReturnType(Invocation invocation) {
        Class<?> returnClass = null;
        String methodName = invocation.getMethodName();
        ServiceModel serviceModel = invocation.getServiceModel();
        for (MethodDescriptor method : serviceModel.getAllMethods()) {
            if (method.getMethodName().equals(methodName)) {
                returnClass = method.getReturnClass();
                break;
            }
        }
        return returnClass;
    }

    @Override
    public void onError(Throwable e, Invoker<?> invoker, Invocation invocation) {
    }

}
