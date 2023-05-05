package com.fosung.workbench.entity.project;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 描述:  项目基本信息实体对象
 * @createDate: 2021/10/13 9:01
 * @author: gaojian
 * @modify:
 * @return:
 */
@Entity
@Table(name="project_basic")
@Setter
@Getter
public class ProjectBasicEntity extends AppJpaBaseEntity implements AppJpaSoftDelEntity  {

	/**
     * 是否删除
     */
    @Column
    private Boolean del = Boolean.FALSE ;

	/**
	 * 项目编码
	 */
	@NotBlank(message = "项目编码不能为空")
	@Size(min = 0, max = 32, message = "项目编码长度不能超过32个字符")
	@Column(name="project_code" , nullable = false)
	private String projectCode ="";

	/**
	 * 项目名称
	 */
	@NotBlank(message = "项目名称不能为空")
	@Size(min = 0, max = 32, message = "项目名称长度不能超过32个字符")
	@Column(name="project_name" , nullable = false)
	private String projectName ="";


	/**
	 * 项目描述
	 */
	@Size(min = 0, max = 512, message = "项目描述长度不能超过512个字符")
	@Column(name="remark")
	private String remark ;


	/**
	 * 项目状态
	 */
	@Column(name="status")
	private String status ;


	/**
	 * 项目负责人
	 */
	@NotBlank(message = "负责人不能为空")
	@Size(min = 0, max = 32, message = "负责人长度不能超过32个字符")
	@Column(name="person" , nullable = false)
	private String person ;

}