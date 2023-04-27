package com.geek.system.support.system.dto.sys;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @version V1.0
 * @Description：用户角色关联表Dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserRoleDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 用户id
	 */
	private Long userId ;
   /**
	 * 角色id
	 */
	private Long roleId ;
   /**
	 * 角色标识
	 */
	private String roleMark ;
   /**
	 * 系统id
	 */
	private String projectId ;
   /**
	 * 组织id
	 */
	private String orgId ;
   /**
	 * 组织编码
	 */
	private String orgCode ;

}