package com.geek.workbench.entity.terminal;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;
import com.geek.workbench.dict.RangeType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 终端版本与发布记录关系表实体对象
 */
@Entity
@Table(name="terminal_version_upgrade_range")
@Setter
@Getter
public class TerminalVersionUpgradeRangeEntity extends AppJpaBaseEntity implements AppJpaSoftDelEntity  {
	/**
     * 是否删除
     */
    @Column
    private Boolean del = Boolean.FALSE ;

	/**
	 * 终端版本id
	 */
	@Column(name="terminal_version_id")
	private Long terminalVersionId ;

	/**
	 * 更新范围类型
	 */
	@Column(name="upgrade_range_type")
	@Enumerated(EnumType.STRING)
	private RangeType upgradeRangeType ;

	/**
	 * 更新范围值
	 */
	@Column(name="upgrade_range_value")
	private String upgradeRangeValue ;

	/**
	 * 更新范围名称
	 */
	@Column(name="upgrade_range_name")
	private String upgradeRangeName ;

	/**
	 * 更新 id
	 */
	@Column(name="update_index")
	private Integer updateIndex ;

}