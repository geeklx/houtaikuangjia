package com.fosung.workbench.entity.common;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 描述:  操作日志表实体对象
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Entity
@Table(name="manage_opt_log")
@Setter
@Getter
public class ManageOptLogEntity extends AppJpaBaseEntity implements AppJpaSoftDelEntity  {

	/**
     * 是否删除
     */
    @Column
    private Boolean del = Boolean.FALSE ;

	/**
	 * 业务主键
	 */
	@Column(name="opt_business_id")
	private String optBusinessId ;


	/**
	 * 操作模块
	 */
	@Column(name="opt_module")
	private String optModule ;


	/**
	 * 操作名称
	 */
	@Column(name="opt_name")
	private String optName ;


	/**
	 * 操作类型
	 */
	@Column(name="opt_type")
	private String optType ;


	/**
	 * 请求ip
	 */
	@Column(name="req_ip")
	private String reqIp ;


	/**
	 * 请求方法
	 */
	@Column(name="req_method")
	private String reqMethod ;


	/**
	 * 请求uri
	 */
	@Column(name="req_uri")
	private String reqUri ;


	/**
	 * 请求参数
	 */
	@Column(name="req_param")
	private String reqParam ;


	/**
	 * 返回参数
	 */
	@Column(name="res_param")
	private String resParam ;

}