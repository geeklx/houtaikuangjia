package com.geek.workbench.config;


import groovy.util.logging.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ComponentScan(basePackages = {"com.geek.workbench.service"
        ,"com.geek.workbench.config"
        ,"com.geek.workbench.util"
})
public class WorkBenchServiceAutoConfiguration {
}
