package com.honeycomb.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author maoliang
 * @version 1.0.0
 */
@ServletComponentScan
@SpringBootApplication
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public class BootstrapApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(BootstrapApplication.class);
        springApplication.setWebApplicationType(WebApplicationType.REACTIVE);
        springApplication.run(args);
    }
}
