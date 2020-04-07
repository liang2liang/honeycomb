package com.hoenycomb.nacos.springcloud.controller;

import com.hoenycomb.nacos.springcloud.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author maoliang
 * @version 1.0.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final User user;

    public UserController(User user) {
        this.user = user;
    }

    @GetMapping("/username")
    public String getUsername() {
        return user.getUsername();
    }
}
