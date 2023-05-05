package com.fosung.workbench.dto.config;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import com.fosung.workbench.dict.ConfigType;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @version V1.0
 * @Description：配置管理表dto
 */
@Data
public class ConfigManageDto extends AppBasePageParam {
	

	private String configName ;


	@Enumerated(EnumType.STRING)
	private ConfigType configType ;


	private String configTypeName ;


	private String configPlatform ;


	private String configPlatformName ;


	private Long terminalId ;

	/**
	 * 开始时间
	 */
	private String startTime ;
	/**
	 * 结束时间
	 */
	private String endTime ;

}