package com.fosung.system.pbs.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.pbs.client")
public class PbsClientProperties {

	public String url;

	// 客户端默认的请求source
	private String source;

}
