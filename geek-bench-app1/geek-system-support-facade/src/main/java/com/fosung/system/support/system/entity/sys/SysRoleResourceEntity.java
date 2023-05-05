package com.fosung.system.support.system.entity.sys;

import java.util.List;

import com.fosung.system.support.system.dict.AppType;
import com.fosung.system.support.system.dict.RoleSource;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;

/**
 * 角色资源关联表实体对象
 */
@Entity
@Table(name="sys_role_resource")
@Setter
@Getter
public class SysRoleResourceEntity extends AppJpaBaseEntity    {


	/**
	 * 角色id
	 */
	@Column(name="role_id",nullable = false)
	private Long roleId ;

	/*
	 * 描述: 角色名称
	 * @author fuhao
	 * @date 2021/11/27 15:55
	 **/
	@Transient
	private String roleName;


	/**
	 * 资源id
	 */
	@Column(name="resource_id",nullable = false)
	private Long resourceId ;

	/*
	 * 描述: 多个资源
	 * @author fuhao
	 * @date 2021/12/3 15:50
	 **/
	@Transient
	private List<Long> resources;

	/**
	 * 描述: 资源名称
	 * @author fuhao
	 * @date 2021/11/27 15:56
	 **/
	@Transient
	private String menuName;

	/**
	 * 所属项目id
	 */
	@Column(name = "project_id",nullable = false)
	private Long projectId;

	/**
	 * 所属应用id
	 */
	@Column(name = "app_id",nullable = false)
	private Long appId;

	/**
	 * 描述: 应用名称
	 * @author fuhao
	 * @date 2021/11/27 15:56
	 **/
	@Transient
	private String appName;

	/**
	 * 资源层级
	 */
	@Transient
	private String level;

	/**
	 * 描述: 来源：角色管理、用户管理
	 * @author fuhao
	 * @date 2021/12/20 10:25
	 **/
	@Column(name = "source")
	private RoleSource source;

	@Transient
	private Long parentId;

	@Transient
	private Boolean checkFlag;

	@Transient
	@Enumerated(EnumType.STRING)
	private AppType appType;


	public SysRoleResourceEntity() {

	}

	public SysRoleResourceEntity(Long roleId, Long resourceId) {
		this.roleId = roleId;
		this.resourceId = resourceId;
	}
}