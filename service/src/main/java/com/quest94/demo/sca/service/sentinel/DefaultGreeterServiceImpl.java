package com.quest94.demo.sca.service.sentinel;

import com.quest94.demo.sca.common.LocalDateTimeUtils;
import com.quest94.demo.sca.inversion.sentinel.DefaultGreeterService;
import org.springframework.stereotype.Service;


@Service
public class DefaultGreeterServiceImpl implements DefaultGreeterService {

    @Override
    public String sayHello(String name) {
        System.out.println(LocalDateTimeUtils.formatNowDateTime() + " " + name + " 执行了default服务");
        return String.format("您好 %s！", name);
    }

}
