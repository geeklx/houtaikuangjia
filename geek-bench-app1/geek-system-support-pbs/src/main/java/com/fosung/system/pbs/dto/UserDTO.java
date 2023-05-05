package com.fosung.system.pbs.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class UserDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String orgId;
	private String orgCode;
	//是否递归查询
	private Boolean hasChild;

	//不包含这些userIdList
	private List<String> excludeUserId;

	/** 一级角色ID */
	private List<String> roleId;

	/** 一级角色名称 */
	private List<String> roleName;

	/** 二级角色id*/
	private List<String> secondRoleId;

	/** 二级角色名称 */
	private List<String> secondRoleName;

	//流程模板配置角色参数
	private List<String> procDefParm;

	private int pagesize = 20;

	private int pagenum = 1;

	private String key;
}
