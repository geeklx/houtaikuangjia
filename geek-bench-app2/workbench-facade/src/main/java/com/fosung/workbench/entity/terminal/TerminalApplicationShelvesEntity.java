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
import javax.validation.constraints.NotNull;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.config.AppProperties;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;

/**
 * 终端应用身份授权实体对象
 */
@Entity
@Table(name="terminal_application_shelves")
@Setter
@Getter
public class TerminalApplicationShelvesEntity extends AppJpaBaseEntity implements AppJpaSoftDelEntity  {
	/**
     * 是否删除
     */
    @Column
    private Boolean del = Boolean.FALSE ;

	/**
	 * 终端应用id
	 */
//	@Column(name="app_id")
//	private Long appId ;


	/**
	 * 授权范围
	 */
	@Column(name="shelves_range")
	private String shelvesRange ;

	/**
	 * 终端id
	 */
	@Column(name="terminal_id")
	@NotNull(message = "终端主键不能为空")
	private Long terminalId ;

	/**
	 * 授权类型
	 */
	@Column(name="shelves_type")
	private String shelvesType ;

	/**
	 * 数据来源
	 */
	@Column(name="data_source")
	private String dataSource ;

	/**
	 * 终端应用配置id
	 */
	@Column(name="app_config_id")
	private Long appConfigId;

	/***
	 * 描述: 父节点
	 * @author fuhao
	 * @date 2021/11/10 18:46
	 **/
	@Column(name = "shelves_range_parent")
	private String shelvesRangeParents;

}