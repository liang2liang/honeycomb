package com.honeycomb.springclode.eureka.provider.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {

    @Value("${zone.name}")
    private String zoneName;

    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    public String hi() {
        return zoneName;
    }
}