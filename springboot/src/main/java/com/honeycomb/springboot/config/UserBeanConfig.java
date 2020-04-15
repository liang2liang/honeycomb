package com.honeycomb.springboot.config;

import com.honeycomb.springboot.entity.User;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author maoliang
 * @version 1.0.0
 */
@Configuration
public class UserBeanConfig {

    /**
     * 在此处的ConfigurationProperties会覆盖User类上的ConfigurationProperties配置
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "honeycomb")
    public User user(){
        return new User();
    }
}
