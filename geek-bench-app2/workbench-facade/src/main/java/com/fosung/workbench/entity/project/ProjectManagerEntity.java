package com.fosung.workbench.entity.project;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 描述:  项目管理员实体对象
 * @createDate: 2021/10/13 9:06
 * @author: gaojian
 * @modify:
 * @return:
 */
@Entity
@Table(name="project_manager")
@Setter
@Getter
public class ProjectManagerEntity extends AppJpaBaseEntity implements AppJpaSoftDelEntity  {

	/**
     * 是否删除
     */
    @Column
    private Boolean del = Boolean.FALSE ;

	/**
	 * 用户主键
	 */
	@NotBlank(message = "用户ID不能为空")
	@Size(min = 0, max = 128, message = "用户ID长度不能超过128个字符")
	@Column(name="user_id" , length = 128 , nullable = false)
	private String userId ;


	/**
	 * 用户电话
	 */
	@Column(name="user_phone")
	private String userPhone ;


	/**
	 * 用户名
	 */
//	@NotBlank(message = "用户名不能为空")
//	@Size(min = 0, max = 100, message = "用户名长度不能超过100个字符")
	@Column(name="user_name" , length = 100 , nullable = false)
	private String userName ;


	/**
	 * 项目主键
	 */
	@Column(name="project_id")
	@NotNull(message = "项目主键不能为空")
	private Long projectId ;

	@NotBlank(message = "用户ID不能为空")
	@Size(min = 0, max = 128, message = "用户ID长度不能超过128个字符")
	@Transient
	private String configId;

	public void setConfigId(String configId) {
		this.userId = configId;
		this.configId = configId;
	}
}