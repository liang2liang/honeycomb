package com.honeycomb.java.base.proxy;


import com.honeycomb.java.base.proxy.anno.Select;

public interface Hello {

    @Select("select * from table where name=#{name}")
    void say(String name);
}
