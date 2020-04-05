package com.honeycomb.resilience4j.demo.exception;

import java.util.function.Predicate;

public class RetryOnExceptionPredicate implements Predicate<Throwable> {

    @Override
    public boolean test(Throwable throwable) {
        if (throwable.getCause() instanceof BusinessAException) return false;
        else return true;
    }
}