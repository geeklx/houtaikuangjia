package com.geek.workbench.rest.controller.application;

import com.fosung.framework.common.exception.AppException;
import com.fosung.framework.web.http.AppIBaseController;
import com.geek.workbench.common.MessageContent;
import com.geek.workbench.entity.application.ApplicationBaseConfigEntity;
import com.geek.workbench.entity.application.ApplicationBasicEntity;
import com.geek.workbench.service.application.ApplicationBasicService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description 抽象应用配置
 * @Author gaojian
 * @Date 2021/10/27 9:35
 * @Version V1.0
 */
public abstract class AbstractApplicationConfig extends AppIBaseController {

    /**
     * 描述:  注入应用基础服务
     * @createDate: 2021/10/27 9:39
     * @author: gaojian
     */
    @Autowired
    private ApplicationBasicService applicationBasicService;

    /**
     * 描述:  校验包名是否正确
     * @createDate: 2021/10/27 9:38
     * @author: gaojian
     * @modify:
     * @param applicationBaseConfigEntity
     * @return: void
     */
    public void checkPackageName(ApplicationBaseConfigEntity applicationBaseConfigEntity){

        // 1. 判断应用是否存在
        ApplicationBasicEntity applicationBasicEntity = applicationBasicService.get(applicationBaseConfigEntity.getAppId());
        if(applicationBasicEntity == null){
            throw new AppException(MessageContent.APP_IS_NOT_EXIST);
        }

        // 2. 包名为空则不校验
        if(StringUtils.isBlank(applicationBaseConfigEntity.getPackageName())){
            return;
        }

        // 3. 判断包名是否和配置的一样
        if(!StringUtils.equals(applicationBasicEntity.getPackageName(),applicationBaseConfigEntity.getPackageName())){
            throw new AppException(MessageContent.APP_PACKAGE_NAME_INCONFORMITY);
        }
    }
}
