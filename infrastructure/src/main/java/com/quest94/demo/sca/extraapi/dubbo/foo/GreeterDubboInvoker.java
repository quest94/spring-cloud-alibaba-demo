package com.quest94.demo.sca.extraapi.dubbo.foo;

import com.quest94.demo.sca.openapi.dubbo.greeter.GreeterDubboService;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Greeter 提供者 Dubbo 服务引入
 */
@Configuration
public class GreeterDubboInvoker {

    @Value("${consumer.registry.address.greeter}")
    private String registryAddress;

    private static final String REGISTRY_ID = "greeterDubboInvokerRegistry";

    @Bean(REGISTRY_ID)
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setId(REGISTRY_ID);
        registryConfig.setAddress(registryAddress);
        // 消费者注册中心设置成非默认属性
        registryConfig.setDefault(Boolean.FALSE);
        return registryConfig;
    }

    // 引入自己暴露的服务，模拟调用三方dubbo接口
    @DubboReference(id = "dubboDemoService", registry = REGISTRY_ID)
    private GreeterDubboService dubboDemoService;

}
