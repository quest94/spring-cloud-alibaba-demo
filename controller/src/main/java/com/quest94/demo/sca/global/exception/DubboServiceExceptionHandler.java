package com.quest94.demo.sca.global.exception;

import com.quest94.demo.sca.api.dto.global.Response;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.apache.dubbo.rpc.model.MethodDescriptor;
import org.apache.dubbo.rpc.model.ServiceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Activate(group = CommonConstants.PROVIDER, order = 1)
public class DubboServiceExceptionHandler implements Filter, Filter.Listener {

    public static final Logger LOGGER = LoggerFactory.getLogger(DubboServiceExceptionHandler.class);

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
        if (!Response.class.isAssignableFrom(returnType)) {
            return;
        }
        // 处理全局异常返回值
        Throwable exception = extractException(appResponse);
        Response response = ExceptionHandlerUtils.convertToGlobalResponse(exception);
        appResponse.setException(null);
        appResponse.setValue(response);
    }

    private Throwable extractException(Result appResponse) {
        Throwable exception = appResponse.getException();
        exception = SentinelBlockExceptionHandler.handle(exception);
        return exception;
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
