package com.honeycomb.dubbo.server.controller;

import com.honeycomb.dubbo.facade.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author maoliang
 * @version 1.0.0
 */
@RestController
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/hello/{name}")
    public String sayHello(@PathVariable(value = "name") String name){
        return helloService.sayHello(name);
    }
}
