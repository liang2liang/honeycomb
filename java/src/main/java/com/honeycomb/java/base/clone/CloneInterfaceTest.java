package com.honeycomb.java.base.clone;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class CloneInterfaceTest {

    /**
     * clone方法属于深复制，但是只会创建新的本类对象。但是属性还是原来的对象。
     * @param args
     * @throws CloneNotSupportedException
     */
    public static void main(String[] args) throws CloneNotSupportedException {
        ClassRoom classRoom = new ClassRoom(new Student());
        ClassRoom clone = (ClassRoom) classRoom.clone();

        //false
        System.out.println(classRoom == clone);

        //true
        System.out.println(classRoom.getStudent() == clone.getStudent());

        //true
        System.out.println(classRoom.getStudent().getName() == classRoom.getStudent().getName());
    }

}
