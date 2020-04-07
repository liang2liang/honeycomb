package com.honeycomb.nacos.springboot.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author maoliang
 * @version 1.0.0
 */
@RestController
@RequestMapping("/config")
public class ConfigController {

    @NacosValue(value = "${honeycomb:honeycomb}", autoRefreshed = true)
    private String honeycomb;

    @GetMapping(value = "/honeycomb", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getHoneycomb(){
        return honeycomb;
    }
}
