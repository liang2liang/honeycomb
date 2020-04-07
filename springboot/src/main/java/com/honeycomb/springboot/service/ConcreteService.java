package com.honeycomb.springboot.service;

import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class ConcreteService implements InitializingBean {

    @PostConstruct
    public void init(){
        System.out.println("init");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet");
    }
}
