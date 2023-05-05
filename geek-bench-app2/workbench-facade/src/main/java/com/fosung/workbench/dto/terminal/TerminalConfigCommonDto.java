package com.fosung.workbench.dto.terminal;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;

/**
 * @version V1.0
 * @Description：终端通用配置Dto
 */
@Data
public class TerminalConfigCommonDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 配置编码
	 */
	private String configCode ;
   /**
	 * 配置类型
	 */
	private String configType ;
   /**
	 * 值
	 */
	private String configValue ;
   /**
	 * 终端id
	 */
	private String terminalId ;

}