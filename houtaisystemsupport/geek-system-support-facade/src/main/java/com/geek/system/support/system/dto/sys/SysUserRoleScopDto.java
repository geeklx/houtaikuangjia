package com.geek.system.support.system.dto.sys;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import com.geek.system.support.system.dict.ShelvesType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

/**
 * @version V1.0
 * @Description：用户角色关联表Dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserRoleScopDto extends AppBasePageParam {
	
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
	 * 组织id
	 */
	private Long orgId ;

	/**
	 * 项目id
	 */
	private Long projectId;

	/**
	 * 描述: 角色
	 * @author fuhao
	 * @date 2021/12/17 17:19
	 **/
	private List<UserShelvesDto> roles;

	/**
	 * 描述: 授权类型
	 * @author fuhao
	 * @date 2021/12/6 9:21
	 **/
	@Enumerated(EnumType.STRING)
	private ShelvesType shelvesType;

	private Long appId;

}