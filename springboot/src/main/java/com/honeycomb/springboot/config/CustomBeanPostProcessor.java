package com.honeycomb.springboot.config;

import com.honeycomb.springboot.service.MockKafkaListener;
import com.honeycomb.springboot.service.MockKafkaSender;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

/**
 * @author maoliang
 * @version 1.0.0
 */
@Configuration
public class CustomBeanPostProcessor implements BeanPostProcessor {

    private MockKafkaSender mockKafkaSender = new MockKafkaSender();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if("concreteService".equals(beanName)) {
            System.out.println("postProcessBeforeInitialization call");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if("concreteService".equals(beanName)) {
            System.out.println("postProcessAfterInitialization call");
        }
//        process(bean);
        return bean;
    }

    private void process(Object bean) {
        if (bean instanceof MockKafkaListener) {
            mockKafkaSender.add("test", bean);
            try {
                mockKafkaSender.afterPropertiesSet();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
