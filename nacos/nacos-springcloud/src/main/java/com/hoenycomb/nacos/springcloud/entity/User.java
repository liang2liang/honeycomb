package com.hoenycomb.nacos.springcloud.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author maoliang
 * @version 1.0.0
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = "user")
@Component
public class User {

    private String username;

    private int age;
}
