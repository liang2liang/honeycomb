package com.honeycomb.util.anno;

/**
 * @author maoliang
 */
@FunctionalInterface
public interface SupplierWithException<R> {

    /**
     * Gets a result.
     *
     * @return a result
     * @throws Exception 受检异常
     */
    R get() throws Exception;
}
