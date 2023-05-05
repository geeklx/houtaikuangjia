package com.fosung.workbench.entity.application;

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
 * 应用审核实体对象
 */
@Entity
@Table(name="application_examine")
@Setter
@Getter
public class ApplicationExamineEntity extends AppJpaBaseEntity implements AppJpaSoftDelEntity  {
	/**
     * 是否删除
     */
    @Column
    private Boolean del = Boolean.FALSE ;

	/**
	 * 所属应用
	 */
	@Column(name="app_id")
	private Long appId ;


	/**
	 * 所属版本
	 */
	@Column(name="verson_id")
	private Long versonId ;


	/**
	 * 审核状态
	 */
	@Column(name="examine_status")
	private String examineStatus ;


	/**
	 * 版本描述
	 */
	@Column(name="remark")
	private String remark ;


	/**
	 * 审核人
	 */
	@Column(name="examine_name")
	private String examineName ;


	/**
	 * 审核时间
	 */
	@Column(name="examine_time")
	private String examineTime ;


	/**
	 * 审核类型 app，版本
	 */
	@Column(name="examine_type")
	private String examineType ;


	/**
	 * 提交人
	 */
	@Column(name="submitter_name")
	private String submitterName ;


	/**
	 * 驳回原因/通过描述
	 */
	@Column(name="examine_reason")
	private String examineReason ;


}