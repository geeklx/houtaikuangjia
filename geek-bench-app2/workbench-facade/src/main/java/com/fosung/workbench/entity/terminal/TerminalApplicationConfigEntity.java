package com.fosung.workbench.entity.terminal;


import com.fosung.workbench.dict.AppType;
import com.fosung.workbench.dict.DataSourceType;
import com.fosung.workbench.dict.StatusType;
import com.fosung.workbench.dict.TerminalType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;

/**
 * 终端应用配置信息实体对象
 */
@Entity
@Table(name="terminal_application_config")
@Setter
@Getter
public class TerminalApplicationConfigEntity extends AppJpaBaseEntity implements AppJpaSoftDelEntity  {
	/**
     * 是否删除
     */
    @Column
    private Boolean del = Boolean.FALSE ;

	/**
	 * 应用id
	 */
	@Column(name="app_id")
	private Long appId ;


	/**
	 * 应用名称
	 */
	@Column(name="app_name")
	private String appName ;


	/**
	 * 应用简介
	 */
	@Column(name="remark")
	private String remark ;


	/**
	 * 应用图标
	 */
	@Column(name="app_icon")
	private String appIcon ;

	/**
	 * 终端id
	 */
	@Column(name="terminal_id")
	private Long terminalId ;

	/**
	 * 启动类名
	 */
	@Column(name="start_name")
	private String startName;

	/**
	 * 启动参数
	 */
	@Column(name="start_param")
	private String startParam;

	/**
	 * app版本id
	 */
	@Column(name="app_version_id")
	private Long appVersionId;

	/**
	 * 状态
	 */
	@Column(name="status")
	@Enumerated(EnumType.STRING)
	private StatusType status;

	/**
	 * 版本号
	 */
	@Column(name="version_num")
	private String versionNum;

	/**
	 * 版本名称
	 */
	@Column(name = "version_name")
	private String versionName;

	/**
	 * app应用类型
	 */
	@Column(name="app_type")
	@Enumerated(EnumType.STRING)
	private TerminalType appType;

	/**
	 * 项目id
	 */
	@Column(name="project_id")
	private Long projectId;

	/**
	 * 项目名称
	 */
	@Transient
	private String projectName;

	/**
	 * 终端名称
	 */
	@Transient
	private String terminalName;

	/**
	 * 图标
	 */
	@Transient
	private String iconUrl;

	/**
	 * 分类编码
	 */
	@Transient
	private String categoryCode;

	/**
	 * 分类编码
	 */
	@Transient
	private String categoryType;

	/**
	 * 选择
	 */
	@Transient
	private Boolean checked;

	@Transient
	@Enumerated(EnumType.STRING)
	private AppType appTypes;

	/**
	 * 数据来源
	 */
	@Column(name="data_source")
	@Enumerated(EnumType.STRING)
	private DataSourceType dataSource;

	/**
	 * 版本名称
	 */
	@Column(name = "num")
	private Integer num;

	@Transient
	private Boolean disabledNum;
}