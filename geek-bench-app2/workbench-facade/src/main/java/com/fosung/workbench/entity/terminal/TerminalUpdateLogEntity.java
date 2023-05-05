package com.fosung.workbench.entity.terminal;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.config.AppProperties;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;

/**
 * 终端发布记录表实体对象
 */
@Entity
@Table(name="terminal_update_log")
@Setter
@Getter
public class TerminalUpdateLogEntity extends AppJpaBaseEntity implements AppJpaSoftDelEntity  {
	/**
     * 是否删除
     */
    @Column
    private Boolean del = Boolean.FALSE ;

	/**
	 * 发布内容
	 */
	@Column(name="update_content")
	private String updateContent ;

	/**
	 * 终端版本id
	 */
	@Column(name="terminal_version_id")
	private Long terminalVersionId;

	/**
	 * 终端id
	 */
	@Column(name="terminal_id")
	private Long terminalId;
}