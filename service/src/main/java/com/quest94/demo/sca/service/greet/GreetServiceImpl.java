/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.quest94.demo.sca.service.greet;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.quest94.demo.sca.common.exception.FlowRegulateException;
import com.quest94.demo.sca.common.exception.ServiceException;
import com.quest94.demo.sca.common.util.FlowRegulateUtils;
import com.quest94.demo.sca.common.util.StringUtils;
import com.quest94.demo.sca.inversion.greet.GreetService;
import com.quest94.demo.sca.inversion.manager.bonjour.BonjourServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Eric Zhao
 */
@Service
public class GreetServiceImpl implements GreetService {

    public static final Logger LOGGER = LoggerFactory.getLogger(GreetServiceImpl.class);

    private static final String SAY_HELLO_CODE = "DefaultGreetDubboServiceImpl#sayHello#code";
    private static final String SAY_HELLO_ANNOTATION = "DefaultGreetDubboServiceImpl#sayHello#annotation";

    @Autowired
    private BonjourServiceManager bonjourServiceManager;

    //    @PostConstruct
    // 手动修改规则（硬编码方式）一般仅用于测试和演示
    // 在 spring cloud alibaba 中如果通过硬编码方式修改规则会造成控制台不可用
    private void initFlowRules() {
        FlowRegulateUtils.loadRules(
                FlowRegulateUtils.initFlowQpsRule(SAY_HELLO_ANNOTATION, 200),
                FlowRegulateUtils.initFlowQpsRule(SAY_HELLO_CODE, 100)
        );
    }

    @Override
//    @SentinelResource(value = SAY_HELLO_ANNOTATION, blockHandler = "blockHandlerForSayHello")
    @SentinelResource(value = SAY_HELLO_ANNOTATION)
    public String sayHello(String name) {
        return FlowRegulateUtils.runInFlowRegulate(SAY_HELLO_CODE, () -> {
            LOGGER.info(name + " 执行了default服务");
            return String.format("您好 %s！", name);
        });
    }

    public String blockHandlerForSayHello(String name, BlockException blockException) {
        throw new FlowRegulateException(name, blockException);
    }

    @Override
    public String bonjour(String name) {
        String remoteResult = bonjourServiceManager.bonjour(name);
        if (StringUtils.isBlank(remoteResult)) {
            throw new ServiceException("demo 服务返回值异常");
        }
        return remoteResult;
    }

}
