package com.honeycomb.dubbo.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * @author maoliang
 * @version 1.0.0
 */
@PropertySource("classpath:dubbo-comsumer.properties")
@ImportResource("classpath:dubbo-comsumer.xml")
@SpringBootApplication
public class DubboClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboClientApplication.class, args);
    }
}
