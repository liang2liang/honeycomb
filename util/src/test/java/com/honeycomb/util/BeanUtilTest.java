package com.honeycomb.util;

import com.honeycomb.util.anno.Alias;
import com.honeycomb.util.anno.Ignore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class BeanUtilTest {

    @Test
    public void testCopyPropertiesByMap() {
        Map map = new HashMap(5);
        map.put("sub", new Sub("sub"));
        map.put("height", 1);
        map.put("ignore", new Sub("ignore"));
        map.put("ignoreAnno", "ignoreAnno");
        map.put("name", "name");
        Apple apple = new Apple();
        BeanUtil.copyPropertiesByMap(map, apple, "ignore");

        Assertions.assertEquals("sub", apple.getSub().getName());
        Assertions.assertEquals(1, apple.getHeight());
        Assertions.assertNull(apple.getIgnore());
        Assertions.assertNull(apple.getIgnoreAnno());
        Assertions.assertEquals("name", apple.getAlias());
    }

    @Test
    public void testToMapWhenObjIsNull() {
        Assertions.assertTrue(CollectionUtil.isEmpty(BeanUtil.toMap(null)));
    }

    @Test
    public void testToMap() {
        Apple apple = new Apple(new Sub("sub"), 1, "ignore", "ignoreAnno", "alias");
        Map<String, String> toMap = BeanUtil.toMap(apple, "ignore");
        Assertions.assertNotNull(toMap.get("sub"));
        Assertions.assertEquals("1", toMap.get("height"));
        Assertions.assertNull(toMap.get("ignore"));
        Assertions.assertNull(toMap.get("ignoreAnno"));
        Assertions.assertEquals("alias", toMap.get("name"));
    }
}

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
class Apple {

    private Sub sub;

    private int height;

    private String ignore;

    @Ignore
    private String ignoreAnno;

    @Alias("name")
    private String alias;
}

@Setter
@Getter
class Sub {
    private String name;

    public Sub(String name) {
        this.name = name;
    }
}
