package com.honeycomb.resilience4j.demo.exception;

import java.util.function.Predicate;

public class RetryOnResultPredicate implements Predicate {

    @Override
    public boolean test(Object o) {
        return o == null ? true : false;
    }
}