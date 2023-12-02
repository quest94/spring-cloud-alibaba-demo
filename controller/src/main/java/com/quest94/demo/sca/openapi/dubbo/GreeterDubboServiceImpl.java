package com.quest94.demo.sca.openapi.dubbo;

import com.quest94.demo.sca.openapi.dubbo.greeter.GreeterDubboService;
import com.quest94.demo.sca.inversion.sentinel.DefaultGreeterService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
public class GreeterDubboServiceImpl implements GreeterDubboService {

    @Autowired
    private DefaultGreeterService defaultGreeterService;

    @Override
    public String sayHello(String name) {
        return defaultGreeterService.sayHello(name);
    }

}
