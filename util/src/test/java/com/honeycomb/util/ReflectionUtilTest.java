package com.honeycomb.util;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author maoliang
 * @className ReflectionUtilTest
 * @date 2019-03-25 15:20
 */
public class ReflectionUtilTest {

    @Test
    public void testGetMethod(){
        Method getName = ReflectionUtil.getMethod(Bob.class, "getName");
        Assertions.assertNotNull(getName);

        Method getName1 = ReflectionUtil.getMethod(Bob.class, "getName1");
        Assertions.assertNull(getName1);
    }

    @Test
    public void testInvokeMethod(){
        Method getName = ReflectionUtil.getMethod(Bob.class, "getName");
        Bob bob = new Bob();
        bob.setName("bob");

        Object value = ReflectionUtil.invokeMethod(getName, bob);
        Assertions.assertEquals("bob", value);
    }

    @Test
    public void testIsBoolean() throws NoSuchFieldException {
        Field name = Bob.class.getDeclaredField("name");
        Field flag = Bob.class.getDeclaredField("flag");
        Field flag1 = Bob.class.getDeclaredField("flag1");

        Assertions.assertFalse(ReflectionUtil.isBoolean(null));
        Assertions.assertFalse(ReflectionUtil.isBoolean(name));
        Assertions.assertTrue(ReflectionUtil.isBoolean(flag));
        Assertions.assertTrue(ReflectionUtil.isBoolean(flag1));
    }

}


class Bob{

    private String name;

    private boolean flag;

    private Boolean flag1;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}