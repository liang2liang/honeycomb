package com.honeycomb.agent.transformaer;

import com.honeycomb.agent.asm.CostMethodVisitor;
import com.honeycomb.agent.asm.ProfilingClassAdapter;
import com.honeycomb.agent.asm.TestVisitor;
import org.objectweb.asm.*;

import java.io.*;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import static org.objectweb.asm.Opcodes.ASM5;


/**
 * @author maoliang
 */
public class CostStatisticTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (className.equals("com/honeycomb/asm/pojo/Bean")) {
            outputClazz(classfileBuffer, "Source");
            System.out.println("开始处理方法：" + className);
            ClassReader cr = new ClassReader(classfileBuffer);
            ClassWriter cw = new ClassWriter(cr,ClassWriter.COMPUTE_MAXS);
            ProfilingClassAdapter testVisitor = new ProfilingClassAdapter(cw, "Bean");
            cr.accept(testVisitor, ClassReader.EXPAND_FRAMES);
            final byte[] bytes = cw.toByteArray();
            outputClazz(bytes, "Test");
            return bytes;
        } else {
            return classfileBuffer;
        }
    }

    public static void main(String[] args) throws IOException, IllegalClassFormatException {
        FileInputStream fis = new FileInputStream(new File("asm/target/classes/SourceSQM.class"));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int n;
        while ((n = fis.read(b)) != -1) {
            bos.write(b, 0, n);
        }
        fis.close();
        bos.close();
        byte[] classfileBuffer = bos.toByteArray();

        final byte[] aas = new CostStatisticTransformer().transform(null, "com/honeycomb/asm/pojo/Bean", null, null, classfileBuffer);
        System.out.println("ok");
//
//        outputClazz(classfileBuffer, "Source");
//        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
//        ClassVisitor visitor = new ClassVisitor(Opcodes.ASM5, cw) {
//            @Override
//            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
//                if ("<init>".equals(name) || "<clinit>".equals(name)) {
//                    return super.visitMethod(access, name, desc, signature, exceptions);
//                }
//                return new CostMethodVisitor(Opcodes.ASM5, super.visitMethod(access, name, desc, signature, exceptions), access, name, desc);
//            }
//        };
//        ClassReader cr = new ClassReader(classfileBuffer);
//        cr.accept(visitor, ClassReader.EXPAND_FRAMES);
//        final byte[] bytes = cw.toByteArray();
//        outputClazz(bytes, "Test");
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
