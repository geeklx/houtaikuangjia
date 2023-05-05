package com.fosung.system.support.system.entity.sys;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.system.support.system.dict.AppType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 用户角色关联表实体对象
 */
@Entity
@Table(name="sys_role_app")
@Setter
@Getter
public class SysRoleAppEntity extends AppJpaBaseEntity  {

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
	 * 管理角色id
	 */
	@Column(name="app_id",nullable = false)
	private Long appId ;

	/**
	 * 描述: 应用名称
	 * @author fuhao
	 * @date 2021/11/27 15:56
	 **/
	@Transient
	private String appName;

    /**
     * 项目id
	 */
	@Column(name = "project_id")
	private Long projectId;

	@Transient
	@Enumerated(EnumType.STRING)
	private AppType appType;

}