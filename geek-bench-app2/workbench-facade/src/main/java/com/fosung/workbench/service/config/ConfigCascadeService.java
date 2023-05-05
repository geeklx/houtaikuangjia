package com.fosung.workbench.service.config;

import com.fosung.framework.common.support.service.AppBaseDataService;
import com.fosung.workbench.entity.config.ConfigCascadeEntity;

import java.util.List;
import java.util.Map;

/**
 * 描述:  级联配置服务层
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
public interface ConfigCascadeService extends AppBaseDataService<ConfigCascadeEntity, Long> {

    /**
     * 描述:  查询信息
     * @createDate: 2021/11/1 10:37
     * @author: gaojian
     * @modify:
     * @param params
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String,Object>> queryInfo(Map<String,Object> params);
}

