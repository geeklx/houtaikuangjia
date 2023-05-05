package com.fosung.workbench.dto.busi;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import com.fosung.workbench.entity.busi.BusiOrgAdmEntity;
import lombok.Data;

import java.util.List;

/**
 * 组织机构管理实体对象
 */
@Data
public class BusiOrgAdmDto extends AppBasePageParam {
	/**
	 * 统一行政树的ClientId
	 */
	 private String treeClientId;
	/**
	 * 上级id
	 */
	 private String amdId;
	/**
	 * 组织id
	 */
	private String orgId ;


	/**
	 * 组织name
	 */
	private String orgName ;


	/**
	 * 组织code
	 */
	private String orgCode ;


	/**
	 * 地域id
	 */
	//private String amdId ;


	/**
	 * 地域name
	 */
	private String admName ;


	/**
	 * 地域code
	 */
	private String admCode ;

	/**
	 * 地域code
	 */
	private Boolean enable ;
	/**
	 * 用户id
	 */
	private String userId ;
	/**
	 * 级别
	 */
	private String level ;
	/**
	 * 省
	 */
	private BusiOrgAdmEntity province;
	/**
	 * 市
	 */
	private BusiOrgAdmEntity city;
	/**
	 * 区
	 */
	private BusiOrgAdmEntity region;
	/**
	 * 街道
	 */
	private BusiOrgAdmEntity street;
	/**
	 * 市
	 */
	private List<BusiOrgAdmEntity> citys;
	/**
	 * 区
	 */
	private List<BusiOrgAdmEntity> regions;
	/**
	 * 街道
	 */
	private List<BusiOrgAdmEntity> streets;
}