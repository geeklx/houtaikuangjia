package com.geek.system.support.system.entity.sys;

import com.geek.system.support.system.dict.AppStatus;
import com.geek.system.support.system.dict.AppType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;

/**
 * 应用系统信息实体对象
 */
@Entity
@Table(name="sys_application")
@Setter
@Getter
public class SysApplicationEntity extends AppJpaBaseEntity implements AppJpaSoftDelEntity  {
	/**
     * 是否删除
     */
    @Column
    private Boolean del = Boolean.FALSE ;

	/**
	 * 应用标识
	 */
	@Column(name="app_code",nullable = false,unique = true)
	private String appCode ;


	/**
	 * 应用名称
	 */
	@Column(name="app_name",nullable = false,unique = true)
	private String appName ;


	/**
	 * 图标
	 */
	@Column(name="icon_url")
	private String iconUrl ;


	/**
	 * 访问地址
	 */
	@Column(name="resource_url",length = 1000)
	private String resourceUrl ;


	/**
	 * 分类
	 */
	@Column(name="category_id")
	private String categoryId ;

	/**
	 * 排序
	 */
	@Column(name="num")
	private Integer num ;


	/**
	 * 备注
	 */
	@Column(name="remark")
	private String remark ;

	/**
	 *目录树类型
	 */
	@Column(name = "org_type")
	private String orgType;


	/**
	 * 如果是外部组织，外部链接地址
	 */
	@Column(name = "out_org_url")
	private String outOrgUrl;

	/**
	 * 应用系统类型
	 */
	@Column(name = "app_type")
	@Enumerated(EnumType.STRING)
	private AppType appType;

	/**
	 * 描述: 状态
	 * @author fuhao
	 * @date 2021/11/25 15:25
	 **/
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private AppStatus status = AppStatus.normal;

	/**
	 * 描述: 是否被选中
	 * @author fuhao
	 * @date 2021/11/25 15:25
	 **/
	@Transient
	private Boolean checkFlag;




}