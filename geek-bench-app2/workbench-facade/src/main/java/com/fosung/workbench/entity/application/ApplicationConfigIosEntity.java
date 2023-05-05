package com.fosung.workbench.entity.application;

import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;
import com.fosung.workbench.dict.TerminalType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 描述:  ios高级配置实体对象
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Entity
@Table(name="application_config_ios")
@Setter
@Getter
public class ApplicationConfigIosEntity extends ApplicationBaseConfigEntity implements AppJpaSoftDelEntity  {

	/**
	 * AppStore ID
	 */
	@Column(name="app_store_id")
	private String appStoreId ;

	/**
	 * 调起应用
	 */
	@Column(name="start_app")
	private Boolean startApp ;


	/**
	 * Bundle ID
	 */
	@Column(name="bundle_id")
	private String bundleId ;


	/**
	 * IOS URL Schemes
	 */
	@Column(name="ios_url_scemes")
	private String iosUrlScemes ;


	/**
	 * 应用版本id
	 */
	@Column(name="app_version_id")
	private Long appVersionId ;


	/**
	 * 应用类型
	 */
	@Transient
	private String AppType = TerminalType.ios.name();
}