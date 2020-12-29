package com.honeycomb.agent.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.ASM5;

public class TestVisitor extends ClassVisitor {

    public TestVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        if ("<init>".equals(name) || "<clinit>".equals(name)) {
            return super.visitMethod(access, name, desc, signature, exceptions);
        }
        return new CostMethodVisitor(ASM5, super.visitMethod(access, name, desc, signature, exceptions), access, name, desc);
    }
}