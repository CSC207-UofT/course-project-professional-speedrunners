package com.boba.bobabuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.boba")
public class BobabuddyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BobabuddyApplication.class, args);
    }

}
