package com.honeycomb.dubbo.server.entity;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * @author maoliang
 * @version 1.0.0
 */
public class NacosTest {

    @Test
    public void testNacosListener() throws NacosException {

        IntStream.range(0, 1).forEach(i -> {
            new Thread(() -> {
                try {
                    test();
                } catch (NacosException e) {
                    e.printStackTrace();
                }
            }, "Thread-" + i).start();
        });

        // 测试让主线程不退出，因为订阅配置是守护线程，主线程退出守护线程就会退出。 正式代码中无需下面代码
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void test() throws NacosException {
        System.setProperty("namespace", "413c882f-e639-408b-87e4-ab3af775ab63");
        String serverAddr = "http://test-nacos.ifeng.com:80";
        NamingService naming = NamingFactory.createNamingService(serverAddr);
        naming.subscribe("providers:com.ifeng.dubbo.namelist.manager.api.NameListFacade:1.0.0:picus", event -> {
            if (event instanceof NamingEvent) {
                System.out.println(LocalDateTime.now() + " : " + Thread.currentThread().getName() + " : " + ((NamingEvent) event).getInstances().stream().map(Instance::getIp).collect(Collectors.toList()));
            }
        });
    }
}
