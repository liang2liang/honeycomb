package com.honeycomb.springboot.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author maoliang
 * @version 1.0.0
 */
@ConfigurationProperties(prefix = "user")
public class User {

    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
