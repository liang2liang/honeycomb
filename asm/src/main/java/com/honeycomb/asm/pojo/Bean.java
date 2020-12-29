package com.honeycomb.asm.pojo;

import com.honeycomb.annotation.processor.Get;
import com.honeycomb.annotation.processor.NotNull;
import com.honeycomb.annotation.processor.Set;
import com.honeycomb.annotation.processor.TakeTime;

import java.util.concurrent.TimeUnit;

/**
 * @author maoliang
 */
@Get
@Set
public class Bean {

    private int f;

    private String name;

    @TakeTime(tag = "mmmm")
    public void checkAndSetF(@NotNull(message = "hahha") Integer f) {
        if (f >= 21) {
            this.f = f;
            System.out.println("ok");
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, InterruptedException {
        final Bean bean = new Bean();
        while(true) {
            new Bean().checkAndSetF(null);
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
