package com.fosung.workbench.service.config;

import com.fosung.framework.common.support.service.AppBaseDataService;
import com.fosung.workbench.dto.other.ConfigCertificationUrlAndParam;
import com.fosung.workbench.entity.config.ConfigCertification;

import java.util.Map;

/**
 * 描述:  认证配置服务层
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
public interface ConfigCertificationService extends AppBaseDataService<ConfigCertification, Long> {

    ConfigCertificationUrlAndParam getCacheTerminal(Long id);

    /**
     * 描述:  查询认证授权的信息
     * @createDate: 2021/10/26 9:45
     * @author: gaojian
     * @modify:
     * @param id
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    Map<String,Object> queryInfo(Long id);
}

