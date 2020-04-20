package com.honeycomb.springcloud.eureka.consumer.controller;

import com.honeycomb.springcloud.eureka.consumer.facade.ProviderFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author maoliang
 * @version 1.0.0
 */
@Slf4j
@RestController
public class HelloController {

    private final DiscoveryClient discoveryClient;

    private final ProviderFacade providerFacade;

    public HelloController(DiscoveryClient discoveryClient, ProviderFacade providerFacade) {
        this.discoveryClient = discoveryClient;
        this.providerFacade = providerFacade;
    }

    @GetMapping("/consumer/hello/{name}")
    public String index(@PathVariable("name") String name) {
        discoveryClient.getServices().forEach(serviceName -> log.info("Find service is : [{}]", serviceName));
        return providerFacade.sayHello(name);
    }
}
