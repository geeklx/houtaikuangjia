package com.fosung.system.support.system.dto.sys;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;

/**
 * @version V1.0
 * @Description：用户资源表Dto
 */
@Data
public class SysUserResourceDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 用户id
	 */
	private Long userId ;
   /**
	 * 资源id
	 */
	private Long resourceId ;

}