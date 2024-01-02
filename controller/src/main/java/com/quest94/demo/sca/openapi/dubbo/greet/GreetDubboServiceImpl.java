package com.quest94.demo.sca.openapi.dubbo.greet;

import com.quest94.demo.sca.api.dto.global.ResponseSingle;
import com.quest94.demo.sca.api.dubbo.greet.GreetDubboService;
import com.quest94.demo.sca.inversion.greet.GreetService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
public class GreetDubboServiceImpl implements GreetDubboService {

    @Autowired
    private GreetService greetService;

    @Override
    public ResponseSingle<String> sayHello(String name) {
        String string = greetService.sayHello(name);
        return ResponseSingle.success(string);
    }

}
