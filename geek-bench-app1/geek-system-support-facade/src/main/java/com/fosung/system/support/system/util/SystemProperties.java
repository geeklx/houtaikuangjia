package com.fosung.system.support.system.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
@Setter
@Getter
public class SystemProperties {

	private String passwordEncoder="default";

	private boolean enableMq = false;


}
