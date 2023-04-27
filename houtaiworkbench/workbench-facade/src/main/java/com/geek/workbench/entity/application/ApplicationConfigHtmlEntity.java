package com.geek.workbench.entity.application;

import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;
import com.geek.workbench.dict.PublishType;
import com.geek.workbench.dict.TerminalType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 描述:  h5页面高级配置实体对象
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Entity
@Table(name="application_config_html")
@Setter
@Getter
public class ApplicationConfigHtmlEntity extends ApplicationBaseConfigEntity implements AppJpaSoftDelEntity  {

	/**
	 * 发布方式
	 */
	@Column(name="publish_type")
	@Enumerated(EnumType.STRING)
	private PublishType publishType ;


	/**
	 * 前端url
	 */
	@Column(name="front_url")
	private String frontUrl ;


	/**
	 * 安装包地址
	 */
	@Column(name="app_package_path")
	private String appPackagePath ;

	/**
	 * 下载地址
	 */
	@Column(name="download_path")
	private String downloadPath ;


	/**
	 * 应用版本主键
	 */
	@Column(name="app_version_id")
	private Long appVersionId ;
	/**
	 * 是否是小程序
	 */
	@Column(name="is_applet")
	private String isApplet;
	/**
	 *  小程序名称
	 */
	@Column(name="user_name")
	private String userName;
	/**
	 *  小程序appid
	 */
	@Column(name="applet_id")
	private String appletId;
	/**
	 *  小程序链接地址
	 */
	@Column(name="applet_path")
	private String appletPath;
	/**
	 * 应用类型
	 */
	@Transient
	private String AppType = TerminalType.h5.name();
}