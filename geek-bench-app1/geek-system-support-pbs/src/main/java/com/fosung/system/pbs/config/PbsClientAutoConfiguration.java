package com.fosung.system.pbs.config;

import com.fosung.system.pbs.common.CloudProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.fosung.system.pbs")
@EnableConfigurationProperties({ PbsClientProperties.class, CloudProperties.class, DingTalkProperties.class})
public class PbsClientAutoConfiguration {

}
