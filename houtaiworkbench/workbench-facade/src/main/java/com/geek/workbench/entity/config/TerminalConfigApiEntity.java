package com.geek.workbench.entity.config;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.geek.workbench.dict.BindCategoryType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 描述:  终端api配置表实体对象
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Entity
@Table(name="terminal_config_api")
@Setter
@Getter
public class TerminalConfigApiEntity extends AppJpaBaseEntity {

	/**
	 * 终端id
	 */
	@Column(name="terminal_id")
	private Long terminalId ;


	/**
	 * 绑定分类（目录树、人员接口）
	 */
	@Column(name="bind_category")
	@Enumerated(EnumType.STRING)
	private BindCategoryType bindCategory ;


	/**
	 * 绑定编码
	 */
	@Column(name="bind_type")
	private String bindType ;

	/**
	 * 绑定主键
	 */
	@Column(name="bind_group_id")
	private Long bindGroupId ;


}