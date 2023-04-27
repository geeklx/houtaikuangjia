package com.geek.system.pbs.config;

import com.geek.system.pbs.common.CloudProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.geek.system.pbs")
@EnableConfigurationProperties({ PbsClientProperties.class, CloudProperties.class, DingTalkProperties.class})
public class PbsClientAutoConfiguration {

}
