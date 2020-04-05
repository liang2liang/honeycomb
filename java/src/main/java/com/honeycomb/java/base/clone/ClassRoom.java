package com.honeycomb.java.base.clone;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class ClassRoom implements Cloneable {

    private final Student student;

    public ClassRoom(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
