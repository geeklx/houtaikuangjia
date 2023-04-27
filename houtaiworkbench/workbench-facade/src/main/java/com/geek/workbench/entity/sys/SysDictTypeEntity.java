package com.geek.workbench.entity.sys;

import com.geek.workbench.dict.DictStatus;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;

/**
 * 字典类型实体对象
 */
@Entity
@Table(name="sys_dict_type")
@Setter
@Getter
public class SysDictTypeEntity extends AppJpaBaseEntity   {

	/**
	 * 字典名
	 */
	@Column(name="dict_name",nullable = false,unique = true)
	private String dictName ;


	/**
	 * 字典类型
	 */
	@Column(name="dict_type",nullable = false,unique = true)
	private String dictType ;


	/**
	 * 备注
	 */
	@Column(name="remark")
	private String remark ;


	/**
	 * 系统id
	 */
	@Column(name="project_id",nullable = false)
	private Long projectId ;

	@Transient
	private String projectName;

	/**
	 * 描述: 状态
	 * @author fuhao
	 * @date 2022/1/18 15:48
	 **/
	@Column(name="status")
	@Enumerated(EnumType.STRING)
	private DictStatus status = DictStatus.normal;



}