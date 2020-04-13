package com.honeycomb.springcloud.eureka.client.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author maoliang
 * @version 1.0.0
 */
@Slf4j
@RestController
public class HelloController {

    private final DiscoveryClient discoveryClient;

    public HelloController(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @RequestMapping
    public String index(){
        discoveryClient.getServices().forEach(serviceName -> log.info("Find service is : [{}]", serviceName));
        return "hello world";
    }
}
