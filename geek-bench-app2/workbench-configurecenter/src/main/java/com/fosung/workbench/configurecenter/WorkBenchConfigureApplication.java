package com.fosung.workbench.configurecenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class WorkBenchConfigureApplication {
    public static void main(String[] args) {
        new SpringApplication(WorkBenchConfigureApplication.class).run(args);
    }
}
