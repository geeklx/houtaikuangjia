package com.geek.workbench.entity.config;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.geek.workbench.dict.BindCategoryType;
import com.geek.workbench.dict.BindType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * 描述:  接口所属组配置表实体对象
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Entity
@Table(name="config_api_group")
@Setter
@Getter
public class ConfigApiGroupEntity extends AppJpaBaseEntity {

	/**
	 * 接口组名称
	 */
	@Column(name="api_group_name")
	private String apiGroupName ;

	/**
	 * 接口分类
	 */
	@Column(name="api_category" , nullable = false , updatable = false)
	@Enumerated(EnumType.STRING)
	private BindCategoryType apiCategory;

	/**
	 * 接口类型
	 */
	@Column(name="api_type" , nullable = false , updatable = false)
	@Enumerated(EnumType.STRING)
	private BindType apiType ;

	/**
	 * 接口备注
	 */
	@Size(min = 0, max = 255, message = "接口组信息描述长度不能超过255个字符")
	@Column(name="remark")
	private String remark ;

	/**
	 * 接口扩展属性
	 */
	@Size(min = 0, max = 2000, message = "接口组扩展属性长度不能超过2000个字符")
	@Column(name="extend_property_json")
	private String extendPropertyJson ;


}