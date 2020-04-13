package com.honeycomb.ehcache.service;


import com.honeycomb.ehcache.entity.User;

/**
 * Ehcache缓存服务接口
 */
public interface EhcacheService {

    /**
     * 测试失效情况，有效期为5秒
     * @param param
     * @return
     */
    String getTimestamp(String param);

    String getDataFromDB(String key);

    void removeDataAtDB(String key);

    String refreshData(String key);

    User findById(String userId);

    /**
     *
     * @param userId
     * @return
     */
    boolean isReserved(String userId);

    /**
     * 清除掉UserCache中某个指定key的缓存
     * @param userId
     */
    void removeUser(String userId);

    /**
     * 清除掉UserCache中全部的缓存
     */
    void removeAllUser();
}
