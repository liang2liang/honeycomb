package com.honeycomb.doc.java.base;

/**
 * @author maoliang
 */
public class Parent {

    public int age = 1;

}
class Son extends Parent {

    public int age = 10;

    public static void main(String[] args) {
        Parent son = new Son();
        System.out.println(son.age);  //// 结果：1
    }
}
