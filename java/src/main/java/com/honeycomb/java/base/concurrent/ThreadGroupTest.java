package com.honeycomb.java.base.concurrent;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class ThreadGroupTest {

    public static void main(String[] args) {
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();

        System.out.println(Thread.currentThread().getPriority());
        System.out.println(threadGroup.getMaxPriority());
    }
}
