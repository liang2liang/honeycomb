package bytebuddy;

import javassist.ApiTest;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.Test;

import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * @author maoliang
 */
public class CreateClass {

    @Test
    public void test() throws IllegalAccessException, InstantiationException {
        final String toString = new ByteBuddy()
                .subclass(Object.class)
                .method(named("toString"))
                .intercept(FixedValue.value("Hello World!"))
                .make()
                .load(getClass().getClassLoader())
                .getLoaded()
                .newInstance()
                .toString();
        System.out.println(toString);
    }

    @Test
    public void test_byteBuddy() throws Exception {
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .subclass(BizMethod.class)
                .method(ElementMatchers.named("queryUserInfo"))
                .intercept(MethodDelegation.to(MonitorDemo.class))
                .make();

        // 加载类
        Class<?> clazz = dynamicType.load(CreateClass.class.getClassLoader())
                .getLoaded();

        // 反射调用
        clazz.getMethod("queryUserInfo", String.class, String.class).invoke(clazz.newInstance(), "10001", "Adhl9dkl");
    }
}
