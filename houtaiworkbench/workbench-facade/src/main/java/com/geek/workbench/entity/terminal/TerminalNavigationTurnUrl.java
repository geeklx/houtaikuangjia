package com.geek.workbench.entity.terminal;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;
import com.geek.workbench.dict.AssociationType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 描述:  终端导航跳转地址表实体对象
 * @createDate: 2021/11/05 15:06
 * @author: gaojian
 * @version V1.0
 */
@Entity
@Table(name="terminal_navigation_turn_url")
@Setter
@Getter
public class TerminalNavigationTurnUrl extends AppJpaBaseEntity implements AppJpaSoftDelEntity  {

	/**
     * 是否删除
     */
    @Column
    private Boolean del = Boolean.FALSE ;

	/**
	 * 终端主键
	 */
	@Column(name="terminal_id")
	@NotNull(message = "终端主键信息不能为空")
	private Long terminalId ;


	/**
	 * 导航跳转地址
	 */
	@Column(name="navigation_turn_url")
	@NotBlank(message = "导航跳转地址不能为空")
	@Size(min = 0, max = 255, message = "导航跳转地址长度不能超过255个字符")
	private String navigationTurnUrl ;


	/**
	 * 关联类型
	 */
	@Column(name="association_type")
	@Enumerated(EnumType.STRING)
	private AssociationType associationType ;


	/**
	 * 跳转名称
	 */
	@Column(name="turn_name")
	@NotBlank(message = "跳转名称不能为空")
	@Size(min = 0, max = 255, message = "跳转名称长度不能超过255个字符")
	private String turnName ;

	/**
	 * 排序
	 */
	@Column(name="num")
	private Integer num ;
}