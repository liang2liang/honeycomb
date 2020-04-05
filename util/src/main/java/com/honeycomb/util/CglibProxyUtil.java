package com.honeycomb.util;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.util.Objects;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class CglibProxyUtil {

    /**
     * 创建代理对象
     *
     * @param clazz       对象
     * @param interceptor 增强类
     * @param <T>         对象类型
     * @return 代理对象
     */
    public static  <T> T createProxyObj(Class<T> clazz, MethodInterceptor interceptor) {
        if (Objects.isNull(interceptor) || Objects.isNull(clazz)) {
            throw new IllegalArgumentException("clazz and interceptor must be not null");
        }
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(interceptor);

        return (T) enhancer.create();
    }

    private CglibProxyUtil() {
        throw new IllegalStateException("Utility class");
    }
}
