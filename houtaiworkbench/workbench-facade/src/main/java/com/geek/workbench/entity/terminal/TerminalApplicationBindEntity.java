package com.geek.workbench.entity.terminal;

import com.geek.workbench.dict.StatusType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;

/**
 * 终端应用选择配置实体对象
 */
@Entity
@Table(name="terminal_application_bind")
@Setter
@Getter
public class TerminalApplicationBindEntity extends AppJpaBaseEntity implements AppJpaSoftDelEntity  {
	/**
     * 是否删除
     */
    @Column
    private Boolean del = Boolean.FALSE ;

	/**
	 * 项目id
	 */
	@Column(name="project_id")
	private Long projectId ;


	/**
	 * 终端id
	 */
	@Column(name="terminal_id")
	@NotNull(message = "终端主键不能为空")
	private Long terminalId ;


	/**
	 * 专区id
	 */
	@Column(name="zone_id")
	private Long zoneId ;


	/**
	 * 应用id
	 */
	@Column(name="app_id")
	private Long appId ;

	/**
	 * 发布状态
	 */
	@Column(name="status")
	@Enumerated(EnumType.STRING)
	private StatusType status;

	/**
	 * 应用版本id
	 */
	@Column(name="app_version_id")
	private Long appVersionId ;

	/**
	 * 终端应用配置id
	 */
	@Column(name="app_config_id")
	private Long appConfigId;


	/**
	 * 版本号
	 */
	@Transient
	private String versionNum;

	/**
	 * 版本大小
	 */
	@Transient
	private String versionSize;

	/**
	 * 版本说明
	 */
	@Transient
	private String remark;

	/**
	 * app名称
	 */
	@Transient
	private String versionName;

	/**
	 * 状态描述
	 */
	@Transient
	private String statusRemark;
}