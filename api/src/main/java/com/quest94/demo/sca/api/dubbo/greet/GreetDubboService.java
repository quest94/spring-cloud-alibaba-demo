package com.quest94.demo.sca.api.dubbo.greet;


import com.quest94.demo.sca.api.dto.global.ResponseSingle;

public interface GreetDubboService {

    // 同步调用方法
    ResponseSingle<String> sayHello(String name);

}
