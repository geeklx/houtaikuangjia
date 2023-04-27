package com.geek.system.support.system.entity.sys;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;
import java.util.Map;

/**
 * 用户角色关联表实体对象
 */
@Entity
@Table(name="sys_role_scop")
@Setter
@Getter
public class SysRoleScopEntity extends AppJpaBaseEntity  {

	/**
	 * 角色id
	 */
	@Column(name="role_id",nullable = false)
	private Long roleId ;


	/**
	 * 管理角色id
	 */
	@Column(name="role_scop_id",nullable = false)
	private Long roleScopId ;

	/**
	 * 描述: 角色名称
	 * @author fuhao
	 * @date 2021/12/2 16:39
	 **/
	@Transient
	private String roleName;

	/**
	 * 描述: 角色类型
	 * @author fuhao
	 * @date 2021/12/2 17:53
	 **/
	@Transient
	private String roleType;

    /**
     * 项目id
	 */
	@Column(name = "project_id")
	private Long projectId;

	/**
	 * 排序
	 */
	@Transient
	private int num = 99;


	/**
	 * 是否选中
	 */
	@Transient
	private Boolean checkFlag;

	/**
	 * 描述: 管理范围
	 * @author fuhao
	 * @date 2021/12/7 16:49
	 **/
	@Transient
	private List<SysUserRoleScopEntity> orgs;

	/**
	 * 描述: 资源树
	 * @param null
	 * @return
	 * @author fuhao
	 * @date 2021/12/7 16:56
	 **/
	@Transient
	private List<Map<String, Object>> resources;

	/**
	 * 描述: 默认角色绑定的资源
	 * @author fuhao
	 * @date 2021/12/20 11:04
	 **/
	@Transient
	private List<Map<String, Object>> roleBindResources;

}