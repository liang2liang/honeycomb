package com.honeycomb.ehcache.entity;

public class Dog {
    private Long length;
    private String name;
    private int age;

    public Dog(Long length, String name, int age) {
        this.length = length;
        this.name = name;
        this.age = age;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "length=" + length +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
