package com.quest94.demo.sca.manager;

import com.quest94.demo.sca.common.util.StringUtils;
import com.quest94.demo.sca.inversion.manager.DubboDemoServiceManager;
import com.quest94.demo.sca.openapi.dto.GlobalResponseWrapper;
import com.quest94.demo.sca.openapi.dubbo.greeter.GreeterDubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DubboDemoServiceManagerImpl implements DubboDemoServiceManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(DubboDemoServiceManagerImpl.class);

    @Autowired
    @Qualifier("dubboDemoService")
    private GreeterDubboService dubboDemoService;

    public String sayHello(String name) {
        try {
            GlobalResponseWrapper<String> remoteResult = dubboDemoService.sayHello(name);
            if (remoteResult.isSuccess()) {
                return StringUtils.trimToEmpty(remoteResult.getData());
            }
        } catch (Exception e) {
            LOGGER.error("调用 dubboDemoService 的 sayHello 接口出现异常", e);
        }
        return "";
    }

}
