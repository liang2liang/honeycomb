package com.honeycomb.java.base.concurrent;

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class ArrayBlockingQueueTest {

    public static void main(String[] args) throws InterruptedException {

        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(2);

        //增加一个元素，如果增加失败会抛出IllegalStateException异常
        arrayBlockingQueue.add("a");

        //增加一个元素，成功返回true，失败返回false
        arrayBlockingQueue.offer("b");

        //false 队列已满
        boolean offerResult = arrayBlockingQueue.offer("c");
        assert !offerResult;

        //增加一个元素，当队列满时会进入阻塞状态，当处于阻塞状态时线程关闭会抛出中断异常
//        arrayBlockingQueue.put("d");

        //移除头元素，如果队列为空则抛出NoSuchElementException异常
        arrayBlockingQueue.remove();

        //移除指定元素，返回值true表示成功，false表示失败
        arrayBlockingQueue.remove("a");

        //移除头元素，并返回移除的元素，如果队列为空则返回null
        Object poll = arrayBlockingQueue.poll();

        //移除头元素，如果队列为空则等待指定时间
        Object poll1 = arrayBlockingQueue.poll(1, TimeUnit.SECONDS);

        //阻塞方法，如果队列为空一直阻塞，会抛出InterruptedException异常
//        arrayBlockingQueue.take();

        Iterator iterator = arrayBlockingQueue.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        Iterator iterator1 = arrayBlockingQueue.iterator();
        while (iterator1.hasNext()) {
            System.out.println(iterator1.next());
        }
    }
}
