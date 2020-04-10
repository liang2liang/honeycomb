package com.honeycomb.dubbo.server.service.impl;

import com.honeycomb.dubbo.facade.service.UserService;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author maoliang
 * @version 1.0.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public String getUsername() {
        return "honeycomb";
    }
}
