package com.quest94.demo.sca.api.dubbo.greet;


import com.quest94.demo.sca.api.dto.global.GlobalResponseWrapper;

public interface GreetDubboService {

    // 同步调用方法
    GlobalResponseWrapper<String> sayHello(String name);

}
