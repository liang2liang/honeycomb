package com.honeycomb.nacos.spring.entity;

import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author maoliang
 * @version 1.0.0
 */
@Component
@NacosConfigurationProperties(prefix = "user", dataId = "nacos-spring.properties", groupId = "nacos:spring", autoRefreshed = true)
public class User {

    private String username;

    private int age;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
