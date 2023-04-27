package com.geek.workbench.entity.microcoder;

import com.geek.workbench.dict.AgreementType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;

/**
 * 协议配置信息实体对象
 */
@Entity
@Table(name="terminal_config_agreement")
@Setter
@Getter
public class TerminalConfigAgreementEntity extends AppJpaBaseEntity implements AppJpaSoftDelEntity  {
	/**
     * 是否删除
     */
    @Column
    private Boolean del = Boolean.FALSE ;

	/**
	 * 协议名称
	 */
	@Column(name="agreement_name")
	@NotBlank(message = "协议名称不能为空")
	@Size(min = 0, max = 32, message = "协议名称长度不能超过32个字符")
	private String agreementName ;


	/**
	 * 协议类型
	 */
	@Column(name="agreement_type")
	@Enumerated(EnumType.STRING)
	private AgreementType agreementType ;


	/**
	 * 协议内容
	 */
	@Column(name="agreement_content")
	private String agreementContent ;

	/**
	 * 协议url
	 */
	@Column(name="agreement_url")
	private String agreementUrl;

	/**
	 * 协议id
	 */
	@Column(name="terminal_id")
	@NotNull(message = "终端主键不能为空")
	private Long terminalId ;


}