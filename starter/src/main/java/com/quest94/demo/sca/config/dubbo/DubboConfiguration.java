package com.quest94.demo.sca.config.dubbo;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableDubbo(scanBasePackages = "com.quest94.demo.sca.openapi.dubbo")
public class DubboConfiguration {

    private static final String REGISTRY_ID_PROVIDER_NACOS = "providerNacosRegistry";
    private static final String PROTOCOL_ID_DUBBO = CommonConstants.DUBBO;

    static {
        /*
         * 强制指定Dubbo框架使用SLF4J打印日志，在Dubbo框架的Logger工厂里log4j的优先级比slf4j高，
         * 如果项目中存在log4j的包的话，Dubbo框架的日志会打印不出来，
         * dubbo框架的Logger工厂类：org.apache.dubbo.common.logger.LoggerFactory
         */
        String loggerKey = "dubbo.application.logger";
        String logger = System.getProperty(loggerKey);
        if (StringUtils.isBlank(logger)) {
            System.setProperty(loggerKey, "slf4j");
        }
    }


    @Value("${spring.application.name}")
    private String applicationName;
    @Value("${provider.registry.address.nacos}")
    private String registryAddress;

    // 应用配置
    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(applicationName);
        /*
         * 可选值
         * 控制provider服务导出时服务注册行为，应用级服务发现迁移用
         * - instance 只注册应用级服务；
         * - interface 只注册接口级服务；
         * - all 同时注册应用级和接口级服务，默认就是这个
         */
        applicationConfig.setRegisterMode("instance");
        Map<String, String> parameters = new HashMap<>();
        /*
         * 可选值
         * 控制consumer服务引入时服务发现行为，应用级服务发现迁移用
         * - FORCE_INTERFACE 强制只使用接口级服务发现
         * - FORCE_APPLICATION 强制只使用应用级服务发现
         * - APPLICATION_FIRST 智能选择是接口级还是应用级，默认就是这个
         */
        parameters.put("migration.step", "FORCE_APPLICATION");
        applicationConfig.setParameters(parameters);
        return applicationConfig;
    }

    /**
     * @return 注册中心配置
     */
    @Bean(REGISTRY_ID_PROVIDER_NACOS)
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setId(REGISTRY_ID_PROVIDER_NACOS);
        registryConfig.setAddress(registryAddress);
        return registryConfig;
    }

    /**
     * @return Dubbo 协议配置
     */
    @Bean(PROTOCOL_ID_DUBBO)
    public ProtocolConfig dubboProtocolConfig() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setId(PROTOCOL_ID_DUBBO);
        protocolConfig.setName(CommonConstants.DUBBO);
        protocolConfig.setPort(DubboProtocol.DEFAULT_PORT);
        return protocolConfig;
    }

}
