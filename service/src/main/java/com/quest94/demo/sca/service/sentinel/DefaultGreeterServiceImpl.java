package com.quest94.demo.sca.service.sentinel;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.quest94.demo.sca.common.exception.FlowRegulateException;
import com.quest94.demo.sca.common.util.FlowRegulateUtils;
import com.quest94.demo.sca.common.util.LocalDateTimeUtils;
import com.quest94.demo.sca.inversion.sentinel.DefaultGreeterService;
import org.springframework.stereotype.Service;

@Service
public class DefaultGreeterServiceImpl implements DefaultGreeterService {

    private static final String RESOURCE_NAME_CODE = "DefaultGreeterServiceImpl#sayHello#code";
    private static final String RESOURCE_NAME_ANNOTATION = "DefaultGreeterServiceImpl#sayHello#annotation";

    //    @PostConstruct
    // 手动修改规则（硬编码方式）一般仅用于测试和演示
    private void initFlowRules() {
        FlowRegulateUtils.loadRules(
                FlowRegulateUtils.initFlowQpsRule(RESOURCE_NAME_ANNOTATION, 200),
                FlowRegulateUtils.initFlowQpsRule(RESOURCE_NAME_CODE, 100)
        );
    }

    @Override
    @SentinelResource(value = RESOURCE_NAME_ANNOTATION, blockHandler = "blockHandlerForSayHello")
    public String sayHello(String name) {
        return FlowRegulateUtils.runInFlowRegulate(RESOURCE_NAME_CODE, () -> {
            System.out.println(LocalDateTimeUtils.formatNowDateTime() + " " + name + " 执行了default服务");
            return String.format("您好 %s！", name);
        });
    }

    public void blockHandlerForSayHello(String name, Exception exception) {
        throw new FlowRegulateException(name, exception);
    }

}
