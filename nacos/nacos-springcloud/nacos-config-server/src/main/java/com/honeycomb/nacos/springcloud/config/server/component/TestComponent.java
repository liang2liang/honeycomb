package com.honeycomb.nacos.springcloud.config.server.component;

import com.honeycomb.nacos.springcloud.config.server.config.NacosEnvironmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author maoliang
 * @version 1.0.0
 */
@Component
public class TestComponent {

    @Autowired
    private NacosEnvironmentRepository nacosEnvironmentRepository;

}
