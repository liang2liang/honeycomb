package com.honeycomb.java.base.classloader;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author maoliang
 * @className ClassLoader
 * @description TODO
 * @date 2019-02-18 00:04
 */
public class ClassLoader extends java.lang.ClassLoader {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        ClassLoader classLoader = new ClassLoader();
        Class<?> loadClass = classLoader.loadClass("");
        Object o = loadClass.getDeclaredConstructor().newInstance();
        Method say = loadClass.getMethod("say");
        say.invoke(o);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File("/Users/zhoufeng/Desktop/Test.class");
        try (FileInputStream fis = new FileInputStream(file)){
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] read = new byte[1];
            int len = 0;
            while((len = fis.read(read)) != -1){
                bos.write(read);
            }
            byte[] classes = bos.toByteArray();
            bos.close();
            return this.defineClass("com.honeycomb.Test", classes, 0, classes.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
