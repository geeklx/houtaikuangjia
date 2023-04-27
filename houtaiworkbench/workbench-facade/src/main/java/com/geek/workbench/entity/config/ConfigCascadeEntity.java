package com.geek.workbench.entity.config;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 描述:  配置级联字典实体对象
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Entity
@Table(name="config_cascade")
@Setter
@Getter
public class ConfigCascadeEntity extends AppJpaBaseEntity {

	/**
	 * 名称
	 */
	@Column(name="config_label")
	private String configLabel ;


	/**
	 * 值
	 */
	@Column(name="config_value")
	private String configValue ;


	/**
	 * 配置状态
	 */
	@Column(name="config_status")
	private String configStatus ;


	/**
	 * 父值id
	 */
	@Column(name="parent_id")
	private Long parentId ;

	/**
	 * 序号
	 */
	@Column(name="num")
	private Integer num;

	/**
	 * 配置类型
	 */
	@Column(name="config_type")
	private String configType ;

	/**
	 * 路径
	 */
	@NotBlank(message = "路径不能为空")
	@Size(min = 0, max = 255, message = "路径长度不能超过255个字符")
	@Column(name="path")
	private String path ;
}