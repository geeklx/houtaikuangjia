package com.geek.workbench.dao.application;

import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.geek.workbench.entity.application.ApplicationConfigAndroidEntity;

/**
 * 描述:  应用Android配置信息持久化接口
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
public interface ApplicationConfigAndroidDao extends AppJPABaseDao<ApplicationConfigAndroidEntity, Long>{

    /**
     * 描述:  根据应用id查询第一个版本信息
     * @createDate: 2021/10/26 17:14
     * @author: gaojian
     * @modify:
     * @param appId
     * @return: com.geek.workbench.entity.application.ApplicationConfigAndroidEntity
     */
    ApplicationConfigAndroidEntity queryFirstByAppIdOrderByCreateDatetime(Long appId);
}