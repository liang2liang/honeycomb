package com.honeycomb.springboot.factory;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @author maoliang
 * @version 1.0.0
 */
@Component
public class CustomFactory implements FactoryBean<String> {

    @Override
    public String getObject() throws Exception {
        return "honeycomb";
    }

    @Override
    public Class<?> getObjectType() {
        return String.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
