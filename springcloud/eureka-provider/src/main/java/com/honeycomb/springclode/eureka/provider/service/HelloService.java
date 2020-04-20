package com.honeycomb.springclode.eureka.provider.service;

import org.springframework.stereotype.Service;

/**
 * @author maoliang
 * @version 1.0.0
 */
@Service
public class HelloService {

    public String sayHello(String name){
        return "Hello " + name;
    }
}
