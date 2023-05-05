package com.fosung.system.support.system.entity.sys;

import com.fosung.system.support.system.dict.RoleType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;

/**
 * 用户角色关联表实体对象
 */
@Entity
@Table(name="sys_user_role")
@Setter
@Getter
public class SysUserRoleEntity extends AppJpaBaseEntity    {


	/**
	 * 用户id
	 */
	@Column(name="user_id",nullable = false)
	private Long userId ;


	/**
	 * 角色id
	 */
	@Column(name="role_id",nullable = false)
	private Long roleId ;


	/**
	 * 角色标识
	 */
	@Column(name="role_mark")
	private String roleMark ;

	/**
	 * 描述: 角色类型
	 * @author fuhao
	 * @date 2021/12/2 17:58
	 **/
	@Transient
	@Enumerated(EnumType.STRING)
	private RoleType roleType;


	/**
	 * 系统id
	 */
	@Column(name="project_id",nullable = false)
	private Long projectId ;


	/**
	 * 组织id
	 */
//	@Column(name="org_id")
//	private Long orgId ;


	/**
	 * 组织编码
	 */
//	@Column(name="org_code")
//	private String orgCode ;

	public SysUserRoleEntity(Long userId, Long roleId,Long projectId) {
		this.userId = userId;
		this.roleId = roleId;
		this.projectId = projectId;
	}

	public SysUserRoleEntity() {
	}
}