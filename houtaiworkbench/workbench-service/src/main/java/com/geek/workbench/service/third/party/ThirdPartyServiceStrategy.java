package com.geek.workbench.service.third.party;

import com.fosung.framework.common.exception.AppException;
import com.geek.workbench.common.MessageContent;
import com.geek.workbench.dict.ConfigType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 业务服务策略类
 * @Author gaojian
 * @Date 2021年11月24日10:38:38
 * @Version V1.0
 */
@Component
public class ThirdPartyServiceStrategy {

    /**
     * @Author gaojian
     * @Description 三方服务策略对象
     */
    @Autowired
    private Map<String, ThirdPartyService> thirdPartyServiceStrategy = new ConcurrentHashMap<>(8);

    /**
     * 描述:  获取三方服务
     * @createDate: 2022/2/28 17:06
     * @author: gaojian
     * @modify:
     * @param component
     * @return: com.geek.workbench.service.third.party.ThirdPartyService
     */
    public ThirdPartyService getThirdPartyService(ConfigType component) {
        ThirdPartyService strategy = thirdPartyServiceStrategy.get(component.name());
        if(strategy == null){
            throw new AppException(MessageContent.THIRD_PARTY_IS_NULL);
        }
        return strategy;
    }

}
