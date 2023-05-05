package com.fosung.workbench.entity.terminal;

import java.util.Date;

import com.fosung.workbench.dict.TerminalAppCategoryType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.config.AppProperties;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;

/**
 * 终端分类应用信息表实体对象
 */
@Entity
@Table(name="terminal_category_app")
@Setter
@Getter
public class TerminalCategoryAppEntity extends AppJpaBaseEntity {
	/**
     * 是否删除
     */
//    @Column
//    private Boolean del = Boolean.FALSE ;

	/**
	 * 终端id
	 */
	@Column(name="terminal_id")
	@NotNull(message = "终端主键不能为空")
	private Long terminalId ;


	/**
	 * 分类编码
	 */
	@Column(name="category_code")
	private String categoryCode ;


	/**
	 * 应用配置id
	 */
	@Column(name="app_config_id")
	private Long appConfigId ;


	/**
	 * 分类类型（常用、其他）
	 */
	@Column(name="category_type")
	@Enumerated(EnumType.STRING)
	private TerminalAppCategoryType categoryType ;

	/*
	 * 排序不允许修改
	 */
	@Column(name="disabled_num")
	private boolean disabledNum;
	/*
	 * 排序不允许修改
	 */
	@Column(name="num")
	private Integer num;


	@Transient
	private Long configId;



}