package com.geek.workbench.config;


import groovy.util.logging.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ComponentScan(basePackages = {"com.fosung.workbench.service"
        ,"com.fosung.workbench.config"
        ,"com.fosung.workbench.util"
})
public class WorkBenchServiceAutoConfiguration {
}
