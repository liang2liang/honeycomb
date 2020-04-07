package com.honeycomb.nacos.springboot.entity;

import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author maoliang
 * @version 1.0.0
 */
@Data
@Component
@NacosConfigurationProperties(prefix = "user", dataId = "nacos.springboot.properties", groupId = "nacos:springboot", autoRefreshed = true)
public class User {

    private String username;

    private int age;
}
