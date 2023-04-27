package com.geek.system.pbs.mq.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.geek.system.pbs.mq.OrgMessageHandleService;
import com.geek.system.pbs.mq.PersonnelMessageHandleService;
import com.geek.system.pbs.mq.facade.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.domain.Page;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@ConditionalOnExpression("'${app.mq.consumer.enable}'.equals('true')")
@RestController
@RequestMapping("/api/cloud/consumer")
@Slf4j
public class ConsumerController extends AppIBaseController {

	@Autowired(required = false)
	private OrgMessageHandleService orgMessageHandleService;

	@Autowired(required = false)
	private PersonnelMessageHandleService personnelMessageHandleService;

	@Autowired
	private RepairOrderConsumerRecordService repairOrderConsumerRecordService;

	/**
	 *@描述 分页查询消费记录
	 *@参数 [RepairOrderConsumerReqParam]
	 *@返回值 消费记录集合
	 *@创建人 chenxh
	 *@创建时间 2020-12-03
	 */
	@PostMapping("/query")
	public ResponseParam queryConsumeRecordPage(@RequestBody RepairOrderConsumerReqParam requestParam) {
		if (requestParam.getStatus() == null) {
			// 默认查询消费失败数据
			requestParam.setStatus(MQStatus.CONSUMER_FAIL);
		}
		Map<String, Object> searchParams = UtilDTO.toDTO(requestParam, null);
		// 分页查询消费记录
		Page<RepairOrderConsumerRecord> resultPage = repairOrderConsumerRecordService.queryByPage(searchParams, requestParam.getPageNum(),
				requestParam.getPageSize(), new String[] { "operateTime_desc" });
		return ResponseParam.success().pageParam(resultPage).datalist(resultPage.getContent());
	}

	/**
	 *@描述 根据消费记录id 重新消费失败的记录
	 *@参数 [RepairOrderConsumerReqParam]
	 *@返回值 消费成功记录id集合、消费失败记录id集合
	 *@创建人 chenxh
	 *@创建时间 2020-12-03
	 */
	@PostMapping("/reConsumeMsg")
	public ResponseParam handleConsumeRecord(@RequestBody RepairOrderConsumerReqParam requestParam) {
		Assert.notNull(requestParam.getOrderIds(), "重试记录ID不能为空");
		List<Long> successIds = new ArrayList<>();
		List<Long> failIds = new ArrayList<>();
		requestParam.getOrderIds().forEach(item -> {
			try {
				// 查询消费记录详细信息
				RepairOrderConsumerRecord workOrder = repairOrderConsumerRecordService.get(item);
				if (workOrder != null && workOrder.getStatus() == MQStatus.CONSUMER_FAIL) {
					if (workOrder.getHandler().equals("OrgConsumerHandle")) {
						log.info("消费消息处理组织业务消息: {},消息记录ID：{}", workOrder.getContent(), item);
						// 重试调用处理组织消费消息
						OrgMessage msg = JSON.parseObject(workOrder.getContent(), OrgMessage.class);
						orgMessageHandleService.handleOrgInfo(msg);
					} else if (workOrder.getHandler().equals("PersonnelConsumerHandle")) {
						log.info("重试消费消息处理人员业务消息: {},消息记录ID：{}", workOrder.getContent(), item);
						// 重试调用处理人员消费消息
						PersonnelMessage msg = JSON.parseObject(workOrder.getContent(), PersonnelMessage.class);
						personnelMessageHandleService.handlePersonnelInfo(msg);
					}
					// 设置消费成功
					workOrder.setStatus(MQStatus.SUCCESS);
					// 更新数据库状态
					repairOrderConsumerRecordService.update(workOrder, Lists.newArrayList("status"));
					// 消费成功返回id
					successIds.add(item);
				} else {
					// 消费记录不存在
					failIds.add(item);
				}
			} catch (Exception e) {
				// 消费失败
				failIds.add(item);
				log.error("重试消费消息处理业务异常: {},消息记录ID：{}", e.getMessage(), item);
			}
		});
		JSONObject resultJson = new JSONObject();
		resultJson.put("successIds", successIds);
		resultJson.put("failIds", failIds);
		return ResponseParam.success().data(resultJson);
	}
}
