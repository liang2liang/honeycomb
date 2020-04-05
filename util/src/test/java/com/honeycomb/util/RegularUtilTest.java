package com.honeycomb.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class RegularUtilTest {

    @Test
    @DisplayName("given str with chinese" +
            "then return true")
    void testContainsChinese(){
        String str = "测试";
        Assertions.assertTrue(RegularUtil.containsChinese(str));
    }

    @Test
    @DisplayName("given str no chinese" +
            "then return false")
    void testContainsChinese1(){
        String str = "abc";
        Assertions.assertFalse(RegularUtil.containsChinese(str));
    }
}
