package com.fosung.system.pbs.mq.facade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fosung.framework.common.config.AppConstants;
import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 工单消费记录实体对象
 */
@Entity
@Table(name = "cosumer_order")
@Setter
@Getter
public class RepairOrderConsumerRecord extends AppJpaBaseEntity {

	/**
	 * 
	 * 消费topic 
	 */
	@Column(name = "topic")
	private String topic;

	/**
	 * 
	 * tag 
	 */
	@Column(name = "tag")
	private String tag;

	/**
	 * 对应工单服务生产者消息记录id 
	 */
	@Column(name = "unique_id")
	private Long uniqueId;

	/**
	 * 消费者
	 */
	@Column(name = "consumer")
	private String consumer;

	@Column(name = "handler")
	private String handler;

	/**
	 * 内容（json字符串）
	 */
	@Column(name = "content", length = 2000)
	private String content;

	/**
	 * 消费结果
	 */
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private MQStatus status;

	/**
	 * 异常信息
	 */
	@Column(name = "exception_msg", length = 2000)
	private String exceptionMsg;

	/**
	 * 时间
	 */
	@Column(name = "operate_time")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = AppConstants.DATE_TIME_PATTERN)
	private Date operateTime;
}
