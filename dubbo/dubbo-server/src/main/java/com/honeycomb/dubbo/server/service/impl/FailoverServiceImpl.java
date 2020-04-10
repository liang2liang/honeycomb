package com.honeycomb.dubbo.server.service.impl;

import com.honeycomb.dubbo.facade.service.FailoverService;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author maoliang
 * @version 1.0.0
 */
@Service(methods = {
        @Method(name = "failover", oninvoke = "before", onreturn = "after")
})
public class FailoverServiceImpl implements FailoverService {

    @Override
    public String failover(){
//       throw new RuntimeException("failover exception");
        return "succeed";
    }
}
