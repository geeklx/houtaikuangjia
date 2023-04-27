package com.geek.system.support.system.entity.sys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;

/**
 * 应用系统分类标签实体对象
 */
@Entity
@Table(name="sys_application_category")
@Setter
@Getter
public class SysApplicationCategoryEntity extends AppJpaBaseEntity {

	/**
	 * 分类名称
	 */
	@Column(name="category_name",nullable = false)
	private String categoryName ;


	/**
	 * 分类类型
	 */
	@Column(name="category_type",nullable = false)
	private String categoryType ;


	/**
	 * 排序
	 */
	@Column(name="num")
	private Integer num ;


}