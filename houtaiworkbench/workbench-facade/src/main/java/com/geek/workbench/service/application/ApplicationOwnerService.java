package com.geek.workbench.service.application;

import com.fosung.framework.common.support.service.AppBaseDataService;
import com.geek.workbench.entity.application.ApplicationOwnerEntity;

import java.util.List;

public interface ApplicationOwnerService extends AppBaseDataService<ApplicationOwnerEntity, Long> {


    /**
     * 得到我的应用
     * @param userId
     * @param terminalId
     * @return
     */
    List<ApplicationOwnerEntity> getMyApp(String userId, Long terminalId);

    /**
     * 移除用户缓存
     * @param userId
     * @param terminalId
     */
    void deleteMyAppCache(String userId, Long terminalId);

    /**
     * 获取我的应用
     * @param terminalId
     * @param userId
     * @return
     */
    List queryMyApp(Long terminalId,Long userId);

}

