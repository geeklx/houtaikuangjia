package com.geek.workbench.dto.config;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @version V1.0
 * @Description：配置级联字典dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ConfigCascadeDto extends AppBasePageParam {

	/**
	 * 主键
	 */
	private Long id ;

	/**
	 * 名称
	 */
	private String configLabel ;


	/**
	 * 值
	 */
	private String configValue ;


	/**
	 * 配置状态
	 */
	private String configStatus ;


	/**
	 * 父值id
	 */
	private Long parentId ;

	/**
	 * 序号
	 */
	private Integer num;

	/**
	 * 配置类型
	 */
	private String configType ;


}