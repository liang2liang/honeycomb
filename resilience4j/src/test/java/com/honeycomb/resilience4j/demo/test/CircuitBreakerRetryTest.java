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
public class CircuitBreakerRetryTest {

    @Autowired
    private CircuitBreakerServiceImpl circuitBreakerService;

    //结论：
//    1. 重试配置和熔断配置互不影响。
//    2. 同一请求的重试的都只算一次。
//    3. 已重试最后一次结果去熔断配置。
    // 请求 -> 重试配置  -> 重试最后结果  -> 熔断配置  ->  熔断结果
    @Test
    public void circuitBreakerTest(){
        for (int i = 0; i < 10; i++) {
            circuitBreakerService.circuitBreakerRetryNotAOP();
        }
    }
}
