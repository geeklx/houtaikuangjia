package com.fosung.workbench.entity.config;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 描述:  接口配置实体对象
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Entity
@Table(name="config_api")
@Setter
@Getter
public class ConfigApiEntity extends AppJpaBaseEntity implements AppJpaSoftDelEntity {

	/**
	 * 接口所属组的主键
	 */
	@NotNull(message = "接口所属组主键不能为空")
	@Column(name="api_group_id" , nullable = false , updatable = false)
	private Long apiGroupId;

	/**
	 * 接口编码
	 */
	@NotBlank(message = "接口编码不能为空")
	@Size(min = 0, max = 255, message = "接口编码长度不能超过255个字符")
	@Column(name="api_code" , nullable = false , updatable = false)
	private String apiCode ;

	/**
	 * 接口名称
	 */
	@Column(name="api_name")
	@NotBlank(message = "接口名称不能为空")
	@Size(min = 0, max = 32, message = "接口名称长度不能超过32个字符")
	private String apiName ;

	/**
	 * 接口host
	 */
	@NotBlank(message = "接口HOST不能为空")
	@Size(min = 0, max = 255, message = "接口HOST长度不能超过255个字符")
	@Column(name="api_host")
	private String apiHost ;

	/**
	 * 接口地址
	 */
	@NotBlank(message = "接口地址不能为空")
	@Size(min = 0, max = 255, message = "接口地址长度不能超过255个字符")
	@Column(name="api_address")
	private String apiAddress ;

	/**
	 * 接口备注
	 */
	@Size(min = 0, max = 255, message = "接口描述长度不能超过255个字符")
	@Column(name="remark")
	private String remark ;

	/**
	 * 接口扩展属性
	 */
	@Size(min = 0, max = 2000, message = "接口扩展属性长度不能超过2000个字符")
	@Column(name="extend_property_json")
	private String extendPropertyJson ;

	/**
	 * 是否逻辑删除
	 */
	@Column(name="del")
	private Boolean del = Boolean.FALSE;


}