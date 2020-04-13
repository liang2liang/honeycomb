package com.honeycomb.ehcache;

import com.honeycomb.ehcache.service.EhcacheService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EhcacheApplication.class)
public class EhcacheServiceTest {

    @Autowired
    private EhcacheService ehcacheService;

    // 有效时间是5秒，第一次和第二次获取的值是一样的，因第三次是5秒之后所以会获取新的值
    @Test
    public void testTimestamp() throws InterruptedException {
        String param = ehcacheService.getTimestamp("param");
        System.out.println("第一次调用：" + param);

        Thread.sleep(2000);
        String param1 = ehcacheService.getTimestamp("param");
        System.out.println("2秒之后调用：" + param1);
        Assertions.assertEquals(param, param1);

        Thread.sleep(4000);
        String param2 = ehcacheService.getTimestamp("param");
        System.out.println("再过4秒之后调用：" + param2);
        Assertions.assertNotEquals(param, param2);
    }

    @Test
    public void testCache() {
        String key = "zhangsan";
        String value = ehcacheService.getDataFromDB(key); // 从数据库中获取数据...
        System.out.println(value);
        System.out.println(ehcacheService.getDataFromDB(key));  // 从缓存中获取数据，所以不执行该方法体
        ehcacheService.removeDataAtDB(key); // 从数据库中删除数据
        System.out.println(ehcacheService.getDataFromDB(key));  // 从数据库中获取数据...（缓存数据删除了，所以要重新获取，执行方法体）
    }

    @Test
    public void testPut() {
        String key = "mengdee";
        ehcacheService.refreshData(key);  // 模拟从数据库中加载数据
        String data = ehcacheService.getDataFromDB(key);
        System.out.println("data:" + data); // data:mengdee::103385

        ehcacheService.refreshData(key);  // 模拟从数据库中加载数据
        String data2 = ehcacheService.getDataFromDB(key);
        System.out.println("data2:" + data2);   // data2:mengdee::180538
    }

    @Test
    public void testFindById() {
        ehcacheService.findById("1"); // 模拟从数据库中查询数据
        ehcacheService.findById("1");
    }

    @Test
    public void testIsReserved() {
        ehcacheService.isReserved("123");
        ehcacheService.isReserved("123");
    }

    @Test
    public void testRemoveUser() {
        // 线添加到缓存
        ehcacheService.findById("1");

        // 再删除
        ehcacheService.removeUser("1");

        // 如果不存在会执行方法体
        ehcacheService.findById("1");
    }

    @Test
    public void testRemoveAllUser() {
        ehcacheService.findById("1");
        ehcacheService.findById("2");

        ehcacheService.removeAllUser();

        ehcacheService.findById("1");
        ehcacheService.findById("2");

//      模拟从数据库中查询数据
//      模拟从数据库中查询数据
//      UserCache delete all
//      模拟从数据库中查询数据
//      模拟从数据库中查询数据
    }
}
