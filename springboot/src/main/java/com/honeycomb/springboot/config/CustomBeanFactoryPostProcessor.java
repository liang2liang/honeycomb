package com.honeycomb.springboot.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.context.annotation.Configuration;

/**
 * @author maoliang
 * @version 1.0.0
 */
@Configuration
public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition concreteService = beanFactory.getBeanDefinition("concreteService");
        BeanDefinition userBeanConfig = beanFactory.getBeanDefinition("userBeanConfig");
        ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
        try {
            constructorArgumentValues.addIndexedArgumentValue(0, Class.forName(concreteService.getBeanClassName()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        concreteService.setBeanClassName("com.honeycomb.springboot.service.ProxyFactoryBean");
        ((AbstractBeanDefinition) concreteService).setConstructorArgumentValues(constructorArgumentValues);

        //修改concreteService为懒加载
//        concreteService.setLazyInit(true);
//        Arrays.stream(beanFactory.getBeanDefinitionNames()).forEach(System.out::println);
    }
}
