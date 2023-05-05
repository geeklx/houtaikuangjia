package com.fosung.workbench.entity.application;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 应用系统分类标签实体对象
 */
@Entity
@Table(name="application_category")
@Setter
@Getter
public class ApplicationCategoryEntity extends AppJpaBaseEntity implements AppJpaSoftDelEntity  {

	/**
     * 是否删除
     */
    @Column
    private Boolean del = Boolean.FALSE ;

	/**
	 * 分类名称
	 */
	@Column(name="category_name")
	private String categoryName ;


	/**
	 * 分类类型
	 */
	@Column(name="category_type")
	private String categoryType ;


	/**
	 * 排序
	 */
	@Column(name="num")
	private Integer num ;

}