package com.fosung.system.support.system.config;

import com.fosung.framework.common.secure.password.AppPasswordEncoderDefault;
import com.fosung.framework.common.util.UtilString;
import com.fosung.system.support.system.util.SystemProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@ComponentScan(basePackages = "com.fosung.system.support.*.service")
@EnableConfigurationProperties(SystemProperties.class)
public class SystemServiceAutoConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder(SystemProperties systemProperties) {
        if (UtilString.equals(systemProperties.getPasswordEncoder(),"cloud")){
            log.info("---加密工具类：AppCloudPasswordEncoder.class");
          return new AppCloudPasswordEncoder();

        }else {
            log.info("---加密工具类：AppPasswordEncoderDefault.class");
            return new AppPasswordEncoderDefault();
        }
    }

}
