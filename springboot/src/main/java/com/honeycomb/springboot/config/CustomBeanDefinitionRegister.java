package com.honeycomb.springboot.config;

import com.honeycomb.springboot.service.ConcreteService;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * ImportBeanDefinitionRegistrar接口的实现类可能还会实现下面org.springframework.beans.factory.Aware接口中的一个或者多个，
 * 它们各自的方法优先于ImportBeanDefinitionRegistrar#registerBeanDefinitions被调用
 * @author maoliang
 * @version 1.0.0
 */
public class CustomBeanDefinitionRegister implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(ConcreteService.class);
        registry.registerBeanDefinition("concreteService", beanDefinitionBuilder.getBeanDefinition());
    }
}
