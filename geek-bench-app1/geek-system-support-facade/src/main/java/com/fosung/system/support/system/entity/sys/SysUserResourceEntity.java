package com.fosung.system.support.system.entity.sys;

import com.fosung.system.support.system.dict.AppType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;

/**
 * 用户资源表实体对象
 */
@Entity
@Table(name="sys_user_resource")
@Setter
@Getter
public class SysUserResourceEntity extends AppJpaBaseEntity  {

	/**
	 * 用户id
	 */
	@Column(name="user_id")
	private Long userId ;


	/**
	 * 资源id
	 */
	@Column(name="resource_id")
	private Long resourceId ;


	/**
	 * 资源id
	 */
	@Column(name="role_id")
	private Long roleId ;

	@Transient
	private String resourceName;

	/**
	 * 项目id
	 */
	@Column(name="project_id")
	private Long projectId ;

	/**
	 * 描述: 资源树，前端回显用
	 * @author fuhao
	 * @date 2021/12/17 14:46
	 **/
	@Column(name = "resource_tree")
	private String resourceTree;

	@Transient
	private Long appId;

	@Transient
	private String appName;

	@Transient
	@Enumerated(EnumType.STRING)
	private AppType appType;
}