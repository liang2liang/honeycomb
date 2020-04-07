package com.hoenycomb.nacos.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author maoliang
 * @version 1.0.0
 */
@RefreshScope
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Value("${honeycomb}")
    private String honeycomb;

    @GetMapping("/honeycomb")
    public String getHoneycomb(){
        return honeycomb;
    }
}
