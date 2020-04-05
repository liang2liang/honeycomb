package com.honeycomb.util;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class CglibProxyUtilTest {

    public static class MyInterceptor implements MethodInterceptor {

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
//            System.out.println(obj); //死循环
            System.out.println("代理成功");
            return proxy.invokeSuper(obj, args);
        }
    }

    public static class Honeycomb {

        public String obtainName() {
            return "honeycomb";
        }
    }

    @Test
    public void testCreateProxy() {
        Honeycomb honeycomb = CglibProxyUtil.createProxyObj(Honeycomb.class, new MyInterceptor());
        Assertions.assertNotNull(honeycomb);
        honeycomb.obtainName();
    }
}
