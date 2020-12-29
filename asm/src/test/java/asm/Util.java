package asm;

import org.objectweb.asm.Type;
import org.junit.Test;

/**
 * @author maoliang
 */
public class Util {

    @Test
    public void typeTest(){
        assert Type.getType(String.class).getInternalName().equals("java/lang/String");
        System.out.println(Type.getType(Util.class).getDescriptor());

        final Type[] argumentTypes = Type.getArgumentTypes("(I)V");
        assert argumentTypes.length == 1;
        // 非对象不能调用getInternalName,会抛出NP
        System.out.println(argumentTypes[0].getDescriptor() == "I");

        assert Type.getReturnType("(I)V") == Type.VOID_TYPE;
    }
}
