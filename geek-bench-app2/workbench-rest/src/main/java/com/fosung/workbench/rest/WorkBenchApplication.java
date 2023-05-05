package com.fosung.workbench.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(value = "com.fosung.*")
public class WorkBenchApplication  {
    public static void main(String[] args) {
        new SpringApplication(WorkBenchApplication.class).run(args);
    }

}
