package asm;


import org.junit.Test;
import org.objectweb.asm.ClassWriter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

/**
 * Java 类型	类型描述符
 * boolean	Z
 * char	C
 * byte	B
 * short	S
 * int	I
 * float	F
 * long	J
 * double	D
 * Object	Ljava/lang/Object;
 * int[]	[I
 * Object[][]	[[Ljava/lang/Object;
 */


/**
 * @author maoliang
 */
public class CreateClass {

    @Test
    public void test() {
        ClassWriter cw = new ClassWriter(0);
        cw.visit(V1_8, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE, "asm/Comparable", null, "java/lang/Object", null);
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "LESS", "I", null, -1);
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "EQUAL", "I", null, 0);
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "GREATER", "I", null, 1);
        cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "compareTo", "(Ljava/lang/Object;)I", null, null);
        cw.visitEnd();
        byte[] b = cw.toByteArray();
        MyClassLoader myClassLoader = new MyClassLoader();
        final Class aClass = myClassLoader.defineClass("asm.Comparable", b);
        final Field[] fields = aClass.getFields();
        for (Field field : fields){
            System.out.println(field.getName());
        }
        final Method[] methods = aClass.getMethods();
        for(Method method : methods){
            System.out.println(method.getName());
        }
    }
}

class MyClassLoader extends ClassLoader {
    public Class defineClass(String name, byte[] b) {
        return defineClass(name, b, 0, b.length);
    }
}

// 目标类
//public interface Comparable {
//
//    int LESS = -1;
//
//    int EQUAL = 0;
//
//    int GREATER = 1;
//
//    int compareTo(Object o);
//}
