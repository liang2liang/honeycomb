package com.honeycomb.reactive.java9.store;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class Book {

    private final String name;

    public Book(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                '}';
    }
}
