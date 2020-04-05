package com.honeycomb.java.base.serialized;

import java.io.*;
import java.util.Objects;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class SerializedTest {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        User user = new User("honeycomb");
        User clone = null;
        //写入字节流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream obs = new ObjectOutputStream(out);
        obs.writeObject(user);
        obs.close();

        //分配内存，写入原始对象，生成新对象
        ByteArrayInputStream ios = new ByteArrayInputStream(out.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(ios);
        //返回生成的新对象
        clone = (User) ois.readObject();
        ois.close();

        //true
        System.out.println(Objects.nonNull(user));

        //false
        System.out.println(clone == user);
    }
}
