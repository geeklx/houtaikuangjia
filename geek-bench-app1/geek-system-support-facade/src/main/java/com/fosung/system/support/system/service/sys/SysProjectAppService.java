package com.fosung.system.support.system.service.sys;

import java.util.HashMap;
import java.util.List;

import com.fosung.system.support.system.entity.sys.SysProjectApp;
import com.fosung.framework.common.support.service.AppBaseDataService;

public interface SysProjectAppService extends AppBaseDataService<SysProjectApp, Long> {

    /**
     * 描述: 通过应用id查询租户信息
     * @param searchParam
     * @return java.util.List<com.fosung.system.support.system.entity.sys.SysProjectApp>
     * @author fuhao
     * @date 2021/12/31 10:12
     **/
    List<SysProjectApp> queryInfo(HashMap<String, Object> searchParam);
}

