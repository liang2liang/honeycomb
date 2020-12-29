package javassist;

import javassist.util.HotSwapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * public class ApiTest {
 *
 *     private double π = 3.14D;
 *
 *     //S = πr²
 *     public double calculateCircularArea(int r) {
 *         return π * r * r;
 *     }
 *
 *     //S = a + b
 *     public double sumOfTwoNumbers(double a, double b) {
 *         return a + b;
 *     }
 *
 * }
 *
 * @author maoliang
 */
public class TransformClass {

    private CtClass ctClass;

    @Before
    public void init() throws CannotCompileException, IOException {
        final ClassPool classPool = ClassPool.getDefault();
        ctClass = classPool.makeClass("javassist.ApiTest");

        final CtField π = new CtField(CtClass.doubleType, "π", ctClass);
        π.setModifiers(Modifier.PRIVATE);
        ctClass.addField(π, "3.14");

        final CtMethod calculateCircularArea = new CtMethod(CtClass.doubleType, "calculateCircularArea", new CtClass[]{CtClass.intType}, ctClass);
        calculateCircularArea.setModifiers(Modifier.PUBLIC);
        calculateCircularArea.setBody("{return π * $1 * $1;}");
        ctClass.addMethod(calculateCircularArea);

        final CtMethod sumOfTwoNumbers = new CtMethod(CtClass.doubleType, "sumOfTwoNumbers", new CtClass[]{CtClass.doubleType, CtClass.doubleType}, ctClass);
        sumOfTwoNumbers.setModifiers(Modifier.PUBLIC);
        sumOfTwoNumbers.setBody("{return $1 + $2;}");
        ctClass.addMethod(sumOfTwoNumbers);

        //如果一个CtClass对象通过writeFile（）,toClass（）或者toByteCode（）转换成class文件，那么javassist会冻结这个CtClass对象。后面就不能修改这个CtClass对象了。这样是为了警告开发者不要修改已经被JVM加载的class文件，因为JVM不允许重新加载一个类。
//        ctClass.writeFile(".\\target\\classes");
    }

    @Test
    public void test() throws CannotCompileException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        // 测试调用
        Class clazz = ctClass.toClass();
        Object obj = clazz.newInstance();

        Method method_calculateCircularArea = clazz.getDeclaredMethod("calculateCircularArea", int.class);
        Object obj_01 = method_calculateCircularArea.invoke(obj, 3);
        System.out.println("圆面积：" + obj_01);

        Method method_sumOfTwoNumbers = clazz.getDeclaredMethod("sumOfTwoNumbers", double.class, double.class);
        Object obj_02 = method_sumOfTwoNumbers.invoke(obj, 1, 2);
        System.out.println("两数和：" + obj_02);
    }

    @Test
    public void addProxy() throws CannotCompileException, NotFoundException, IOException {
        // 测试调用
        final CtMethod calculateCircularArea = ctClass.getDeclaredMethod("calculateCircularArea");
        // 增加变量 ：如果一个CtClass对象通过writeFile（）,toClass（）或者toByteCode（）转换成class文件，那么javassist会冻结这个CtClass对象。后面就不能修改这个CtClass对象了。这样是为了警告开发者不要修改已经被JVM加载的class文件，因为JVM不允许重新加载一个类。
        calculateCircularArea.addLocalVariable("test", CtClass.doubleType);
        // 增加参数
        calculateCircularArea.addParameter(CtClass.doubleType);
        // 执行前增强
        calculateCircularArea.insertBefore("{test = 0.1;}");
        // 执行后增强
        calculateCircularArea.insertAfter("{test = 0.3;}");
        // catch:注意添加异常块时一定要添加throw $e即一定要抛出异常，否则编译错误；$e：标识异常值
        calculateCircularArea.addCatch("{throw $e;}", ClassPool.getDefault().get("java.lang.Exception"));
        ctClass.writeFile(".\\target\\classes");
    }
}
