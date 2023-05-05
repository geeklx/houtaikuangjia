package com.fosung.workbench.dto.microcoder;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;

/**
 * @version V1.0
 * @Description：客户端样式配置信息Dto
 */
@Data
public class ClientStyleConfigurationInfoDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 客户端样式
	 */
	private String clientStyle ;
   /**
	 * 开始时间
	 */
	private String startTime ;
   /**
	 * 结束时间
	 */
	private String endTime ;
   /**
	 * 终端id
	 */
	private String terminalId ;

}