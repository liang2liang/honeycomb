package com.honeycomb.nacos.spring.config;

import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.context.annotation.Configuration;

/**
 * @author maoliang
 * @version 1.0.0
 */
@Configuration
@EnableNacosConfig(globalProperties = @NacosProperties(serverAddr = "127.0.0.1:8848"))
@NacosPropertySource(dataId = "nacos-spring.properties", groupId = "nacos:spring", autoRefreshed = true)
public class NacosConfiguration {
}
