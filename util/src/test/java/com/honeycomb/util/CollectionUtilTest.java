package com.honeycomb.util;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author maoliang
 * @className CollectionUtilTest
 * @date 2019-03-21 12:48
 */
public class CollectionUtilTest {

    @Test
    public void testIsEmpty(){
        List list = null;
        Assertions.assertTrue(CollectionUtil.isEmpty(list));
        list = new ArrayList();
        Assertions.assertTrue(CollectionUtil.isEmpty(list));
        list.add(null);
        Assertions.assertFalse(CollectionUtil.isEmpty(list));


        Map map = null;
        Assertions.assertTrue(CollectionUtil.isEmpty(map));
        map = new HashMap(1);
        Assertions.assertTrue(CollectionUtil.isEmpty(map));
        map.put(1, null);
        Assertions.assertFalse(CollectionUtil.isEmpty(map));
    }

    @Test
    public void testIsNotEmpty() {
        List list = null;
        Assertions.assertFalse(CollectionUtil.isNotEmpty(list));
        list = new ArrayList();
        Assertions.assertFalse(CollectionUtil.isNotEmpty(list));
        list.add(null);
        Assertions.assertTrue(CollectionUtil.isNotEmpty(list));


        Map map = null;
        Assertions.assertFalse(CollectionUtil.isNotEmpty(map));
        map = new HashMap(1);
        Assertions.assertFalse(CollectionUtil.isNotEmpty(map));
        map.put(1, null);
        Assertions.assertTrue(CollectionUtil.isNotEmpty(map));
    }
}
