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

package com.quest94.demo.sca.service.sentinel;

import com.quest94.demo.sca.common.exception.ServiceException;
import com.quest94.demo.sca.common.util.StringUtils;
import com.quest94.demo.sca.inversion.manager.DubboDemoServiceManager;
import com.quest94.demo.sca.inversion.sentinel.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Eric Zhao
 */
@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DubboDemoServiceManager dubboDemoServiceManager;

    @Override
    public String bonjour(String name) {
        String remoteResult = dubboDemoServiceManager.sayHello(name);
        if (StringUtils.isBlank(remoteResult)) {
            throw new ServiceException("demo 服务返回值异常");
        }
        return remoteResult;
    }

}
