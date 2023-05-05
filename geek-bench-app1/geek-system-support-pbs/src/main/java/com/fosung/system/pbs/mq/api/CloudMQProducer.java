package com.fosung.system.pbs.mq.api;

import com.fosung.framework.web.http.ResponseParam;
import com.fosung.system.pbs.common.CloudProperties;
import com.fosung.system.pbs.common.CloudRequestTemplate;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class CloudMQProducer {
    @Autowired
    protected CloudRequestTemplate cloudRequestTemplate;

    @Autowired
    protected CloudProperties cloudProperties;

    /**
     * 发送队列消息
     *
     * @param system  topic
     * @param module  tag
     * @param message 消息内容
     * @param other   是否调用第三方工单
     * @throws Exception
     */
    public ResponseParam sendQueue(String system, String module, String message, Boolean other) {
        Map<String, String> params = Maps.newHashMap();
        params.put("system", system);
        params.put("module", module);
        params.put("message", message);
        params.put("other", other.toString());
        ResponseParam responseParam = cloudRequestTemplate.doRequestForBean(httpClient -> {
            return httpClient.get(cloudProperties.getUrls().getMqSendQueue()).queryString(params);
        }, ResponseParam.class);
        return responseParam;
    }

    /**
     * 发送消息
     *
     * @param system  topic
     * @param module  tag
     * @param message 消息内容
     * @param other   是否调用第三方工单
     * @throws Exception
     */
    public ResponseParam send(String system, String module, String message, Boolean other) {
        Map<String, String> params = Maps.newHashMap();
        params.put("system", system);
        params.put("module", module);
        params.put("message", message);
        params.put("other", other.toString());
        ResponseParam responseParam = cloudRequestTemplate.doRequestForBean(httpClient -> {
            return httpClient.get(cloudProperties.getUrls().getMqSend()).queryString(params);
        }, ResponseParam.class);
        return responseParam;
    }

}
