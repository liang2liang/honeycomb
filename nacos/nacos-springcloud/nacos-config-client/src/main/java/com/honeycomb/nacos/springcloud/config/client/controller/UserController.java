package com.honeycomb.nacos.springcloud.config.client.controller;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
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
@RequestMapping("/user")
public class UserController {

    @NacosInjected
    private ConfigService configService;

    @Value(value = "${user.username:ml}")
    private String username;

    @GetMapping("/")
    public String getUsername(){
        return username;
    }
}
