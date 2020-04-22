package com.honeycomb.springboot.config;

import com.honeycomb.springboot.anno.CustomQualifier;
import com.honeycomb.springboot.entity.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author maoliang
 * @version 1.0.0
 */
@Configuration
public class QualifierTestConfig {

    @Bean
    @CustomQualifier
    public User user1(){
        return new User();
    }

    @Bean
    @Qualifier
    public User user2(){
        return new User();
    }

    //加了@Qualifier表示需要注入所有带有@Qualifier("")的User. @CustomQualifier也可以
    @Bean
    public List<User> users(@Qualifier List<User> userList){
        return userList;
    }
}
