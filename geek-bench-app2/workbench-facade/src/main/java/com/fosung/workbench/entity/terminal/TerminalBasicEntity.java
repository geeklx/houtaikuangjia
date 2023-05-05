package com.fosung.workbench.entity.terminal;


import com.fosung.workbench.dict.TerminalQueryType;
import com.fosung.workbench.dict.TerminalType;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;

import java.util.List;

/**
 * 终端基本信息实体对象
 */
@Entity
@Table(name="terminal_basic")
@Setter
@Getter
public class TerminalBasicEntity extends AppJpaBaseEntity implements AppJpaSoftDelEntity  {
	/**
     * 是否删除
     */
    @Column
    private Boolean del = Boolean.FALSE ;

	/**
	 * 项目id
	 */
	@Column(name="project_id")
	@NotNull(message = "项目主键不能为空")
	private Long projectId ;

	/**
	 * 项目名称
	 */
	@Transient
	private String projectName;


	/**
	 * 终端名称
	 */
	@Column(name="terminal_name")
	@Size(min = 0, max = 32, message = "终端名称长度不能超过32个字符")
	private String terminalName ;


	/**
	 * 终端类型
	 */
	@Column(name="terminal_type")
	@Enumerated(EnumType.STRING)
	private TerminalType terminalType;


	/**
	 * 终端编码
	 */
	@Column(name="terminal_code")
	@NotBlank(message = "终端编码不能为空")
	@Size(min = 0, max = 32, message = "终端编码长度不能超过32个字符")
	private String terminalCode ;


	/**
	 * 备注说明
	 */
	@Column(name="remark")
	@Size(min = 0, max = 512, message = "终端简介长度不能超过512个字符")
	private String remark ;
	/**
	 * 备注说明地址
	 */
	@Column(name="remark_url")
	@Size(min = 0, max = 512, message = "终端简介长度不能超过512个字符")
	private String remarkUrl;

	/**
	 * 专区id
	 */
	@Column(name="zone_id")
	private String zoneId ;

	/**
	 * 终端图标
	 */
	@Column(name = "terminal_logo")
	private String terminalLogo;

	/**
	 * 终端认证id
	 */
	@Column(name = "authentication_id")
	private Long authenticationId;

	/**
	 * 包名
	 */
	@Column(name = "package_name")
	private String packageName;
	/**
	 * 首页展示条数
	 */
	@Column(name = "show_num")
	private String showNum;
	/**
	 * 描述:  终端查询应用类型
	 */
	@Column(name = "query_app_type")
	@Enumerated(EnumType.STRING)
	private TerminalQueryType queryAppType = TerminalQueryType.all;

	public TerminalQueryType getQueryAppType() {
		return queryAppType;
	}

	public void setQueryAppType(TerminalQueryType queryAppType) {
		if(queryAppType == null){
			this.queryAppType = TerminalQueryType.all;
		}else{
			this.queryAppType = queryAppType;
		}

	}

	/*
	 *终端id
	 */
	@Transient
	private String terminalId;

	@Transient
	private List<TerminalBasicEntity> projectTrees;

	@Transient
	private List<TerminalBasicEntity> terminalTrees;

}