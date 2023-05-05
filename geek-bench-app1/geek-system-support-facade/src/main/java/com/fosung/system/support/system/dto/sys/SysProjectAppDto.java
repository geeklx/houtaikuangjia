package com.fosung.system.support.system.dto.sys;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;

/**
 * @version V1.0
 * @Description：应用项目关联表Dto
 */
@Data
public class SysProjectAppDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 项目id
	 */
	private Long projectId ;
   /**
	 * 应用id
	 */
	private Long appId ;

}