package com.honeycomb.springcloud.eureka.consumer.facade;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author maoliang
 * @version 1.0.0
 */
@Component
public class ProviderFacade {

    private final RestTemplate restTemplate;

    public ProviderFacade(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String sayHello(String name){
        return restTemplate.getForEntity("http://PROVIDER-SERVICE/hello/" + name, String.class).getBody();
    }
}
