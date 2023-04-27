package com.geek.workbench.dto.terminal;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @version V1.0
 * @Description：终端通用配置Dto
 */
@EqualsAndHashCode(callSuper = true)
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