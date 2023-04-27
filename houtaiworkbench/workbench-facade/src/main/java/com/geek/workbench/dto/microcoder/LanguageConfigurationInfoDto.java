package com.geek.workbench.dto.microcoder;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @version V1.0
 * @Description：语言配置信息Dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LanguageConfigurationInfoDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 语言类型
	 */
	private String languageType ;
   /**
	 * 终端id
	 */
	private String terminalId ;

}