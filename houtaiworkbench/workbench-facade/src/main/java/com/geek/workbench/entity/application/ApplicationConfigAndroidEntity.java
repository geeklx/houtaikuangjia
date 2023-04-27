package com.geek.workbench.entity.application;

import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;
import com.geek.workbench.dict.TerminalType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 描述:  安卓高级页面配置实体对象
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Entity
@Table(name="application_config_android")
@Setter
@Getter
public class ApplicationConfigAndroidEntity extends ApplicationBaseConfigEntity implements AppJpaSoftDelEntity  {

	/**
	 * 应用包存储路径
	 */
	@Column(name="app_package_path")
	private String appPackagePath ;

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
	 * 下载地址
	 */
	@Column(name="download_path")
	private String downloadPath ;


	/**
	 * AppStore ID 
	 */
	@Column(name="app_store_id")
	private String appStoreId ;


	/**
	 * 应用版本id
	 */
	@Column(name="app_version_id")
	private Long appVersionId ;

	/**
	 * 应用类型
	 */
	@Transient
	private String appType = TerminalType.android.name();
}