package com.geek.system.support.config;

import com.geek.system.support.system.util.SystemProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Slf4j
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.REGEX,pattern = "com.fosung.system.support.system.service.auth.*")})
@EnableConfigurationProperties(SystemProperties.class)
public class SystemClientAutoConfiguration {


}
