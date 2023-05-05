package com.fosung.workbench.service.ums;

import com.alibaba.fastjson.JSONObject;
import com.fosung.cloud.ums.rest.dto.send.msg.UmsUserMsgInfo;
import com.fosung.framework.common.exception.AppException;
import com.fosung.framework.delaytask.AppDelayTask;
import com.fosung.framework.delaytask.AppDelayTaskService;
import com.fosung.workbench.dto.ums.UmsDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 统一消息服务适配器
 * @Author gaojian
 * @Date 2022/2/21 9:14
 * @Version V1.0
 */
@Service
@Slf4j
public class UmsServiceAdapter implements UmsService{

    @Autowired
    private AppDelayTaskService appDelayTaskService;

    /**
     * 发送消息，发送目标仅为人员id
     *
     * @param umsDto 消息信息
     * @return
     */
    @Override
    public void sendUserMsg(UmsDto umsDto) {

        // 1. 组装参数
        UmsUserMsgInfo userMsgInfo = new UmsUserMsgInfo();
        userMsgInfo.setUserIds(umsDto.getUserIds());
        Map<String,String> customsParams = new HashMap<>(8);
        customsParams.put("type",umsDto.getType().name());
        userMsgInfo.setCustomerParams(customsParams);
        Map<String,String> templateParams = new HashMap<>(8);
        templateParams.put("messageContent",umsDto.getMessageContent());
        userMsgInfo.setTemplateParams(templateParams);
        userMsgInfo.setModuleCode("");
        userMsgInfo.setOrgId("1000");
        userMsgInfo.setOrgCode("0001");
        userMsgInfo.setAccessKey("workbeach_ak");
        userMsgInfo.setTemplateCode(umsDto.getTemplateCode());

        // 2. 创建本地延迟任务 重试 3 次 为了可以重试和记录调用远程日志这里不直接调用使用延迟任务调用
        createLocalDelayTask(JSONObject.toJSONString(userMsgInfo),String.valueOf(System.currentTimeMillis()));
    }

    /**
     * 描述:  创建本地延迟任务
     * @createDate: 2022/1/17 15:39
     * @author: gaojian
     * @modify:
     * @param params
     * @param businessId
     * @return: void
     */
    public void createLocalDelayTask(String params,String businessId) {

        // 1. 非空判断
        if(StringUtils.isBlank(params)){
            throw new AppException("创建延迟任务失败,必要信息为空，请确认！");
        }

        // 2. 创建延迟任务
        AppDelayTask appDelayTask = new AppDelayTask();
        appDelayTask.setType("local");
        appDelayTask.setBusinessId(businessId);
        appDelayTask.setMaxRetry(3);
        appDelayTask.setUrl("umsDelayTask");
        appDelayTask.setParams(params);

        log.info(" call local delay task json info :" + JSONObject.toJSONString(appDelayTask));
        appDelayTaskService.createDelayTask(appDelayTask);
    }

}
