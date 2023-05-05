package com.fosung.workbench.entity.busi;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 组织机构管理实体对象
 */
@Entity
@Table(name="busi_org_adm")
@Setter
@Getter
public class BusiOrgAdmEntity extends AppJpaBaseEntity implements AppJpaSoftDelEntity  {
	/**
     * 是否删除
     */
    @Column
    private Boolean del = Boolean.FALSE ;

	/**
	 * 组织id
	 */
	@Column(name="org_id")
	private String orgId ;


	/**
	 * 组织name
	 */
	@Column(name="org_name")
	private String orgName ;


	/**
	 * 组织code
	 */
	@Column(name="org_code")
	private String orgCode ;


	/**
	 * 地域id
	 */
	@Column(name="adm_id")
	private String admId ;


	/**
	 * 地域name
	 */
	@Column(name="adm_name")
	private String admName ;


	/**
	 * 地域code
	 */
	@Column(name="adm_code")
	private String admCode ;
	/**
	 * 子节点
	 */
	@Column(name="has_children")
	private String hasChildren ;
	/**
	 * 组织父节点
	 */
	@Column(name="org_parent_id")
	private String orgParentId ;

	/**
	 * 地域父节点
	 */
	@Column(name="adm_parent_id")
	private String admParentId ;
	/**
	 * 用户id
	 */
	@Column(name="user_id")
	private String userId ;
	/**
	 * 是否选中
	 */
	@Column(name="enable")
	private Boolean enable ;
	/**
	 * 级别
	 */
	@Column(name="level")
	private String level ;
}