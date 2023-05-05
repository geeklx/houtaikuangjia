package com.fosung.workbench.entity.application;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;
import com.fosung.workbench.dict.AppType;
import com.fosung.workbench.dict.DataSourceType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 描述:  应用基本信息表实体对象
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Entity
@Table(name="application_basic")
@Setter
@Getter
public class ApplicationBasicEntity extends AppJpaBaseEntity implements AppJpaSoftDelEntity  {

	/**
	 * 是否删除
	 */
	@Column
	private Boolean del = Boolean.FALSE ;

	/**
	 * 应用名称
	 */
	@NotBlank(message = "应用名称不能为空")
	@Size(min = 0, max = 255, message = "应用名称长度不能超过255个字符")
	@Column(name="app_name")
	private String appName ;


	/**
	 * 应用编码
	 */
	@NotBlank(message = "应用编码不能为空")
	@Size(min = 0, max = 16, message = "应用编码长度不能超过16个字符")
	@Column(name="app_code")
	private String appCode ;


	/**
	 * 图标
	 */
	@NotBlank(message = "应用图标不能为空")
	@Size(min = 0, max = 255, message = "应用图标地址长度不能超过255个字符")
	@Column(name="icon_url")
	private String iconUrl ;


	/**
	 * 排序
	 */
	@Column(name="num")
	private Integer num ;


	/**
	 * 备注简介
	 */
	@Column(name="remark")
	private String remark ;


	/**
	 * 应用类型（app原生，h5）
	 */
	@NotNull(message = "应用类型不能为空")
	@Column(name="app_type")
	@Enumerated(EnumType.STRING)
	private AppType appType ;


	/**
	 * 应用提供方
	 */
	@NotBlank(message = "应用提供方不能为空")
	@Size(min = 0, max = 255, message = "应用提供方长度不能超过255个字符")
	@Column(name="app_support")
	private String appSupport ;


	/**
	 * 所属项目id
	 */
	@Column(name="project_id")
	private Long projectId ;


	/**
	 * 应用所属分类
	 */
	@NotBlank(message = "应用所属分类编码不能为空")
	@Size(min = 0, max = 255, message = "应用所属分类编码长度不能超过255个字符")
	@Column(name="category_code")
	private String categoryCode ;

	/**
	 * 所属分类名称
	 */
	@Column(name="category_name")
	private String categoryName;

	/**
	 * 审核状态
	 */
	@Column(name="examine_flag")
	private String examineFlag ;

	/**
	 * 数据来源
	 */
	@Enumerated(EnumType.STRING)
	@Column(name="data_source")
	private DataSourceType dataSource ;


	/**
	 * 外部应用唯一id
	 */
	@Column(name="external_id")
	private Long externalId ;


	/**
	 * 是否维护
	 */
	@Column(name="maintain")
	private Boolean maintain ;

	/**
	 * 内部应用 / 外部应用
	 */
	@Column(name="in_or_out")
	private Boolean inOrOut;


	/**
	 * 维护内容
	 */
	@Column(name="maintain_message")
	private String maintainMessage ;

	/**
	 * 维护内容URL
	 */
	@Column(name="maintain_url")
	private String maintainUrl;

	/**
	 * 维护标题 2021-12-15 根据需求增加
	 */
	@Column(name="maintain_title")
	private String maintainTitle ;

	/**
	 * 维护背景图 2021-12-15 根据需求增加
	 */
	@Column(name="maintain_background_url")
	private String maintainBackgroundUrl;


	/**
	 * 创建人名称
	 */
	@Column(name="creater_name")
	private String createrName ;

	/**
	 * 包名
	 */
	@Column(name="package_name")
	@NotBlank(message = "应用包名不能为空")
	@Size(min = 0, max = 255, message = "应用包名长度不能超过255个字符")
	private String packageName;

	/**
	 * 应用配置
	 */
	@Transient
	private List<ApplicationBaseConfigEntity> appConfigs;

	@Transient
	private boolean checked;


}