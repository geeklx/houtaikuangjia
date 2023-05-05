package com.fosung.system.support.system.dto.sys;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;

/**
 * @version V1.0
 * @Description：组织机构Dto
 */
@Data
public class SysOrgDto extends AppBasePageParam {

	private Long id;
	/**
	 * 是否删除
	 */
	private Boolean del = Boolean.FALSE ;

	/**
	 * 机构名称
	 */
	private String orgName ;

	private String orgNameL;



	/**
	 * 机构编码
	 */
	private String orgCode ;


	/**
	 * 父节点
	 */
	private Long parentId ;




	/**
	 * 是否有子节点
	 */
	private Boolean hasChildren ;


	/**
	 * 层级
	 */
	private String level ;




	/**
	 * 组织状态
	 */
	private String status ;


	/**
	 * 组织类型
	 */
	private String orgType ;


	/**
	 * 系统id
	 */
	private Long projectId ;

	/**
	 * 用户id
	 */
	private Long userId;

	/**
	 * 组织id
	 */
	private Long roleId;


}