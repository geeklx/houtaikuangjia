package com.fosung.workbench.service.application;

import com.fosung.framework.common.support.service.AppBaseDataService;
import com.fosung.workbench.entity.application.ApplicationBaseConfigEntity;
import com.fosung.workbench.entity.application.ApplicationConfigAndroidEntity;
import com.fosung.workbench.entity.application.ApplicationConfigHtmlEntity;
import com.fosung.workbench.entity.application.ApplicationConfigIosEntity;

/**
 * 描述:  H5应用配置服务层
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
public interface ApplicationConfigHtmlService extends AppBaseDataService<ApplicationConfigHtmlEntity, Long> {

    /**
     * 描述:  升级APP
     * @createDate: 2021/10/18 19:24
     * @author: gaojian
     * @modify:
     * @param applicationConfigHtmlEntity
     * @return: void
     */
    void upgradeApp(ApplicationConfigHtmlEntity applicationConfigHtmlEntity);

    /**
     * 描述:  修改信息
     * @createDate: 2021/10/22 9:00
     * @author: gaojian
     * @modify:
     * @param applicationConfigHtmlEntity
     * @return: void
     */
    void updateApp(ApplicationConfigHtmlEntity applicationConfigHtmlEntity);


    /**
     * 描述:  根据应用id查询第一个版本信息
     * @createDate: 2021/10/26 17:14
     * @author: gaojian
     * @modify:
     * @param id
     * @return: com.fosung.workbench.entity.application.ApplicationConfigAndroidEntity
     */
    ApplicationBaseConfigEntity queryFirstByAppIdOrderByCreateDatetime(Long id);
}

