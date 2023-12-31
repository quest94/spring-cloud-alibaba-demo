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

import com.alibaba.csp.sentinel.EntryType;
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

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Eric Zhao
 */
@Service
public class GreetServiceImpl implements GreetService {

    public static final Logger LOGGER = LoggerFactory.getLogger(GreetServiceImpl.class);

    private static final String RESOURCE_NAME_BONJOUR = "GreetServiceImpl#bonjour";
    private static final String RESOURCE_NAME_SAY_HELLO_CODE = "GreetServiceImpl#sayHello#code";
    private static final String RESOURCE_NAME_SAY_HELLO_ANNOTATION = "GreetServiceImpl#sayHello#annotation";

    @Autowired
    private BonjourServiceManager bonjourServiceManager;

    //    @PostConstruct
    // 手动修改规则（硬编码方式）一般仅用于测试和演示
    // 在 spring cloud alibaba 中如果通过硬编码方式修改规则会造成控制台不可用
    private void initFlowRules() {
        FlowRegulateUtils.loadRules(
                FlowRegulateUtils.initFlowQpsRule(RESOURCE_NAME_SAY_HELLO_ANNOTATION, 200),
                FlowRegulateUtils.initFlowQpsRule(RESOURCE_NAME_SAY_HELLO_CODE, 100)
        );
    }

    @Override
    @SentinelResource(value = RESOURCE_NAME_SAY_HELLO_ANNOTATION, entryType = EntryType.IN, blockHandler = "blockHandlerForSayHello")
    public String sayHello(String name) {
        return FlowRegulateUtils.run(RESOURCE_NAME_SAY_HELLO_CODE, () -> {
            try {
                TimeUnit.MILLISECONDS.sleep(new Random(System.currentTimeMillis()).nextInt(500));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return String.format("您好 %s！", name);
        });
    }

    public String blockHandlerForSayHello(String name, BlockException blockException) {
        throw new FlowRegulateException(name, blockException);
    }

    @Override
    @SentinelResource(value = RESOURCE_NAME_BONJOUR, entryType = EntryType.IN)
    public String bonjour(String name) {
        String remoteResult = bonjourServiceManager.bonjour(name);
        if (StringUtils.isBlank(remoteResult)) {
            throw new ServiceException("bonjour 服务返回值异常");
        }
        return remoteResult;
    }

}
