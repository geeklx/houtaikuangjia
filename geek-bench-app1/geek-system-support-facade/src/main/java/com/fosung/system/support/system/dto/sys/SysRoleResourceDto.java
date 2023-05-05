package com.fosung.system.support.system.dto.sys;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import com.fosung.system.support.system.entity.sys.SysRoleResourceEntity;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @version V1.0
 * @Description：角色资源关联表Dto
 */
@Data
public class SysRoleResourceDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 角色id
	 */
	private Long roleId ;
   /**
	 * 资源id
	 */
	private Long resourceId ;

	/***
	 * 描述: 项目id
	 * @author fuhao
	 * @date 2021/11/24 15:22
	 **/
	private Long projectId;
   /**
	 * 系统id
	 */
	private Long appId ;

	private Set<Long> resourceIds;

	/**
	 * 描述: 角色绑定信息
	 * @author fuhao
	 * @date 2021/11/29 16:49
	 **/
	private List<SysRoleResourceEntity> bindScopes;

}