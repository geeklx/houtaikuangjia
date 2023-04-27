package com.geek.workbench.dto.config;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 描述:  目录树配置dto
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ConfigCategoryDto extends AppBasePageParam {

	/**
	 * id
	 */
    private  Long id;

	/**
	 * 目录树名称
	 */
	private String categoryName ;

	/**
	 * 目录树类型
	 */
	private String categoryType ;

	/**
	 * 目录树地址
	 */
	private String categoryAddr ;

}