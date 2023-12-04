package com.quest94.demo.sca.service.sentinel;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.quest94.demo.sca.common.exception.FlowRegulateException;
import com.quest94.demo.sca.common.util.FlowRegulateUtils;
import com.quest94.demo.sca.inversion.sentinel.DefaultGreeterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DefaultGreeterServiceImpl implements DefaultGreeterService {

    public static final Logger LOGGER = LoggerFactory.getLogger(DefaultGreeterServiceImpl.class);

    private static final String RESOURCE_NAME_CODE = "DefaultGreeterServiceImpl#sayHello#code";
    private static final String RESOURCE_NAME_ANNOTATION = "DefaultGreeterServiceImpl#sayHello#annotation";

    //    @PostConstruct
    // 手动修改规则（硬编码方式）一般仅用于测试和演示
    // 在 spring cloud alibaba 中如果通过硬编码方式修改规则会造成控制台不可用
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
            LOGGER.info(name + " 执行了default服务");
            return String.format("您好 %s！", name);
        });
    }

    public String blockHandlerForSayHello(String name, BlockException blockException) {
        throw new FlowRegulateException(name, blockException);
    }

}
