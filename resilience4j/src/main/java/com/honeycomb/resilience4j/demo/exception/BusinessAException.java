package com.honeycomb.resilience4j.demo.exception;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class BusinessAException extends RuntimeException {
    public BusinessAException(String message) {
        super(message);
    }
}
