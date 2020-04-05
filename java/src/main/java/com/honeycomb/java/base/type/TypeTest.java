package com.honeycomb.java.base.type;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class TypeTest {

    public static <T> T getT(T param) {
        return param;
    }

    public static void main(String[] args) throws NoSuchFieldException, NoSuchMethodException {
        Method method = TypeTest.class.getMethod("getT", Object.class);
        Type genericReturnType = method.getGenericReturnType();
        if (genericReturnType instanceof TypeVariable) {
            //获取范型定义
            TypeVariable<?>[] typeParameters = ((TypeVariable) genericReturnType).getGenericDeclaration().getTypeParameters();
            System.out.println(typeParameters[0]);
            //获取返回值类型
            System.out.println(((TypeVariable) genericReturnType).getBounds()[0].getTypeName());
        }
    }
}
