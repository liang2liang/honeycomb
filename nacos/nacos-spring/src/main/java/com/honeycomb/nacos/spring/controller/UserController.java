package com.honeycomb.nacos.spring.controller;

import com.honeycomb.nacos.spring.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author maoliang
 * @version 1.0.0
 */
@RestController
@RequestMapping
public class UserController {

    private final User user;

    public UserController(User user) {
        this.user = user;
    }

    @GetMapping("/user")
    public User getUser(){
        return user;
    }
}
