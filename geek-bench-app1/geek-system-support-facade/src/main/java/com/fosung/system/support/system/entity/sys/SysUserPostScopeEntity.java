package com.fosung.system.support.system.entity.sys;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.system.support.system.anno.SysDict;
import com.fosung.system.support.system.util.Constant;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 岗位表实体对象
 */
@Entity
@Table(name="sys_user_post_scope")
@Setter
@Getter
public class SysUserPostScopeEntity extends AppJpaBaseEntity{

	/**
	 * 描述: 用户id
	 * @author fuhao
	 * @date 2022/2/14 11:20
	 **/
	@Column(name="user_id",nullable = false)
	private Long userId ;

	/**
	 * 组织id
	 */
	@Column(name="org_id",nullable = false)
	private Long orgId ;

	/**
	 * 组织编码
	 */
	@Column(name="org_code",nullable = false)
	private String orgCode ;

	/**
	 * 组织名称
	 */
	@Column(name="org_name",nullable = false)
	private String orgName ;

	/**
	 * 岗位id
	 */
	@Column(name="post_id",nullable = false)
	private Long postId ;

	/**
	 * 描述: 岗位编码
	 * @author fuhao
	 * @date 2022/2/16 14:06
	 **/
	@Column(name="post_code",nullable = false)
	private String postCode;

	/**
	 * 岗位名称
	 */
	@Column(name="post_name",nullable = false)
	private String postName ;

	/**
	 * 租户id
	 */
	@Column(name="project_id",nullable = false)
	private Long projectId ;

	/**
	 * 描述: 租户名称
	 * @author fuhao
	 * @date 2022/2/8 14:41
	 **/
	@Transient
	private String projectName;

	/**
	 * 描述: 身份属性
	 * @author fuhao
	 * @date 2022/2/8 14:45
	 **/
	@SysDict(dictType = Constant.IDATTRS)
	@Column(name = "identify")
	private String identify;

	/**
	 * 描述: 岗位名称（肥城项目）
	 * @author fuhao
	 * @date 2022/2/16 14:07
	 **/
	@Transient
	private String positionInfoName;

	/**
	 * 描述: 岗位编码（肥城项目）
	 * @author fuhao
	 * @date 2022/2/16 14:08
	 **/
	@Transient
	private String positionInfoCode;

	/**
	 * 描述: 灯塔党组织id（肥城项目）
	 * @author fuhao
	 * @date 2022/2/17 15:14
	 **/
	@Column(name = "dt_org_id")
	private String dtOrgId;

	public SysUserPostScopeEntity(){}

	public SysUserPostScopeEntity(Long userId, Long orgId, String orgCode, String orgName, Long postId, String postCode, String postName, Long projectId, String identify, String dtOrgId) {
		this.userId = userId;
		this.orgId = orgId;
		this.orgCode = orgCode;
		this.orgName = orgName;
		this.postId = postId;
		this.postCode = postCode;
		this.postName = postName;
		this.projectId = projectId;
		this.identify = identify;
		this.dtOrgId = dtOrgId;
	}
}