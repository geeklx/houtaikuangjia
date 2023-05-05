package com.fosung.workbench.entity.application;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 我的应用实体对象
 */
@Entity
@Table(name="application_owner")
@Setter
@Getter
public class ApplicationOwnerEntity extends AppJpaBaseEntity   {

	public ApplicationOwnerEntity(String userId,Long appConfigId,Long terminalId){
		this.userId = userId;
		this.appConfigId = appConfigId;
		this.terminalId = terminalId;
	}
	public ApplicationOwnerEntity(){

	}



	/**
	 * 用户id
	 */
	@Column(name="user_id")
	private String userId ;


	/**
	 * app配置主键
	 */
	@Column(name="app_config_id")
	private Long appConfigId ;


	/**
	 * 终端id
	 */
	@Column(name="terminal_id")
	private Long terminalId ;


}