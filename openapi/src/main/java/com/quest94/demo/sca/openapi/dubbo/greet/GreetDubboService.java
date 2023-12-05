package com.quest94.demo.sca.openapi.dubbo.greet;


import com.quest94.demo.sca.openapi.dto.global.GlobalResponseWrapper;

public interface GreetDubboService {

    // 同步调用方法
    GlobalResponseWrapper<String> sayHello(String name);

}
