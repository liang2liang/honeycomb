package com.honeycomb.dubbo.facade.service;

/**
 * @author maoliang
 * @version 1.0.0
 */
public interface FailfastService {

    /**
     * 快速失败，调用出现异常直接返回错误.
     * 通常用于非幂等性的写操作，比如新增记录。
     * @return
     */
    String failfast();
}
