package com.geek.workbench.entity.config;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 描述:  目录树配置实体对象
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Entity
@Table(name="config_category")
@Setter
@Getter
public class ConfigCategory extends AppJpaBaseEntity {

	/**
	 * 目录树名称
	 */
	@Column(name="category_name")
	@NotBlank(message = "目录树名称不能为空")
	@Size(min = 0, max = 32, message = "目录树名称长度不能超过32个字符")
	private String categoryName ;


	/**
	 * 目录树类型
	 */
	@NotBlank(message = "目录树类型不能为空")
	@Size(min = 0, max = 32, message = "目录树类型长度不能超过32个字符")
	@Column(name="category_type" , nullable = false , updatable = false)
	private String categoryType ;

	/**
	 * 目录树类型名称
	 */
	@Column(name="category_type_name")
	@NotBlank(message = "目录树类型名称不能为空")
	@Size(min = 0, max = 255, message = "目录树类型名称长度不能超过255个字符")
	private String categoryTypeName ;


	/**
	 * 目录树地址
	 */
	@Column(name="category_addr")
	@NotBlank(message = "目录树地址不能为空")
	@Size(min = 0, max = 255, message = "目录树地址长度不能超过255个字符")
	@URL
	private String categoryAddr ;


	/**
	 * 逻辑删除
	 */
	@Column(name="del")
	private Boolean del = Boolean.FALSE ;


}