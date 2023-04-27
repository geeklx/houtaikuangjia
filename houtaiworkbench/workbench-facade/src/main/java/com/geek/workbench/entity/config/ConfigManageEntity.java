package com.geek.workbench.entity.config;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;
import com.geek.workbench.dict.ConfigType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 描述:  配置管理表实体对象
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Entity
@Table(name="config_manage")
@Setter
@Getter
public class ConfigManageEntity extends AppJpaBaseEntity implements AppJpaSoftDelEntity {

	/**
	 * 配置名称
	 */
	@NotBlank(message = "配置名称不能为空")
	@Size(min = 0, max = 255, message = "配置名称长度不能超过255个字符")
	@Column(name="config_name")
	private String configName ;


	/**
	 * 配置类型
	 */
	@Column(name="config_type")
	@Enumerated(EnumType.STRING)
	private ConfigType configType ;


	/**
	 * 配置类型
	 */
	@NotBlank(message = "配置类型名称不能为空")
	@Size(min = 0, max = 255, message = "配置类型名称长度不能超过255个字符")
	@Column(name="config_type_name")
	private String configTypeName;


	/**
	 * 配置平台
	 */
	@NotBlank(message = "配置平台不能为空")
	@Size(min = 0, max = 255, message = "配置平台长度不能超过255个字符")
	@Column(name="config_platform")
	private String configPlatform ;


	/**
	 * 配置平台
	 */
	@NotBlank(message = "配置平台名称不能为空")
	@Size(min = 0, max = 255, message = "配置平台名称长度不能超过255个字符")
	@Column(name="config_platform_name")
	private String configPlatformName ;


	/**
	 * 是否删除
	 */
	@Column(name="del")
	private Boolean del = Boolean.FALSE;


}