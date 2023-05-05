package com.fosung.system.support.system.entity.sys;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 用户角色关联表实体对象
 */
@Entity
@Table(name="sys_user_role_scop")
@Setter
@Getter
public class SysUserRoleScopEntity extends AppJpaBaseEntity  {

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
	 * 组织id
	 */
	@Column(name="org_id")
	private Long orgId ;

	@Transient
	private String orgName;

	@Transient
	private String orgCode;

	/**
	 * 项目id
	 */
	@Column(name = "project_id")
	private Long projectId;

	/**
	 * 描述: 组织父id，前端回显用
	 * @author fuhao
	 * @date 2021/12/17 14:45
	 **/
	@Column(name = "parent_org_id")
	private String parentOrgId;

}