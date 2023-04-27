package com.geek.workbench.entity.config;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 描述:  接口参数配置表实体对象
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Entity
@Table(name="config_api_params")
@Setter
@Getter
public class ConfigApiParamsEntity extends AppJpaBaseEntity {

	/**
	 * 接口id
	 */
	@Column(name="api_id")
	private Long apiId ;


	/**
	 * 参数key
	 */
	@Column(name="param_key")
	private String paramKey ;


	/**
	 * 参数值
	 */
	@Column(name="param_value")
	private String paramValue ;


}