package com.quest94.demo.sca.extraapi.dubbo.greet;

import com.quest94.demo.sca.openapi.dubbo.greet.GreetDubboService;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Bonjour 提供者 Dubbo 服务引入
 */
@Configuration
public class BonjourDubboInvoker {

    @Value("${consumer.registry.address.bonjour}")
    private String registryAddress;

    private static final String BONJOUR_REGISTRY_ID = "bonjourDubboInvokerRegistry";

    @Bean(BONJOUR_REGISTRY_ID)
    public RegistryConfig bonjourRegistryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setId(BONJOUR_REGISTRY_ID);
        registryConfig.setAddress(registryAddress);
        // 消费者注册中心设置成非默认属性
        registryConfig.setDefault(Boolean.FALSE);
        return registryConfig;
    }

    // 引入 GreetDubboService 服务，模拟调用三方dubbo接口
    @DubboReference(id = "bonjourService", registry = BONJOUR_REGISTRY_ID)
    private GreetDubboService bonjourService;

}
