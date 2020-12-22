package com.honeycomb.util;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @className StringUtilTest
 * @date 2019-03-19 16:45
 */
public class StringUtilTest {

    @Test
    public void testPascalCaseWhenStrIsBlankThenReturnBlankStr() {
        String str = "";
        Assertions.assertEquals("", StringUtil.pascalCase(str));

        Assertions.assertNull(StringUtil.pascalCase(null));
    }

    @Test
    public void testPascalCase() {
        Assertions.assertEquals("PersonId", StringUtil.pascalCase("personId"));
    }

    @Test
    public void testSnakeCaseWhenStrIsBlankThenReturnBlankStr() {
        String str = "";
        Assertions.assertNull(StringUtil.snakeCase(null));
    }

    @Test
    public void testSnakeCase() {
        Assertions.assertEquals("person_id", StringUtil.snakeCase("personId"));
    }

    @Test
    public void testIsBlank() {
        Assertions.assertTrue(StringUtil.isBlank(null));
        Assertions.assertTrue(StringUtil.isBlank(""));
        Assertions.assertTrue(StringUtil.isBlank("  "));
        Assertions.assertFalse(StringUtil.isBlank("bob"));
    }

    @Test
    public void testIsNotBlank() {
        Assertions.assertFalse(StringUtil.isNotBlank(null));
        Assertions.assertFalse(StringUtil.isNotBlank(""));
        Assertions.assertFalse(StringUtil.isNotBlank("  "));
        Assertions.assertTrue(StringUtil.isNotBlank("bob"));
    }

    @Test
    public void testIsEmpty() {
        Assertions.assertTrue(StringUtil.isEmpty(null));
        Assertions.assertTrue(StringUtil.isEmpty(""));
        Assertions.assertFalse(StringUtil.isEmpty("  "));
        Assertions.assertFalse(StringUtil.isEmpty("bob"));
    }

    @Test
    public void testIsNotEmpty() {
        Assertions.assertFalse(StringUtil.isNotEmpty(null));
        Assertions.assertFalse(StringUtil.isNotEmpty(""));
        Assertions.assertTrue(StringUtil.isNotEmpty("  "));
        Assertions.assertTrue(StringUtil.isNotEmpty("bob"));
    }

    @Test
    public void testFillPlaceholder() {
        Map<String, Object> param = new HashMap<>(4);
        param.put("age", 1);
        param.put("name", "honeycomb");
        param.put("people", new People("honeycomb1", 10));
        Assertions.assertEquals("", StringUtil.fillPlaceholder("", param));
        Assertions.assertEquals("#{age}", StringUtil.fillPlaceholder("#{age}", null));
        Assertions.assertEquals("1", StringUtil.fillPlaceholder("#{age}", param));
        Assertions.assertEquals("'honeycomb'", StringUtil.fillPlaceholder("#{name}", param));
        Assertions.assertEquals("'honeycomb'", StringUtil.fillPlaceholder("#{    name }", param));
        Assertions.assertEquals("name is : 'honeycomb1', age is : 10", StringUtil.fillPlaceholder("name is : #{people.name}, age is : #{people.age}", param));
    }

    @Data
    @AllArgsConstructor
    class People{

        private String name;

        private int age;
    }
}
