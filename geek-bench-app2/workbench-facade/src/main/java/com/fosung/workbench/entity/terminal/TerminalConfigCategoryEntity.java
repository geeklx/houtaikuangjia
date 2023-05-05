package com.fosung.workbench.entity.terminal;

import java.util.Date;
import java.util.List;

import com.fosung.workbench.AppBean.AppInfo;
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
 * 工作台应用分类实体对象
 */
@Entity
@Table(name="terminal_config_category")
@Setter
@Getter
public class TerminalConfigCategoryEntity extends AppJpaBaseEntity implements AppJpaSoftDelEntity  {
	/**
     * 是否删除
     */
    @Column
    private Boolean del = Boolean.FALSE ;

	/**
	 * 分类编码
	 */
	@Column(name="code")
	private String code ;


	/**
	 * 分类名称
	 */
	@Column(name="name")
	private String name ;


	/**
	 * 状态
	 */
	@Column(name="status")
	private Boolean status ;


	/**
	 * 排序
	 */
	@Column(name="num")
	private Integer num ;


	/**
	 * 分类类型
	 */
	@Column(name="type")
	@Enumerated(EnumType.STRING)
	private TerminalAppCategoryType type ;


	/**
	 * 图标
	 */
	@Column(name="logo_url")
	private String logoUrl ;

	/**
	 * 终端id
	 */
	@Column(name = "terminal_id")
	@NotNull(message = "终端主键不能为空")
	private Long terminalId;

	/**
	 * 父id
	 */
	@Column(name = "parent_id")
	private Long parentId;

	/**
	 * 描述
	 */
	@Column(name = "remark")
	private String remark;


	/**
	 * 授权地域
	 */
	@Column(name = "area")
	private String area;
	/**
	 * 底部导航
	 */
	@Column(name = "navigation_btm_id")
	private Long navigationBtmId;

	@Transient
	private List<AppInfo> apps;

	/*
		是否是不可删除的标识
	 */
	@Transient
	private Boolean disabledFlag;

	private String navigationBtmName;

}