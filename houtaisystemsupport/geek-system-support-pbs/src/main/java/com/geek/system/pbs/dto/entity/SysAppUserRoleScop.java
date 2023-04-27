package com.geek.system.pbs.dto.entity;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 用户角色关联表实体对象
 */
@Data
public class SysAppUserRoleScop   {





	/**
	 * 组织id
	 */
	private Long orgId ;

	private String orgName;

	private String orgCode;


}