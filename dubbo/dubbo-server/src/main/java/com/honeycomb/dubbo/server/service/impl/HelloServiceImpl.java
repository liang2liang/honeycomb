package com.honeycomb.dubbo.server.service.impl;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.honeycomb.dubbo.facade.service.HelloService;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author maoliang
 * @version 1.0.0
 */
@Service
public class HelloServiceImpl implements HelloService {

    @NacosValue(value = "${honorifics:Hello}", autoRefreshed = true)
    private String honorifics;

    @Override
    public String sayHello(String name) {
        return honorifics.concat(" ").concat(name);
    }
}
