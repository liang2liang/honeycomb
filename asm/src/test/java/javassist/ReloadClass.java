package javassist;

import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import javassist.util.HotSwapper;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author maoliang
 */
public class ReloadClass {

    @Test
    public void reload() throws IOException, NotFoundException, CannotCompileException, IllegalConnectorArgumentsException, InterruptedException {
        // 监听 8000 端口,在启动参数里设置
        // java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000
        HotSwapper hs = new HotSwapper(8000);

        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.get(ApiTest.class.getName());

        // 获取方法
        CtMethod ctMethod = ctClass.getDeclaredMethod("queryGirlfriendCount");
        // 重写方法
        ctMethod.setBody("{ return $1 + \"的前女友数量：\" + (0L) + \" 个\"; }");

        // 加载新的类
        System.out.println(":: 执行HotSwapper热插拔，修改谢飞机前女友数量为0个！");
        // 不能分两个服务进行，但是直接调用hotswap()是可以的
        hs.reload(ApiTest.class.getName(), ctClass.toBytecode());
    }
}
