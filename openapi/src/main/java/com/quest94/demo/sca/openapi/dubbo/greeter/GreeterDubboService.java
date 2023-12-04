package com.quest94.demo.sca.openapi.dubbo.greeter;


import com.quest94.demo.sca.openapi.dto.GlobalResponseWrapper;

public interface GreeterDubboService {

    // 同步调用方法
    GlobalResponseWrapper<String> sayHello(String name);

}
