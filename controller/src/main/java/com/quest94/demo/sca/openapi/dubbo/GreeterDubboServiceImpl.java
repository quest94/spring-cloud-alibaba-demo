package com.quest94.demo.sca.openapi.dubbo;

import com.quest94.demo.sca.inversion.sentinel.DefaultGreeterService;
import com.quest94.demo.sca.openapi.dto.GlobalResponseWrapper;
import com.quest94.demo.sca.openapi.dubbo.greeter.GreeterDubboService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
public class GreeterDubboServiceImpl implements GreeterDubboService {

    @Autowired
    private DefaultGreeterService defaultGreeterService;

    @Override
    public GlobalResponseWrapper<String> sayHello(String name) {
        String string = defaultGreeterService.sayHello(name);
        return GlobalResponseWrapper.success(string);
    }

}
