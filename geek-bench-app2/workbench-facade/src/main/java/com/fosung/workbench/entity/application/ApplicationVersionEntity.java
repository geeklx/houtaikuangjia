package com.fosung.workbench.entity.application;

import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;
import com.fosung.workbench.dict.AuditStatusType;
import com.fosung.workbench.dict.TerminalType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 应用版本信息实体对象
 */
@Entity
@Table(name="application_version")
@Setter
@Getter
public class ApplicationVersionEntity extends AppJpaBaseEntity implements AppJpaSoftDelEntity  {

	/**
     * 是否删除
     */
    @Column
    private Boolean del = Boolean.FALSE ;

	/**
	 * 版本名称
	 */
	@Column(name="version_name")
	private String versionName ;

	/**
	 * 是否兼容老版本
	 */
	@Column(name="compatible_old_version")
	private Boolean compatibleOldVersion ;

	/**
	 * 版本说明
	 */
	@Column(name="remark")
	private String remark ;

	/**
	 * 应用id
	 */
	@Column(name="app_id")
	private Long appId ;

	/**
	 * 应用名称
	 */
	@Transient
	private String appName;

	/**
	 * 审核状态
	 */
	@Enumerated(EnumType.STRING)
	@Column(name="audit_status")
	private AuditStatusType auditStatus ;

	/**
	 * 审核说明
	 */
	@Column(name="audit_remark")
	private String auditRemark ;

	/**
	 * 是否最新版本
	 */
	@Column(name="is_new_version")
	private Boolean isNewVersion ;

	/**
	 * 版本大小
	 */
	@Column(name="version_size")
	private String versionSize ;

	/**
	 * 版本号纯数字类型
	 */
	@Column(name="version_num")
	private Integer versionNum ;

	/**
	 * 下载次数
	 */
	@Column(name = "download_num")
	private Integer downloadNum;

	/**
	 * 创建人
	 */
	@Column(name = "create_user_name")
	private String createUserName;

	/**
	 * 应用类型
	 */
	@Column(name="app_type")
	@Enumerated(EnumType.STRING)
	private TerminalType appType;

	/**
	 * 描述:  版本配置主键
	 */
	@Column(name="version_config_id")
	private Long versionConfigId;

	/**
	 * 描述:  包名
	 */
	@Column(name="package_name")
	private String packageName;

	/**
	 * 描述:  启动类名
	 */
	@Column(name="config_path")
	private String configPath;

	/**
	 * 描述:  启动类名
	 */
	@Column(name="start_name")
	private String startName;

	/**
	 * 描述:  启动参数
	 */
	@Column(name="start_params")
	private String startParams;

	/**
	 * 描述:  文件名称
	 */
	@Column(name="file_name")
	private String fileName;

	/**
	 * 版本属性
	 */
	@Transient
	private JSONObject jsonPropertyObj;

	/**
	 * 应用信息
	 */
	@Transient
	private ApplicationBasicEntity appInfo;

	/**
	 * 应用配置
	 */
	@Transient
	private List<ApplicationBaseConfigEntity> appConfigs;

}