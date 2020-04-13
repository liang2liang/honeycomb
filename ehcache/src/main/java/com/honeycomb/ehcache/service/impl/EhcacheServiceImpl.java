package com.honeycomb.ehcache.service.impl;

import com.honeycomb.ehcache.entity.User;
import com.honeycomb.ehcache.service.EhcacheService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *
 *   @Cacheable
 *   表明所修饰的方法是可以缓存的：当第一次调用这个方法时，它的结果会被缓存下来，在缓存的有效时间内，以后访问这个方法都直接返回缓存结果，不再执行方法中的代码段。
 *   这个注解可以用condition属性来设置条件，如果不满足条件，就不使用缓存能力，直接执行方法。
 *   可以使用key属性来指定key的生成规则。
 *
 *   @Cacheable 支持如下几个参数：
 *
 *   value：缓存位置名称，不能为空，如果使用EHCache，就是ehcache.xml中声明的cache的name, 指明将值缓存到哪个Cache中.
 *   key：缓存的key，默认为空，既表示使用方法的参数类型及参数值作为key，支持SpEL，如果要引用参数值使用井号加参数名，如：#userId.
 *   一般来说，我们的更新操作只需要刷新缓存中某一个值，所以定义缓存的key值的方式就很重要，最好是能够唯一，因为这样可以准确的清除掉特定的缓存，而不会影响到其它缓存值 ，
 *   本例子中使用实体加冒号再加ID组合成键的名称，如”user:1”、”order:223123”等
 *   condition：触发条件，只有满足条件的情况才会加入缓存，默认为空，既表示全部都加入缓存，支持SpEL
 *
 *
 *   @CachePut
 *   与@Cacheable不同，@CachePut不仅会缓存方法的结果，还会执行方法的代码段。它支持的属性和用法都与@Cacheable一致。
 *
 *   @CacheEvict
 *   与@Cacheable功能相反，@CacheEvict表明所修饰的方法是用来删除失效或无用的缓存数据。
 *
 *   @CacheEvict 支持如下几个参数：
 *   value：缓存位置名称，不能为空，同上
 *   key：缓存的key，默认为空，同上
 *   condition：触发条件，只有满足条件的情况才会清除缓存，默认为空，支持SpEL
 *   allEntries：true表示清除value中的全部缓存，默认为false
 *
 *
 *
 *
 */
@Service
public class EhcacheServiceImpl implements EhcacheService {


    // value的值和ehcache.xml中的配置保持一致
    @Cacheable(value = "HelloWorldCache", key = "#param")
    @Override
    public String getTimestamp(String param) {
        Long timestamp = System.currentTimeMillis();
        System.out.println(timestamp.toString());
        return timestamp.toString();
    }

    @Cacheable(value = "HelloWorldCache", key = "#key")
    @Override
    public String getDataFromDB(String key) {
        System.out.println("从数据库中获取数据。。。。");
        return key + ":" + Math.round(Math.random() * 1000000);
    }

    @CacheEvict(value="HelloWorldCache", key="#key")
    @Override
    public void removeDataAtDB(String key) {
        System.out.println("从数据库中删除数据。。。。");
    }

    //同一个缓存实例的相同的key的缓存的数据，可以用@CachePut更新，而@Cacheable在取值的时候，是@CachePut更新后的值。
    @CachePut(value="HelloWorldCache", key="#key")
    @Override
    public String refreshData(String key) {
        System.out.println("模拟从数据库中加载数据");
        return key + "::" + Math.round(Math.random()*1000000);
    }


    @Cacheable(value="HelloWorldCache", key="'user:' + #userId")
    @Override
    public User findById(String userId) {
        System.out.println("模拟从数据库中查询数据");
        return new User("1", "mengdee",10);
    }

    @Cacheable(value="HelloWorldCache", condition="#userId.length()<12")
    @Override
    public boolean isReserved(String userId) {
        System.out.println("UserCache:"+userId);
        return false;
    }

    //清除掉UserCache中某个指定key的缓存
    @CacheEvict(value="HelloWorldCache",key="'user:' + #userId")
    @Override
    public void removeUser(String userId) {
        System.out.println("UserCache remove:"+ userId);
    }


    //清除掉UserCache中全部的缓存
    @CacheEvict(value="HelloWorldCache", allEntries=true)
    @Override
    public void removeAllUser() {
        System.out.println("UserCache delete all");
    }
}
