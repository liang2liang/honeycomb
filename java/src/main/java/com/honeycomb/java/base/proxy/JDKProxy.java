package com.honeycomb.java.base.proxy;


import com.honeycomb.java.base.proxy.anno.Select;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author maoliang
 * @className JDKProxy
 * @date 2019-03-14 11:23
 */
public class JDKProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Select annotation = method.getAnnotation(Select.class);
        String value = annotation.value();
        System.out.println(value);
        return "ok";
    }


    public static void main(String[] args) {
        Hello hello = (Hello) Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), new Class[]{Hello.class}, new JDKProxy());
        hello.say("honeycomb");
    }
}
