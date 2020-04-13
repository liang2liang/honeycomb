package com.honeycomb.ehcache.entity;

public class User {

    private String userId;
    private String username;
    private Integer age;

    public User() {
    }

    public User(String userId, String username, Integer age) {
        this.userId = userId;
        this.username = username;
        this.age = age;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}
