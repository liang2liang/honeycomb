package com.honeycomb.java.base.serialized;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = -6365545402343723343L;

    private final String name;

    User(String name) {
        this.name = name;
    }
}