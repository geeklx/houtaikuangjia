package com.fosung.workbench.dto.config;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @version V1.0
 * @Description：接口参数配置表dto
 */
@Data
public class ConfigApiParamsDto{
	
   @NotEmpty
   private  Long id;
	

	private String apiId ;


	private String paramKey ;


	private String paramValue ;


}