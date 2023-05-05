package com.fosung.system.support.system.entity.sys;

import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;
import com.fosung.system.support.system.dict.RoleStatus;
import com.fosung.system.support.system.dict.RoleType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;

/**
 * 角色表实体对象
 */
@Entity
@Table(name="sys_role")
@Setter
@Getter
public class SysRoleEntity extends AppJpaBaseEntity  implements AppJpaSoftDelEntity {



	@Column(name="id")
	private Long id;
	/**
	 * 角色名
	 */
	@Column(name="role_name",nullable = false)
	private String roleName ;


	/**
	 * 角色编码
	 */
	@Column(name="role_code",nullable = false)
	private String roleCode ;


	/**
	 * 角色描述
	 */
	@Column(name="remark")
	private String remark ;


	/**
	 * 帐号状态（0正常 1删除）
	 */
	@Column(name="del")
	private Boolean del = Boolean.FALSE;


	/**
	 * 父节点
	 */
//	@Column(name="parent_id")
//	private Long parentId ;


	/**
	 *   角色状态
	 */
	@Column(name="status")
	@Enumerated(EnumType.STRING)
	private RoleStatus status = RoleStatus.normal;


	/**
	 * 系统id
	 */
	@Column(name="project_id",nullable = false)
	private Long projectId ;

	/*
	 * 描述: 项目名称
	 * @author fuhao
	 * @date 2021/11/26 10:57
	 **/
	@Transient
	private String projectName ;

	/**
	 * 层级
	 */
//	@Column(name="level")
//	private String level ;


	/**
	 * 排序
	 */
	@Column(name="num",nullable = false)
	private Integer num ;

	/**
	 * 角色类型
	 */
	@Column(name = "role_type")
	@Enumerated(EnumType.STRING)
	private RoleType roleType;

	/**
	 * 描述: 是否选中
	 * @author fuhao
	 * @date 2021/11/27 10:29
	 **/
	@Transient
	private Boolean checkFlag;

}