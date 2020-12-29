package com.honeycomb.annotation.processor;

import com.google.auto.service.AutoService;
import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Names;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.util.Set;

/**
 * @author maoliang
 */
@SupportedSourceVersion(value = SourceVersion.RELEASE_8)
@SupportedAnnotationTypes(value = {"com.honeycomb.annotation.processor.NotNull"})
@AutoService(Processor.class)
public class MyProcessor extends AbstractProcessor {

    private Types typeUtils;
    private JavacElements elementUtils;
    private Filer filer;
    private Messager messager;
    private TreeMaker treeMaker;
    private Names names;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.typeUtils = processingEnv.getTypeUtils();
        this.elementUtils = (JavacElements) processingEnv.getElementUtils();
        this.filer = processingEnv.getFiler();
        this.messager = processingEnv.getMessager();
        final Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
        this.treeMaker = TreeMaker.instance(context);
        this.names = Names.instance(context);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        messager.printMessage(Diagnostic.Kind.NOTE, "MyProcess开始执行");
        final Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(NotNull.class);
        for(Element element : elementsAnnotatedWith) {
            NotNullProcessor notNullProcessor = new NotNullProcessor(treeMaker, element, names, messager, elementUtils);
            notNullProcessor.process();
        }
        return false;
    }
}
