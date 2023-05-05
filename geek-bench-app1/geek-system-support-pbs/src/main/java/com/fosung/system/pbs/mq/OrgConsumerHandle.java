package com.fosung.system.pbs.mq;

import java.util.Date;
import java.util.Map;

import com.fosung.framework.mq.common.StringAbstractMessageConsumerHandler;
import com.fosung.system.pbs.common.DingDingMsg;
import com.fosung.system.pbs.mq.facade.MQStatus;
import com.fosung.system.pbs.mq.facade.OrgMessage;
import com.fosung.system.pbs.mq.facade.RepairOrderConsumerRecord;
import com.fosung.system.pbs.mq.facade.RepairOrderConsumerRecordService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ConditionalOnExpression("'${app.mq.consumer.enable}'.equals('true')")
@ConditionalOnProperty(prefix = "app.mq.consumer", value = "enableOrg", havingValue = "true", matchIfMissing = true)
@Service("OrgConsumerHandle")
public class OrgConsumerHandle extends StringAbstractMessageConsumerHandler {

	@Autowired
	private DingDingMsg dingDingMsg;

	@Autowired
	private OrgMessageHandleService orgMessageHandleService;

	@Autowired
	private RepairOrderConsumerRecordService repairOrderConsumerRecordService;

	@Value("${spring.application.name:OrgConsumer}")
	private String applicationName;

	/**
	 * 消费消息处理业务
	 * @param tag 订阅标签值
	 * @param message 消息
	 * @return 消费结果
	 * @since 1.0
	 */
	@Override
	public boolean handler(String topic, String tag, long bornTimestamp, String message) {
		// 默认消费成功
		MQStatus status = MQStatus.SUCCESS;
		log.info("消费消息处理组织业务消息: {}", message);
		String errMsg = "";
		String jsonMsg = "";
		Long uniqueId = null;
		try {
			// 调用处理消息
			JSONObject json = JSON.parseObject(message);
			OrgMessage msg = JSON.toJavaObject(json.getJSONObject("message"), OrgMessage.class);
			uniqueId = json.getLong("uniqueId");
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("uniqueId", uniqueId);
			searchParams.put("consumer", this.applicationName);
			searchParams.put("status", MQStatus.SUCCESS);
			jsonMsg = JSON.toJSONString(msg);
			if (CollectionUtils.isNotEmpty(repairOrderConsumerRecordService.queryAll(searchParams)))
				return true;
			msg.setOperateType(json.getString("operateType"));
			msg.setUniqueId(uniqueId);
			msg.setBornTimestamp(bornTimestamp);
			orgMessageHandleService.handleOrgInfo(msg);
		} catch (Exception e) {
			// 消费失败
			status = MQStatus.CONSUMER_FAIL;
			log.error("消费消息处理组织业务异常: {},消息内容：{}", e.getMessage(), message);
			errMsg = e.getMessage();
			dingDingMsg.sendMessage("消费消息处理组织业务异常", e.getMessage(), message, null);

		} finally {
			RepairOrderConsumerRecord workInfo = new RepairOrderConsumerRecord();
			workInfo.setTopic(topic);
			workInfo.setTag(tag);
			workInfo.setUniqueId(uniqueId);
			workInfo.setConsumer(applicationName);
			// 设定消费内容
			workInfo.setContent(jsonMsg);
			// 设定操作时间
			workInfo.setOperateTime(new Date(bornTimestamp));
			// 设定消费结果
			workInfo.setStatus(status);
			workInfo.setExceptionMsg(errMsg);

			workInfo.setHandler("OrgConsumerHandle");
			// 保存处理最终结果
			repairOrderConsumerRecordService.save(workInfo);
		}

		return true;
	}

}
