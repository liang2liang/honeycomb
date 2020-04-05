package com.honeycomb.resilience4j.demo.exception;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class BusinessBException extends RuntimeException {
    public BusinessBException(String message) {
        super(message);
    }
}
