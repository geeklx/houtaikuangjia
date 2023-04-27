package com.geek.system.support.system.dto.sys;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @version V1.0
 * @Description：应用项目关联表Dto
 */
@EqualsAndHashCode(callSuper = true)
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