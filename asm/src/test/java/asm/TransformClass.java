package asm;

import org.objectweb.asm.*;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.objectweb.asm.Opcodes.*;

/**
 * @author maoliang
 */
public class TransformClass {

    private byte[] b;

    @Before
    public void init(){
        ClassWriter cw = new ClassWriter(0);
        cw.visit(V1_8, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE, "asm/Comparable", null, "java/lang/Object", null);
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "LESS", "I", null, -1);
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "EQUAL", "I", null, 0);
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "GREATER", "I", null, 1);
        cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "compareTo", "(Ljava/lang/Object;)I", null, null);
        cw.visitEnd();
        b = cw.toByteArray();
    }

    @Test
    public void test() {
        ClassWriter classWriter = new ClassWriter(0);
        ClassVisitor cv = new ClassVisitor(ASM4, classWriter) {
            @Override
            public FieldVisitor visitField(int i, String s, String s1, String s2, Object o) {
                System.out.println(i + " " + s + " " + s1 + " " + s2 + " " + o);
                return super.visitField(i, s, s1, s2, o);
            }
        };
        ClassReader cr = new ClassReader(b);
        cr.accept(cv, 0);
    }

    @Test
    public void removeField(){
        ClassWriter classWriter = new ClassWriter(0);
        ClassVisitor cv = new ClassVisitor(ASM4, classWriter) {
            @Override
            public FieldVisitor visitField(int i, String s, String s1, String s2, Object o) {
                if(s.equals("LESS")){
                    return null;
                }
                return super.visitField(i, s, s1, s2, o);
            }
        };
        ClassReader cr = new ClassReader(b);
        cr.accept(cv, 0);

        MyClassLoader myClassLoader = new MyClassLoader();
        final Class aClass = myClassLoader.defineClass("asm.Comparable", classWriter.toByteArray());
        final Field[] fields = aClass.getFields();
        assert fields.length == 2;
        for (Field field : fields){
            assert !field.getName().equals("LESS");
            System.out.println(field.getName());
        }
    }

    @Test
    public void removeMethod(){
        ClassWriter classWriter = new ClassWriter(0);
        ClassVisitor cv = new ClassVisitor(ASM4, classWriter) {
            @Override
            public MethodVisitor visitMethod(int i, String s, String s1, String s2, String[] strings) {
                if(s.equals("compareTo")){
                    return null;
                }
                return super.visitMethod(i, s, s1, s2, strings);
            }
        };
        ClassReader cr = new ClassReader(b);
        cr.accept(cv, 0);
        MyClassLoader myClassLoader = new MyClassLoader();
        final Class aClass = myClassLoader.defineClass("asm.Comparable", classWriter.toByteArray());
        final Method[] methods = aClass.getMethods();
        assert methods.length == 0;
    }

    @Test
    public void addField(){
        ClassWriter classWriter = new ClassWriter(0);
        ClassVisitor cv = new ClassVisitor(ASM4, classWriter) {

            private boolean hasLess = false;

            @Override
            public FieldVisitor visitField(int i, String s, String s1, String s2, Object o) {
                if(s.equals("LESS")){
                    hasLess = true;
                }
                return super.visitField(i, s, s1, s2, o);
            }

            @Override
            public void visitEnd() {
                if(hasLess){
                    super.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "LESS1", "I", null, -1);
                }
                super.visitEnd();
            }
        };
        ClassReader cr = new ClassReader(b);
        cr.accept(cv, 0);
        MyClassLoader myClassLoader = new MyClassLoader();
        final Class aClass = myClassLoader.defineClass("asm.Comparable", classWriter.toByteArray());
        assert aClass.getFields().length == 4;
        for (Field field : aClass.getFields()){
            System.out.println(field.getName());
        }
    }
}
