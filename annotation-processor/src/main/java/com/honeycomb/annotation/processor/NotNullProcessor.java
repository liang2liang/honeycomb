package com.honeycomb.annotation.processor;

import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

/**
 * @author maoliang
 */
public class NotNullProcessor {

    /**
     * 语法树
     */
    private final TreeMaker treeMaker;

    /**
     * 使用该注解的元素
     */
    private final Element element;

    private final Names names;

    private final Messager messager;

    private final JavacElements elementUtils;

    public NotNullProcessor(TreeMaker treeMaker, Element element, Names names, Messager messager, JavacElements elementUtils) {
        this.treeMaker = treeMaker;
        this.element = element;
        this.names = names;
        this.messager = messager;
        this.elementUtils = elementUtils;
    }

    public void process() {
        // 获取参数名
        final String parameterName = element.getSimpleName().toString();
        final Element methodElement = element.getEnclosingElement();
        final String methodName = methodElement.getSimpleName().toString();
        final Element classElement = methodElement.getEnclosingElement();
        final String className = classElement.getSimpleName().toString();
        messager.printMessage(Diagnostic.Kind.WARNING, String.format("开始处理参数注解[%s]位于方法[%s]上的[%s]", NotNull.class.getSimpleName(), className.concat("#").concat(methodName), parameterName));
        final JCTree.JCLiteral literal = treeMaker.Literal(String.format("Param[%s] must not be null!!!", parameterName));
        List<JCTree.JCExpression> args = List.of(literal);

        List<JCTree.JCExpression> typeargs = List.of(memberAccess("java.lang.String"));

        final JCTree.JCNewClass jcNewClass = treeMaker.NewClass(null, typeargs, memberAccess("java.lang.IllegalArgumentException"), args, null);

        final JCTree.JCParens parens = treeMaker.Parens(
                treeMaker.Binary(
                        JCTree.Tag.EQ,
                        treeMaker.Ident(getNameFromString(parameterName)),
                        treeMaker.Literal(TypeTag.BOT, null)));

        final JCTree.JCIf anIf = treeMaker.If(parens,
                treeMaker.Throw(jcNewClass),
                null
        );

        JCTree.JCMethodDecl jcMethodDecl = (JCTree.JCMethodDecl) elementUtils.getTree(methodElement);
        jcMethodDecl.body = treeMaker.Block(0, List.of(anIf, jcMethodDecl.body));
    }


    private JCTree.JCExpression memberAccess(String components) {
        String[] componentArray = components.split("\\.");
        JCTree.JCExpression expr = treeMaker.Ident(getNameFromString(componentArray[0]));
        for (int i = 1; i < componentArray.length; i++) {
            expr = treeMaker.Select(expr, getNameFromString(componentArray[i]));
        }
        return expr;
    }

    private Name getNameFromString(String s) {
        return names.fromString(s);
    }
}
