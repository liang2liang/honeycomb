package com.honeycomb.drools;

import lombok.Data;

/**
 * @author maoliang
 * @version 1.0.0
 */
@Data
public class Car {

    public int discount = 100;

    private Person person;
}
