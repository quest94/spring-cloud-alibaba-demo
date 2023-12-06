package com.quest94.demo.sca.manager.bonjour;

import com.quest94.demo.sca.common.util.StringUtils;
import com.quest94.demo.sca.inversion.manager.bonjour.BonjourServiceManager;
import com.quest94.demo.sca.api.dto.global.GlobalResponseWrapper;
import com.quest94.demo.sca.api.dubbo.greet.GreetDubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BonjourServiceManagerImpl implements BonjourServiceManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(BonjourServiceManagerImpl.class);

    // 使用 GreetDubboService 服务，模拟调用三方dubbo接口
    @Autowired
    @Qualifier("bonjourService")
    private GreetDubboService bonjourService;

    public String bonjour(String name) {
        try {
            GlobalResponseWrapper<String> remoteResult = bonjourService.sayHello(name);
            if (remoteResult.isSuccess()) {
                return StringUtils.trimToEmpty(remoteResult.getData());
            }
            LOGGER.info("调用 dubboDemoService 的 sayHello 接口返回失败，{}，{}", remoteResult.getCode(), remoteResult.getMessage());
        } catch (Exception e) {
            LOGGER.error("调用 dubboDemoService 的 sayHello 接口出现异常", e);
        }
        return "";
    }

}
