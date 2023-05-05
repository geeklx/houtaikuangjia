package com.fosung.system.pbs.mq.facade;

import com.fosung.framework.common.config.AppConstants;
import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 工单消费记录查询
 */
@Data
public class RepairOrderConsumerReqParam {

	/**
	 * 
	 * 消费topic 
	 */
	private String topic;

	/**
	 * 
	 * tag 
	 */
	private String tag;

	/**
	 * 消费者
	 */
	private String consumer;

	private String handler;

	/**
	 * 消费结果
	 */
	private MQStatus status;

	/**
	 * 处理对象id
	 */
	private List<Long> orderIds;

	/**
	 * 开始时间
	 */
	@DateTimeFormat(pattern= AppConstants.DATE_TIME_PATTERN)
	private Date startTime;

	/**
	 * 结束时间
	 */
	@DateTimeFormat(pattern= AppConstants.DATE_TIME_PATTERN)
	private Date endTime;

	private Integer pageNum = 0;

	private Integer pageSize = 15;


}
