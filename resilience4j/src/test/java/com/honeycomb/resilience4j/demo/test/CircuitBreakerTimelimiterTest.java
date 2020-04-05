package com.honeycomb.resilience4j.demo.test;

import com.honeycomb.resilience4j.demo.Bootstrap;
import com.honeycomb.resilience4j.demo.service.CircuitBreakerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author maoliang
 * @version 1.0.0
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Bootstrap.class)
public class CircuitBreakerTimelimiterTest {

    @Autowired
    private CircuitBreakerServiceImpl circuitBreakerService;

    @Test
    public void circuitBreakerTest(){
        for (int i = 0; i < 10; i++) {
            circuitBreakerService.circuitBreakerTimeLimiter();
        }
    }
}
