package com.fosung.workbench.entity.terminal;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;
import com.fosung.workbench.dict.RangeType;
import com.fosung.workbench.dict.StatusType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 终端版本号控制实体对象
 */
@Entity
@Table(name="terminal_version")
@Setter
@Getter
public class TerminalVersionEntity extends AppJpaBaseEntity implements AppJpaSoftDelEntity  {

	/**
     * 是否删除
     */
    @Column
    private Boolean del = Boolean.FALSE ;

	/**
	 * 终端版本号
	 */
	@Column(name="terminal_version")
	@Size(min = 0, max = 32, message = "终端版本号长度不能超过32个字符")
	private String terminalVersion ;


	/**
	 * 终端id
	 */
	@Column(name="terminal_id")
	@NotNull(message = "终端主键不能为空")
	private Long terminalId ;

	/**
	 * 终端名称
	 */
	@Transient
	private String terminalName;


	/**
	 * 终端版本号（纯数字）
	 */
	@Column(name="terminal_version_num")
	private Integer terminalVersionNum ;


	/**
	 * 版本说明
	 */
	@Column(name="remark")
	@Size(min = 0, max = 512, message = "更细说明长度不能超过512个字符")
	private String remark ;

	/**
	 * app大小
	 */
	@Column(name = "app_size")
	private String appSize;

	/**
	 * 发布状态
	 */
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private StatusType status;

	/**
	 * 项目id
	 */
	@Column(name = "project_id")
	private Long projectId;

	/**
	 * 项目名称
	 */
	@Transient
	private String projectName;

	/**
	 * 终端logo
	 */
	@Column(name = "terminal_logo_url")
	private String terminalLogoUrl;

	/**
	 * 应用平台
	 */
	@Column(name = "terminal_type")
	private String terminalType;

	/**
	 * 安装包
	 */
	@Column(name = "installation_package")
	private String installationPackage;

	/**
	 * 安装包md5
	 */
	@Column(name = "installation_package_md5")
	private String installationPackageMd5;

	/**
	 * 更新提示
	 */
	@Column(name = "upgrade_prompt")
	private Boolean upgradePrompt;

	/**
	 * 强制升级
	 */
	@Column(name = "force_upgrade")
	private Boolean forceUpgrade;

	/**
	 * 版本名称
	 */
	@Column(name = "version_name")
	@Size(min = 0, max = 32, message = "版本名称长度不能超过32个字符")
	private String versionName;

	/**
	 * 安装数
	 */
	@Column(name = "installations_number")
	private Integer installationsNumber;

	/**
	 * 下载数
	 */
	@Column(name = "download_number")
	private Integer downloadNumber;

	/**
	 * 服务电话
	 */
//	@Column(name = "service_phone")
//	private String servicePhone;

	/**
	 * 更新标题
	 */
	@Column(name = "upgrade_title")
	private String upgradeTitle;
	/**
	 * 更新背景图片
	 */
	@Column(name = "upgrade_back_img")
	private String upgradeBackImg;
	/**
	 * 更新范围的值
	 */
	@Transient
	private List<TerminalVersionUpgradeRangeEntity> upgradeRangeValues;

	/**
	 * 更新范围
	 */
	@Transient
	@Enumerated(EnumType.STRING)
	private RangeType upgradeRangeType ;

	/***
	 * 描述:文件名称
	 * @author fuhao
	 * @date 2021/11/8 10:21
	 **/
	@Column(name = "file_name")
	private String fileName;


}