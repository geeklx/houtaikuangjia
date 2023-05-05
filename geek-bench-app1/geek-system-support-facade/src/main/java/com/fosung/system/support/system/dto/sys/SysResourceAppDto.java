package com.fosung.system.support.system.dto.sys;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;

import java.util.Date;

/**
 * @version V1.0
 * @Description：应用资源信息Dto
 */
@Data
public class SysResourceAppDto extends AppBasePageParam {


	/**
	 *应用id
	 */
	private Long appId;

	/**
	 * 资源路径
	 */
	private String resourceUrl;



	/**
	 * 资源名称
	 */
	private String resourceName;

}