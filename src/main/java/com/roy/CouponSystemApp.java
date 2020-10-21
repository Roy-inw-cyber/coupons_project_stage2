package com.roy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CouponSystemApp {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(CouponSystemApp.class, args);
        Test test = context.getBean(Test.class);
        test.runTests();
    }
}

