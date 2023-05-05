package com.fosung.workbench.configurecenter.config;

import groovy.util.logging.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ComponentScan(basePackages = {"com.fosung.workbench.configurecenter.service",
        "com.fosung.workbench.common"})
public class WorkBenchConfigAutoConfiguration {
}
