package com.fosung.workbench.service.shorturl;

import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.delaytask.exec.AppLocalDelayTaskService;
import com.fosung.workbench.common.ShortUrlGlobalVariableKey;
import com.fosung.workbench.entity.shorturl.ShortUrlMappingEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description 端网址映射延迟任务
 * @Author gaojian
 * @Date 2022/1/17 15:32
 * @Version V1.0
 */
@Service("shortUrlMappingDelayTask")
@Slf4j
public class ShortUrlMappingDelay implements AppLocalDelayTaskService {

    /**
     * 描述:  短 URL 持久化映射
     * @createDate: 2022/1/17 11:33
     * @author: gaojian
     */
    @Autowired
    private ShortUrlMappingService shortUrlMappingService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excute(JSONObject json) {

        // 1. 保存映射关系
        ShortUrlMappingEntity shortUrlMappingEntity = new ShortUrlMappingEntity();
        shortUrlMappingEntity.setLongUrl(json.getString(ShortUrlGlobalVariableKey.LONG_URL));
        shortUrlMappingEntity.setShortUrl(json.getString(ShortUrlGlobalVariableKey.SHORT_URL));
        shortUrlMappingEntity.setStartTime(json.getDate(ShortUrlGlobalVariableKey.START_TIME));
        shortUrlMappingEntity.setValidTime(json.getInteger(ShortUrlGlobalVariableKey.VALID_TIME));
        shortUrlMappingService.save(shortUrlMappingEntity);

    }
}
