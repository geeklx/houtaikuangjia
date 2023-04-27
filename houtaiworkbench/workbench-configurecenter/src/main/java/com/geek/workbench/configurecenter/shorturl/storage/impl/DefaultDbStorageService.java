package com.geek.workbench.configurecenter.shorturl.storage.impl;

import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.common.exception.AppException;
import com.fosung.framework.delaytask.AppDelayTask;
import com.fosung.framework.delaytask.AppDelayTaskService;
import com.geek.workbench.common.ShortUrlGlobalVariableKey;
import com.geek.workbench.configurecenter.shorturl.storage.StorageService;
import com.geek.workbench.entity.shorturl.NumberSenderEntity;
import com.geek.workbench.service.shorturl.NumberSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description 默认存储服务实现
 * @Author gaojian
 * @Date 2022/1/17 10:55
 * @Version V1.0
 */
@Service
@Slf4j
public class DefaultDbStorageService implements StorageService {

    /**
     * 描述:  延迟任务服务
     * @createDate: 2022/1/17 15:41
     * @author: gaojian
     */
    @Autowired
    private AppDelayTaskService appDelayTaskService;

    /**
     * 描述:  发号器服务
     * @createDate: 2022/1/17 15:41
     * @author: gaojian
     */
    @Autowired
    private NumberSenderService numberSenderService;

    /**
     * 描述:  持久化
     *
     * @param shortUrl
     * @param longUrl
     * @param startTime
     * @param validTime
     * @createDate: 2022/1/17 10:50
     * @author: gaojian
     * @modify:
     * @return: void
     */
    @Override
    public void saveMapping(String shortUrl, String longUrl, Date startTime, Long validTime) {

        // 1. 组装信息
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ShortUrlGlobalVariableKey.SHORT_URL,shortUrl);
        jsonObject.put(ShortUrlGlobalVariableKey.LONG_URL,longUrl);
        jsonObject.put(ShortUrlGlobalVariableKey.START_TIME,startTime);
        jsonObject.put(ShortUrlGlobalVariableKey.VALID_TIME,validTime);

        // 2. 保存延迟任务
        createLocalDelayTask(jsonObject,shortUrl);
    }

    /**
     * 描述:  保存发号器发到了多少号
     *
     * @param numberSenderKey
     * @param number
     * @createDate: 2022/1/17 10:52
     * @author: gaojian
     * @modify:
     * @return: void
     */
    @Override
    public void saveNumberSender(String numberSenderKey, Long number) {

        // 1. 组装信息
        NumberSenderEntity numberSenderEntity = new NumberSenderEntity();
        numberSenderEntity.setNumberSenderKey(numberSenderKey);
        numberSenderEntity.setNumber(number);

        // 2. 保存信息
        numberSenderService.updateNumber(numberSenderEntity);
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
    public void createLocalDelayTask(JSONObject params,String businessId) {

        // 1. 非空判断
        if(params == null ){
            throw new AppException("创建延迟任务失败,必要信息为空，请确认！");
        }

        // 2. 创建延迟任务
        AppDelayTask appDelayTask = new AppDelayTask();
        appDelayTask.setType("local");
        appDelayTask.setBusinessId(businessId);
        appDelayTask.setMaxRetry(3);
        appDelayTask.setUrl("shortUrlMappingDelayTask");
        appDelayTask.setParams(params.toJSONString());

        log.info(" call local delay task json info :" + JSONObject.toJSONString(appDelayTask));
        appDelayTaskService.createDelayTask(appDelayTask);
    }
}
