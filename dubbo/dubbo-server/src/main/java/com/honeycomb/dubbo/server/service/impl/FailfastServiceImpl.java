package com.honeycomb.dubbo.server.service.impl;

import com.honeycomb.dubbo.facade.service.FailfastService;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author maoliang
 * @version 1.0.0
 */
@Service
public class FailfastServiceImpl implements FailfastService {

    @Override
    public String failfast() {
        throw new RuntimeException("failfast exception");
    }
}
