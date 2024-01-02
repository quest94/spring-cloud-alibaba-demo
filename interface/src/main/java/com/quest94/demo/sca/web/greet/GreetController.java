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

package com.quest94.demo.sca.web.greet;

import com.quest94.demo.sca.inversion.greet.GreetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/greet")
public class GreetController {

    @Autowired
    private GreetService greetService;

    @GetMapping("/bonjour/{name}")
    public String bonjour(@PathVariable String name) {
        return greetService.bonjour(name);
    }

    @PostMapping("/sayHello")
    public String sayHello(String name) {
        return greetService.bonjour(name);
    }

}
