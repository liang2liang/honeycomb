package com.honeycomb.resilience4j.demo.test;

import com.honeycomb.resilience4j.demo.Bootstrap;
import com.honeycomb.resilience4j.demo.service.CircuitBreakerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author maoliang
 * @version 1.0.0
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Bootstrap.class)
public class CircuitBreakerTest {

    @Autowired
    private CircuitBreakerServiceImpl circuitBreakerService;

    @Test
    public void circuitBreakerTest(){
        for (int i = 0; i < 10; i++) {
            circuitBreakerService.circuitBreakerNotAOP1();
        }
    }


    //综上，circuitbreaker的机制已经被证实，且十分清晰，以下为几个需要注意的点：

//    1. 失败率的计算必须等环装满才会计算
//    2. 白名单优先级高于黑名单且白名单上的异常会被忽略，不会占用缓冲环位置，即不会计入失败率计算
//    3. 熔断器打开时同样会计算失败率，当状态转换为半开时重置为-1
//    4. 只要出现异常都可以调用降级方法，不论是在白名单还是黑名单
//    5. 熔断器的缓冲环有两个，一个关闭时的缓冲环，一个打开时的缓冲环
//    6. 熔断器关闭时，直至熔断器状态转换前所有请求都会通过，不会受到限制
//    7. 熔断器半开时，限制请求数为缓冲环的大小，其他请求会等待
//    8. 熔断器从打开到半开的转换默认还需要请求进行触发，也可通过automaticTransitionFromOpenToHalfOpenEnabled=true设置为自动触发
//    9. 优先级排序：白名单 > 黑名单 > 自定义比较方式

    @Test
    public void circuitBreakerThreadTest() throws InterruptedException {
        ExecutorService pool = Executors.newCachedThreadPool();
        for (int i=0; i<15; i++){
//            pool.submit(
                    // circuitService::circuitBreakerAOP
                    circuitBreakerService.circuitBreakerNotAOP1();
//            );
        }
//        pool.shutdown();
//
//        while (!pool.isTerminated());

        Thread.sleep(10000);
        log.info("熔断器状态已转为半开");
        pool = Executors.newCachedThreadPool();
        for (int i=0; i<15; i++){
            pool.submit(
                    // circuitService::circuitBreakerAOP
                    circuitBreakerService::circuitBreakerNotAOP1);
        }
        pool.shutdown();
    }
}
