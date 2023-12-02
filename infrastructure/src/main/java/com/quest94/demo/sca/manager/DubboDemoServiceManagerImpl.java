package com.quest94.demo.sca.manager;

import com.quest94.demo.sca.openapi.dubbo.greeter.GreeterDubboService;
import com.quest94.demo.sca.inversion.manager.DubboDemoServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DubboDemoServiceManagerImpl implements DubboDemoServiceManager {

    @Autowired
    @Qualifier("dubboDemoService")
    private GreeterDubboService dubboDemoService;

    public String sayHello(String name) {
        return dubboDemoService.sayHello(name);
    }

}
