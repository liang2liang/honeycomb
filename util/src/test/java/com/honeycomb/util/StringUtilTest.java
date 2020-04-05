package com.honeycomb.util;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @className StringUtilTest
 * @date 2019-03-19 16:45
 */
public class StringUtilTest {

    @Test
    public void testPascalCaseWhenStrIsBlankThenReturnBlankStr(){
        String str = "";
        Assertions.assertEquals("", StringUtil.pascalCase(str));

        Assertions.assertNull(StringUtil.pascalCase(null));
    }

    @Test
    public void testPascalCase(){
        Assertions.assertEquals("PersonId", StringUtil.pascalCase("personId"));
    }

    @Test
    public void testSnakeCaseWhenStrIsBlankThenReturnBlankStr(){
        String str = "";
        Assertions.assertNull(StringUtil.snakeCase(null));
    }

    @Test
    public void testSnakeCase(){
        Assertions.assertEquals("person_id", StringUtil.snakeCase("personId"));
    }

    @Test
    public void testIsBlank(){
        Assertions.assertTrue(StringUtil.isBlank(null));
        Assertions.assertTrue(StringUtil.isBlank(""));
        Assertions.assertTrue(StringUtil.isBlank("  "));
        Assertions.assertFalse(StringUtil.isBlank("bob"));
    }

    @Test
    public void testIsNotBlank(){
        Assertions.assertFalse(StringUtil.isNotBlank(null));
        Assertions.assertFalse(StringUtil.isNotBlank(""));
        Assertions.assertFalse(StringUtil.isNotBlank("  "));
        Assertions.assertTrue(StringUtil.isNotBlank("bob"));
    }

    @Test
    public void testIsEmpty(){
        Assertions.assertTrue(StringUtil.isEmpty(null));
        Assertions.assertTrue(StringUtil.isEmpty(""));
        Assertions.assertFalse(StringUtil.isEmpty("  "));
        Assertions.assertFalse(StringUtil.isEmpty("bob"));
    }

    @Test
    public void testIsNotEmpty(){
        Assertions.assertFalse(StringUtil.isNotEmpty(null));
        Assertions.assertFalse(StringUtil.isNotEmpty(""));
        Assertions.assertTrue(StringUtil.isNotEmpty("  "));
        Assertions.assertTrue(StringUtil.isNotEmpty("bob"));
    }
}
