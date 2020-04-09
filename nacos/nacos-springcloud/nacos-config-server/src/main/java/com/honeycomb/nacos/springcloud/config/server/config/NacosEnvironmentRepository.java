package com.honeycomb.nacos.springcloud.config.server.config;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

/**
 * @author maoliang
 * @version 1.0.0
 */
@Component
public class NacosEnvironmentRepository implements EnvironmentRepository {

    /**
     * springcloud的下不会加载nacos基础模块,所以@NacosInjected为null
     */
    @NacosInjected
    private ConfigService configService;

    @Override
    public Environment findOne(String application, String profile, String label) {
        String dataId = application + "-" + profile;
        String content = getContent(dataId);
        Environment environment = new Environment(application, profile);
        environment.add(new PropertySource(dataId, properties(content)));
        return environment;
    }

    private Properties properties(String content){
        Properties properties = new Properties();
        try {
            properties.load(new StringReader(content));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    private String getContent(String dataId) {
        try {
            return configService.getConfig(dataId, "DEFAULT_GROUP",5000);
        } catch (NacosException e) {
            e.printStackTrace();
        }
        return null;
    }
}
