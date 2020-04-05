package com.honeycomb.java.base.clone;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class Student implements Cloneable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
