package com.honeycomb.springcloud.eureka.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HiController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/consumer")
    public String hi() {
        return restTemplate.getForObject("http://service/hi", String.class);
    }
}