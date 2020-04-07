package com.honeycomb.nacos.springboot;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author maoliang
 * @version 1.0.0
 */
@SpringBootApplication
@NacosPropertySource(dataId = "nacos.springboot.properties", groupId = "nacos:springboot", autoRefreshed = true)
public class BootstrapApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootstrapApplication.class, args);
    }
}
