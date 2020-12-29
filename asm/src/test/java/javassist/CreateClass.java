package javassist;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author maoliang
 */
public class CreateClass {

    @Test
    public void test() throws CannotCompileException, IOException, IllegalAccessException, InstantiationException {
        ClassPool pool = ClassPool.getDefault();

        // 创建类 classname：创建类路径和名称
        CtClass ctClass = pool.makeClass("org.itstack.demo.javassist.HelloWorld");

        // 创建无参数构造方法
        CtConstructor ctConstructor = new CtConstructor(new CtClass[]{}, ctClass);
        ctConstructor.setBody("{}");
        ctClass.addConstructor(ctConstructor);

        //只需要输出到对应的target目录就不需要手动通过classloader加载，AppClassLoader会自动加载
        ctClass.writeFile(".\\target\\classes");

        // 测试调用
        Class clazz = ctClass.toClass();
        Object obj = clazz.newInstance();

        Assert.assertEquals("org.itstack.demo.javassist.HelloWorld", clazz.getName());
    }

    @Test
    public void addMethod() throws CannotCompileException, NotFoundException, IOException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        ClassPool pool = ClassPool.getDefault();

        // 创建类 classname：创建类路径和名称
        CtClass ctClass = pool.makeClass("org.itstack.demo.javassist.HelloWorld");

        // 添加方法：返回值 方法名 参数列表集合 类
        CtMethod mainMethod = new CtMethod(CtClass.voidType, "main", new CtClass[]{pool.get(String[].class.getName())}, ctClass);
        mainMethod.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
        mainMethod.setBody("{System.out.println(\"javassist hi helloworld by 小傅哥(bugstack.cn)\");}");
        ctClass.addMethod(mainMethod);

        // 创建无参数构造方法
        CtConstructor ctConstructor = new CtConstructor(new CtClass[]{}, ctClass);
        ctConstructor.setBody("{}");
        ctClass.addConstructor(ctConstructor);

        //只需要输出到对应的target目录就不需要手动通过classloader加载，AppClassLoader会自动加载
        ctClass.writeFile(".\\target\\classes");

        // 测试调用
        Class clazz = ctClass.toClass();
        Object obj = clazz.newInstance();

        Method main = clazz.getDeclaredMethod("main", String[].class);
        main.invoke(obj, (Object)new String[1]);
    }
}
