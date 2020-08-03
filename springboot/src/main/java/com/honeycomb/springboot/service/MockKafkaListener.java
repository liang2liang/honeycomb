package com.honeycomb.springboot.service;

import com.honeycomb.springboot.anno.Listener;
import org.springframework.stereotype.Component;

/**
 * @author maoliang
 * @version 1.0.0
 */
@Component
public class MockKafkaListener {

    @Listener(topic = "test")
    public void listen(String number) {
        System.out.println(number);
    }
}
