package com.quest94.demo.sca.openapi.dubbo.greet;

import com.quest94.demo.sca.inversion.greet.GreetService;
import com.quest94.demo.sca.openapi.dto.global.GlobalResponseWrapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
public class GreetDubboServiceImpl implements GreetDubboService {

    @Autowired
    private GreetService greetService;

    @Override
    public GlobalResponseWrapper<String> sayHello(String name) {
        String string = greetService.sayHello(name);
        return GlobalResponseWrapper.success(string);
    }

}
