package com.geek.system.support.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SystemOutServerApplication {
    public static void main(String[] args) {
        new SpringApplication(SystemOutServerApplication.class).run(args);
    }
}
