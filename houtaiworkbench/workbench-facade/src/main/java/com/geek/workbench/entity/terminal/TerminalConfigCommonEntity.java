package com.geek.workbench.entity.terminal;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.config.AppProperties;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;

/**
 * 终端通用配置实体对象
 */
@Entity
@Table(name="terminal_config_common")
@Setter
@Getter
public class TerminalConfigCommonEntity extends AppJpaBaseEntity implements AppJpaSoftDelEntity  {
	/**
     * 是否删除
     */
    @Column
    private Boolean del = Boolean.FALSE ;

	/**
	 * 配置编码
	 */
	@Column(name="config_code")
	private String configCode ;


	/**
	 * 配置类型
	 */
	@Column(name="config_type")
	private String configType ;


	/**
	 * 值
	 */
	@Column(name="config_value")
	private String configValue ;


	/**
	 * 终端id
	 */
	@Column(name="terminal_id")
	@NotNull(message = "终端主键不能为空")
	private Long terminalId ;


}