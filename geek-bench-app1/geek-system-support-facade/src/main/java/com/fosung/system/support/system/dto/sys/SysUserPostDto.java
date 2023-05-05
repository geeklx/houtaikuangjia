package com.fosung.system.support.system.dto.sys;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;

/**
 * @version V1.0
 * @Description：用户与岗位关联表Dto
 */
@Data
public class SysUserPostDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 用户id
	 */
	private Long userId ;
   /**
	 * 岗位id
	 */
	private Long postId ;
   /**
	 * 项目id
	 */
	private String projectId ;

}