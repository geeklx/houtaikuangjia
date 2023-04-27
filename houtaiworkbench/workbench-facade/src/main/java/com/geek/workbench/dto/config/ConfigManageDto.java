package com.geek.workbench.dto.config;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import com.geek.workbench.dict.ConfigType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @version V1.0
 * @Description：配置管理表dto
 */
@EqualsAndHashCode(callSuper = true)
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