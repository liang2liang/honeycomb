package com.honeycomb.springboot.service;

import com.honeycomb.springboot.entity.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class ConcreteService implements InitializingBean {

    @Autowired
    private User user;

    @PostConstruct
    public void init(){
        System.out.println("init");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet");
    }
}
