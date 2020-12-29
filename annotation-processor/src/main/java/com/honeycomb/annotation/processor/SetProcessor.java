package com.honeycomb.annotation.processor;

import com.google.auto.service.AutoService;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Names;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author maoliang
 */
@SupportedSourceVersion(value = SourceVersion.RELEASE_8)
@SupportedAnnotationTypes(value = {"com.honeycomb.annotation.processor.Set"})
@AutoService(Processor.class)
public class SetProcessor extends AbstractProcessor{

    private JavacElements elementUtils;
    private Messager messager;
    private TreeMaker treeMaker;
    private Names names;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.elementUtils = (JavacElements) processingEnv.getElementUtils();
        this.messager = processingEnv.getMessager();
        final Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
        this.treeMaker = TreeMaker.instance(context);
        this.names = Names.instance(context);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        final Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(com.honeycomb.annotation.processor.Set.class);
        for (Element element : elementsAnnotatedWith) {
            if (element.getKind() == ElementKind.CLASS) {
                messager.printMessage(Diagnostic.Kind.NOTE, String.format("开始处理类[%s]上的Get注解", element.getSimpleName()));
                final JCTree.JCClassDecl tree = (JCTree.JCClassDecl) elementUtils.getTree(element);
                List<JCTree.JCVariableDecl> jcVariableDecls = new ArrayList<>();
                for (JCTree member : tree.getMembers()) {
                    if (member instanceof JCTree.JCVariableDecl) {
                        jcVariableDecls.add((JCTree.JCVariableDecl) member);
                    }
                }

                if (!jcVariableDecls.isEmpty()) {
                    jcVariableDecls.forEach(jcVariableDecl -> addGetMethod(tree, jcVariableDecl));
                }
            } else {
                messager.printMessage(Diagnostic.Kind.WARNING, String.format("注解只能在类上使用。使用错误地方:[%s]", element.getSimpleName()));
            }
        }
        return false;
    }

    private void addGetMethod(JCTree.JCClassDecl classDecl, JCTree.JCVariableDecl field) {
        final String fieldName = field.getName().toString();
        String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        boolean containMethod = false;
        for (JCTree member : classDecl.getMembers()) {
            if (member instanceof JCTree.JCMethodDecl) {
                if (methodName.equals(((JCTree.JCMethodDecl) member).getName().toString())) {
                    containMethod = true;
                    break;
                }
            }
        }

        if (containMethod) {
            messager.printMessage(Diagnostic.Kind.NOTE, String.format("类[%s]中属性[%s]已有Set方法", classDecl.getSimpleName(), fieldName));
            return;
        }

        messager.printMessage(Diagnostic.Kind.NOTE, String.format("类[%s]开始生成方法[%s]", classDecl.getSimpleName(), methodName));

        // 属性赋值语句
        // 定义基本类型：treeMaker.Type(new Type.JCPrimitiveType(TypeTag.INT, null))
        // 定义对象类型：treeMaker.Ident(names.fromString("Integer")) 或者 treeMaker.Select(treeMaker.Select(treeMaker.Ident(names.fromString("java")), names.fromString("lang")), names.fromString("Integer"))
        final JCTree.JCVariableDecl paramDel = treeMaker.VarDef(treeMaker.Modifiers(Flags.PARAMETER), names.fromString(fieldName), field.vartype, null);
        // 获取方法中存在和类属性名相同的变量，需要制定this，this需要通过select来拼接。
        final JCTree.JCFieldAccess fieldAccess = treeMaker.Select(treeMaker.Ident(names.fromString("this")), names.fromString(fieldName));
        final JCTree.JCExpressionStatement exec = treeMaker.Exec(treeMaker.Assign(fieldAccess, treeMaker.Ident(paramDel.name)));
        final JCTree.JCBlock methodBlock = treeMaker.Block(0, com.sun.tools.javac.util.List.of(exec));
        final JCTree.JCMethodDecl jcMethodDecl = treeMaker.MethodDef(treeMaker.Modifiers(Flags.PUBLIC), names.fromString(methodName), treeMaker.Type(new Type.JCVoidType()), com.sun.tools.javac.util.List.nil(), com.sun.tools.javac.util.List.of(paramDel), com.sun.tools.javac.util.List.nil(), methodBlock, null);
        final ListBuffer<JCTree> listBuffer = new ListBuffer<>();
        listBuffer.addAll(classDecl.defs);
        listBuffer.add(jcMethodDecl);
        classDecl.defs = listBuffer.toList();
        messager.printMessage(Diagnostic.Kind.NOTE, String.format("类[%s]生成方法[%s]完成", classDecl.getSimpleName(), methodName));
    }
}
