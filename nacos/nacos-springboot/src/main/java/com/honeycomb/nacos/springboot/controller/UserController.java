package com.honeycomb.nacos.springboot.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.honeycomb.nacos.springboot.entity.User;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author maoliang
 * @version 1.0.0
 */
@RestController
@RequestMapping("/user")
public class UserController implements EnvironmentAware {

    private final User user;
    private Environment environment;

    //nacos将配置加入在environment的propertysoucre最后，因此本地配置文件中的优先
    @NacosValue(value = "${user.username:ml}", autoRefreshed = true)
    private String username;

    public UserController(User user) {
        this.user = user;
    }

    @GetMapping("/username")
    public String getUsername(){
        return user.getUsername();
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
