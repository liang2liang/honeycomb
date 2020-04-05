package com.honeycomb.util;

import com.honeycomb.util.anno.Finally;
import com.honeycomb.util.anno.SupplierWithException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Optional;

/**
 * @author maoliang
 */
public class TryCatchUtil {

    private static final Logger logger = LoggerFactory.getLogger(TryCatchUtil.class);

    /**
     * 安全处理
     * @param function 处理函数
     * @param <R> 返回值类型
     * @return 返回值
     */
    public static <R> R safeDisposal(SupplierWithException<R> function) {
        return safeDisposal(function, "", () -> {});
    }

    /**
     * 安全处理
     * @param function 处理函数
     * @param errorMessage 出现异常记录的错误信息
     * @param <R> 返回值类型
     * @return 返回值
     */
    public static <R> R safeDisposal(SupplierWithException<R> function, String errorMessage) {
        return safeDisposal(function, errorMessage, () -> {});
    }

    /**
     * 安全处理
     * @param function 处理函数
     * @param finallyFunction finally函数
     * @param <R> 返回值类型
     * @return 返回值
     */
    public static <R> R safeDisposal(SupplierWithException<R> function, Finally finallyFunction) {
        return safeDisposal(function, "", finallyFunction);
    }

    /**
     * 安全处理
     * @param function 处理函数
     * @param errorMessage 出现异常记录的错误信息
     * @param finallyFunction finally函数
     * @param <R> 返回值类型
     * @return 返回值
     */
    public static <R> R safeDisposal(SupplierWithException<R> function, String errorMessage, Finally finallyFunction) {
        R result = null;
        try {
            result = function.get();
        } catch (Exception e) {
            logger.error(errorMessage, e);
        } finally {
            finallyFunction.accept();
        }
        return result;
    }

    /**
     * 抛出指定异常
     * @param function 处理函数
     * @param exception 指定异常
     * @param <R> 返回值类型
     * @return 返回值
     */
    public static <R> R disposeWithException(SupplierWithException<R> function, RuntimeException exception) {
        return disposeWithException(function, exception, () -> {});
    }

    /**
     * 抛出指定异常
     * @param function 处理函数
     * @param exception 指定异常
     * @param finallyFunction finally函数
     * @param <R> 返回值类型
     * @return 返回值
     */
    public static <R> R disposeWithException(SupplierWithException<R> function, RuntimeException exception, Finally finallyFunction) {
        try {
            return function.get();
        } catch (Exception e) {

            if(Objects.nonNull(exception)){
                exception.addSuppressed(e);
                throw exception;
            }

            throw Optional.of(e)
                    .filter(e1 -> e1 instanceof RuntimeException)
                    .map(e1 -> (RuntimeException) e1)
                    .orElseThrow(() -> new IllegalArgumentException("exception must be not empty"));
        } finally {
            finallyFunction.accept();
        }
    }

    private TryCatchUtil() {
        throw new IllegalStateException("Utility class");
    }
}
