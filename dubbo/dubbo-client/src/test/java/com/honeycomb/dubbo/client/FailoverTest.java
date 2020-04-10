package com.honeycomb.dubbo.client;

import com.honeycomb.dubbo.facade.service.FailoverService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author maoliang
 * @version 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DubboClientApplication.class)
public class FailoverTest {

//    @Reference(methods = {
//            @Method(name = "failover", oninvoke = "failoverNotify.oninvoke", onreturn = "failoverNotify.onreturnWithoutParam", onthrow = "failoverNotify.onthrow")
//    })
    @Autowired
    private FailoverService failoverService;

//    @Test(expected = RuntimeException.class)
    @Test
    public void testFailover(){
        System.out.println(failoverService.failover());
    }
}
