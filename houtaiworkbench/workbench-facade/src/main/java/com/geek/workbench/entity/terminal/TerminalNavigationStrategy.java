package com.geek.workbench.entity.terminal;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 描述:  终端导航策略实体对象
 * @createDate: 2021/11/05 15:06
 * @author: gaojian
 * @version V1.0
 */
@Entity
@Table(name="terminal_navigation_strategy")
@Setter
@Getter
public class TerminalNavigationStrategy extends AppJpaBaseEntity implements AppJpaSoftDelEntity  {

	/**
     * 是否删除
     */
    @Column
    private Boolean del = Boolean.FALSE ;

	/**
	 * 策略名称
	 */
	@Column(name="strategy_name")
	private String strategyName ;


	/**
	 * 底部导航id
	 */
	@Column(name="navigation_btm_id")
	private Long navigationBtmId ;


	/**
	 * 策略地域
	 */
	@Column(name="strategy_area")
	private String strategyArea ;


	/**
	 * 策略地址
	 */
	@Column(name="strategy_url")
	private String strategyUrl ;


	/**
	 * 终端id
	 */
	@Column(name="terminal_id")
	private Long terminalId ;

}