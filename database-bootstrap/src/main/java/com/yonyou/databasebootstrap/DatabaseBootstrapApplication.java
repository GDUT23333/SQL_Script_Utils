package com.yonyou.databasebootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
    "com.yonyou.databasebootstrap"
})
public class DatabaseBootstrapApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatabaseBootstrapApplication.class, args);
    }

}
