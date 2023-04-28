package com.geek.workbench.service.application;

import com.fosung.framework.common.support.service.AppBaseDataService;
import com.geek.workbench.AppBean.ApkBasic;
import com.geek.workbench.entity.application.ApplicationBaseConfigEntity;
import com.geek.workbench.entity.application.ApplicationConfigAndroidEntity;
import com.geek.workbench.entity.terminal.TerminalVersionEntity;

import java.util.Map;

/**
 * 描述:  安卓应用配置服务层
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
public interface ApplicationConfigAndroidService extends AppBaseDataService<ApplicationConfigAndroidEntity, Long> {
	
    /**
     * 描述:  升级APP
     * @createDate: 2021/10/18 19:24
     * @author: gaojian
     * @modify:
     * @param applicationConfigAndroidEntity
     * @return: void
     */
	void upgradeApp(ApplicationConfigAndroidEntity applicationConfigAndroidEntity);

    /**
     * 描述:  修改信息
     * @createDate: 2021/10/22 9:00
     * @author: gaojian
     * @modify:
     * @param applicationConfigAndroidEntity
     * @return: void
     */
    void updateApp(ApplicationConfigAndroidEntity applicationConfigAndroidEntity);

    /**
     * 描述:  根据应用id查询第一个版本信息
     * @createDate: 2021/10/26 17:14
     * @author: gaojian
     * @modify:
     * @param id
     * @return: com.geek.workbench.entity.application.ApplicationConfigAndroidEntity
     */
    ApplicationBaseConfigEntity queryFirstByAppIdOrderByCreateDatetime(Long id);

    /**
     * 获取apk信息
     * @param androidEntity
     * @param p
     * @return
     */
    ApkBasic getInfo(TerminalVersionEntity terminalVersion, ApplicationConfigAndroidEntity androidEntity, Map p);

}

