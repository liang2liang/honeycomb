package com.honeycomb.springboot.service;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class ProxyFactoryBean<T> implements FactoryBean<T> {

    private Class<T> aClass;

    public ProxyFactoryBean(Class<T> aClass) {
        this.aClass = aClass;
    }

    @Override
    public T getObject() throws Exception {
        return aClass.newInstance();
    }

    @Override
    public Class<T> getObjectType() {
        return aClass;
    }
}
