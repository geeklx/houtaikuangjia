package com.geek.workbench.dto.config;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import com.geek.workbench.dict.BindCategoryType;
import com.geek.workbench.dict.BindType;
import com.geek.workbench.entity.config.ConfigApiEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

/**
 * 描述:  接口配置表dto
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ConfigApiDto extends AppBasePageParam {

	/**
	 * id
	 */
    private  Long id;

	/**
	 * api 组id
	 */
    private Long apiGroupId;

	/**
	 * 接口名称
	 */
	private String name ;

	/**
	 * 接口分类
	 */
	@Enumerated(EnumType.STRING)
	private BindCategoryType apiCategory ;

	/**
	 * 接口类型
	 */
	@Enumerated(EnumType.STRING)
	private BindType apiType ;

	/**
	 * 接口备注
	 */
	private String remark ;
	/**
	 * 接口编号  directoryTree 或 directoryTreeDetail
	 */
	private String apiCode ;

	/**
	 * 描述:  接口集合
	 */
	private List<ConfigApiEntity> apiList;

}