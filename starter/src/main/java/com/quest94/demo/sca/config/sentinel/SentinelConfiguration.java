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
package com.quest94.demo.sca.config.sentinel;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.quest94.demo.sca.global.exception.SentinelBlockExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SentinelConfiguration {

    public static final String GLOBAL_BLOCK_EXCEPTION_HANDLER = "blockExceptionHandler";

    @Bean(GLOBAL_BLOCK_EXCEPTION_HANDLER)
    public BlockExceptionHandler globalBlockExceptionHandler() {
        return new SentinelBlockExceptionHandler();
    }

}
