package com.itm.space;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class ApplicationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationServiceApplication.class, args);
    }
}
