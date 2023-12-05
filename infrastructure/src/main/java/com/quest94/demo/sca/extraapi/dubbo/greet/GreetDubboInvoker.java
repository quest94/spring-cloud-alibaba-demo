package com.quest94.demo.sca.extraapi.dubbo.greet;

import com.quest94.demo.sca.openapi.dubbo.greet.GreetDubboService;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Greeter 提供者 Dubbo 服务引入
 */
@Configuration
public class GreetDubboInvoker {

    @Value("${consumer.registry.address.greet}")
    private String registryAddress;

    private static final String GREET_REGISTRY_ID = "greetDubboInvokerRegistry";

    @Bean(GREET_REGISTRY_ID)
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setId(GREET_REGISTRY_ID);
        registryConfig.setAddress(registryAddress);
        // 消费者注册中心设置成非默认属性
        registryConfig.setDefault(Boolean.FALSE);
        return registryConfig;
    }

    // 引入 GreetDubboService 服务，模拟调用三方dubbo接口
    @DubboReference(id = "bonjourService", registry = GREET_REGISTRY_ID)
    private GreetDubboService dubboDemoService;

}
