package com.honeycomb.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.beans.BeanInfo;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class BeanInfoTest {

    @Test
    void testObtainBeanInfo(){
        BeanInfo beanInfo = BeanInfoUtil.obtainBeanInfo(User.class);
        Assertions.assertNotNull(beanInfo);
    }

    @Test
    void testObtainPropertyReadMethod(){
        Map<String, Method> stringMethodMap = BeanInfoUtil.obtainPropertyReadMethod(User.class);
        Assertions.assertTrue(CollectionUtil.isNotEmpty(stringMethodMap));
        Assertions.assertEquals(3, stringMethodMap.size());
    }
}

class User {

    private String name;

    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}