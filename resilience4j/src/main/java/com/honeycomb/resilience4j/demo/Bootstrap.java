package com.honeycomb.resilience4j.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author maoliang
 * @version 1.0.0
 */
@SpringBootApplication()
//    @Configuration
//@EnableAutoConfiguration
//    @PropertySource("application.yml")
public class Bootstrap {

    public static void main(String[] args) {
        SpringApplication.run(Bootstrap.class, args);
    }

//    public static void main(String[] args) throws Throwable {
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.honeycomb");
//        CircuitBreakerServiceImpl bean = context.getBean(CircuitBreakerServiceImpl.class);
//        bean.circuitBreakerNotAOP1();
//        bean.circuitBreakerNotAOP1();
//        bean.circuitBreakerNotAOP1();
//        System.in.read();
//    }
}
