package com.honeycomb.dubbo.client;

import com.honeycomb.dubbo.facade.service.FailfastService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.rpc.cluster.support.FailfastCluster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author maoliang
 * @version 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DubboClientApplication.class)
public class FailfastTest {

    @Reference(cluster = FailfastCluster.NAME)
    private FailfastService failfastService;

    @Test(expected = RuntimeException.class)
    public void testFailfast(){
        System.out.println(failfastService.failfast());
    }
}
