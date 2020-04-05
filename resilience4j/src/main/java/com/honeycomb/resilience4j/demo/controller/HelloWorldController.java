package com.honeycomb.resilience4j.demo.controller;

import com.honeycomb.resilience4j.demo.service.CircuitBreakerServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author maoliang
 * @version 1.0.0
 */
@RestController
public class HelloWorldController {

    private final CircuitBreakerServiceImpl circuitBreakerService;

    public HelloWorldController(CircuitBreakerServiceImpl circuitBreakerService) {
        this.circuitBreakerService = circuitBreakerService;
    }

    @GetMapping("/")
    public String hello(){
        return circuitBreakerService.circuitBreakerNotAOP1();
    }
}
