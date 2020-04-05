package com.honeycomb.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class ArrayUtilTest {

    @Test
    @DisplayName("given empty array " +
            "then return true")
    void testIsEmpty(){
        int[] arr = new int[0];
        Assertions.assertTrue(ArrayUtil.isEmpty(arr));
    }
}
