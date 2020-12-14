package com.honeycomb.agent.transformaer;

import com.honeycomb.agent.asm.CostMethodVisitor;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import static jdk.internal.org.objectweb.asm.Opcodes.ASM4;

/**
 * @author maoliang
 */
public class CostStatisticTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (className.equals("com/honeycomb/asm/pojo/Bean")) {
            outputClazz(classfileBuffer, "Source");
            System.out.println("开始处理方法：" + className);
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            ClassVisitor visitor = new ClassVisitor(ASM4, cw) {
                @Override
                public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                    return new CostMethodVisitor(ASM4, super.visitMethod(access, name, desc, signature, exceptions));
                }
            };
            ClassReader cr = new ClassReader(classfileBuffer);
            cr.accept(visitor, 0);
            final byte[] bytes = cw.toByteArray();
            outputClazz(bytes, "Test");
            return bytes;
        } else {
            return classfileBuffer;
        }
    }

    private static void outputClazz(byte[] bytes, String className) {
        // 输出类字节码
        FileOutputStream out = null;
        try {
            String pathName = CostStatisticTransformer.class.getResource("/").getPath() + className + "SQM.class";
            out = new FileOutputStream(new File(pathName));
            System.out.println("ASM类输出路径：" + pathName);
            out.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != out) try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
