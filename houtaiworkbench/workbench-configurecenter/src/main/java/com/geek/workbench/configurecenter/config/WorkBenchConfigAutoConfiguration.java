package com.geek.workbench.configurecenter.config;

import groovy.util.logging.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ComponentScan(basePackages = {"com.geek.workbench.configurecenter.service",
        "com.geek.workbench.common"})
public class WorkBenchConfigAutoConfiguration {
}
