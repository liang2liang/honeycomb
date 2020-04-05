package com.honeycomb.resilience4j.demo.service;

import com.honeycomb.resilience4j.demo.exception.BusinessAException;
import com.honeycomb.resilience4j.demo.exception.BusinessBException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author maoliang
 * @version 1.0.0
 */
@Slf4j
@Service
public class HelloWorldService {

    private static AtomicInteger count = new AtomicInteger(0);

    public String sayName() {
        return "Hello world, honeycomb";
    }

    public String exception(){
        int num = count.getAndIncrement();
        log.info("count的值 = " + num);
        if (num % 4 == 1){
            throw new BusinessAException("异常A，不需要被记录");
        }
        if (num % 4 == 2 || num % 4 == 3){
            throw new BusinessBException("异常B，需要被记录");
        }
        log.info("服务正常运行，获取用户列表");
        // 模拟数据库的正常查询
        return sayName();
    }

    public String timelimiter(){
        int num = count.getAndIncrement();
        log.info("count的值 = " + num);
        if (num % 4 == 1){
            throw new BusinessAException("异常A，不需要被记录");
        }
        if (num % 4 == 2){
            throw new BusinessBException("异常B，需要被记录");
        }
        if (num % 4 == 3){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.info("服务正常运行，获取用户列表");
        // 模拟数据库的正常查询
        return sayName();
    }

    public String retry(){
        int num = count.getAndIncrement();
        log.info("count的值 = " + num);
        if (num % 4 == 1){
            throw new BusinessAException("异常A，需要重试");
        }
        if (num % 4 == 2){
            return null;
        }
        if (num % 4 == 3){
            throw new BusinessBException("异常B，需要重试");
        }
        log.info("服务正常运行，获取用户列表");
        // 模拟数据库的正常查询
        return sayName();
    }
}
