package com.fosung.system.support.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(value = "com.fosung.*")
public class SystemApplication {
    public static void main(String[] args) {
        new SpringApplication(SystemApplication.class).run(args);
    }
}
