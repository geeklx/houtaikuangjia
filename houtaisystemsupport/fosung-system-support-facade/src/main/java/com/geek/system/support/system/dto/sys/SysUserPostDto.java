package com.geek.system.support.system.dto.sys;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @version V1.0
 * @Description：用户与岗位关联表Dto
 */
@EqualsAndHashCode(callSuper = true)
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