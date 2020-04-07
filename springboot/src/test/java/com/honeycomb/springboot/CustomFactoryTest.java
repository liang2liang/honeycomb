package com.honeycomb.springboot;

import com.honeycomb.springboot.factory.CustomFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author maoliang
 * @version 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomFactoryTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void test(){
        Object customFactory = applicationContext.getBean("customFactory");
        Assert.assertEquals("honeycomb", customFactory);

        Object bean = applicationContext.getBean("&customFactory");
        Assert.assertTrue(bean instanceof CustomFactory);
    }
}
