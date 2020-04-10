package com.honeycomb.dubbo.facade.service;

/**
 * @author maoliang
 * @version 1.0.0
 */
public interface FailoverService {

    /**
     * 失败重试
     * @return
     */
    String failover();
}
