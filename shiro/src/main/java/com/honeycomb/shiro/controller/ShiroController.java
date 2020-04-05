package com.honeycomb.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author maoliang
 * @version 1.0.0
 */
@RestController
@RequestMapping("/")
public class ShiroController {

    @PostMapping("/login")
    public String login(String username, String pass){
        Subject subject = SecurityUtils.getSubject();
        if(!subject.isAuthenticated()){
            UsernamePasswordToken token = new UsernamePasswordToken("honeycomb", "123");
            token.setRememberMe(true);
            subject.login(token);
            Session session = subject.getSession();
            session.setAttribute("username", username);
        }

        if(authenticate(username, pass)){
            return "succeed";
        }
        return "failed";
    }

    @GetMapping("get")
    @RequiresRoles("manager")
    public String get(){
        Subject subject = SecurityUtils.getSubject();
        return "Success";
    }

    private boolean authenticate(String username, String pass){
        return "honeycomb".equals(username) && "123".equals(pass);
    }

    public static void main(String[] args) throws NoSuchMethodException {
        Method authenticate = ShiroController.class.getMethod("login", String.class, String.class);
        Arrays.stream(authenticate.getParameters()).forEach(parameter -> System.out.println(parameter.getName()));
    }
}
