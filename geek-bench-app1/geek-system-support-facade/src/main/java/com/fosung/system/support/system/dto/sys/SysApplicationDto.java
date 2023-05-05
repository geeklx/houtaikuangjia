package com.fosung.system.support.system.dto.sys;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import com.fosung.system.support.system.dict.AppType;
import lombok.Data;

/**
 * @version V1.0
 * @Description：应用系统信息Dto
 */
@Data
public class SysApplicationDto extends AppBasePageParam {

	/**
	 * 是否删除
	 */
	private Boolean del = Boolean.FALSE ;

	/**
	 * 应用标识
	 */
	private String appCode ;


	/**
	 * 应用名称
	 */
	private String appName ;



	/**
	 * 分类
	 */
	private Long categoryId ;


	/**
	 *目录树类型
	 */
	private String orgType;



	/**
	 * 应用系统类型
	 */
	private AppType appType;

	/**
	 * 开始时间
	 */
	private String startTime;

	/**
	 * 结束时间
	 */
	private String endTime;

	/**
	 * 租户id
	 */
	private Long projectId;

}