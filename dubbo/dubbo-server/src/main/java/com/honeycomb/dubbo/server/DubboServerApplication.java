package com.honeycomb.dubbo.server;

import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import com.honeycomb.dubbo.server.service.impl.HelloServiceImpl;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author maoliang
 * @version 1.0.0
 */
@EnableNacosConfig(globalProperties = @NacosProperties(serverAddr = "${dubbo.config-center.address}", namespace = "${dubbo.config-center.namespace}"))
@NacosPropertySource(dataId = "dubbo-server-test.properties", groupId = "dubbo-server", autoRefreshed = true)
@SpringBootApplication
@EnableDubbo(scanBasePackageClasses = HelloServiceImpl.class)
public class DubboServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboServerApplication.class, args);
    }
}
