package com.geek.workbench.service.ums;

import com.alibaba.fastjson.JSONObject;
import com.fosung.cloud.ums.rest.dto.send.msg.UmsUserMsgInfo;
import com.fosung.framework.delaytask.exec.AppLocalDelayTaskService;
import com.geek.workbench.service.feign.GwapiUmsSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author gaojian
 * @Date 2022/2/22 17:08
 * @Version V1.0
 */
@Service("umsDelayTask")
@Slf4j
public class UmsDelayTask implements AppLocalDelayTaskService {

    /**
     * 描述:  注入网关统一消息服务
     * @createDate: 2022/2/21 9:31
     * @author: gaojian
     */
    @Autowired
    private GwapiUmsSendService gwapiUmsService;

    @Override
    public void excute(JSONObject json) {

        // 1. 调用网关上的统一消息服务
        UmsUserMsgInfo userMsgInfo = JSONObject.parseObject(json.toJSONString(), UmsUserMsgInfo.class);
        Long result = gwapiUmsService.sendUserMsg(userMsgInfo);
        log.info("调用统一消息返回结果信息：" + result);

    }
}
