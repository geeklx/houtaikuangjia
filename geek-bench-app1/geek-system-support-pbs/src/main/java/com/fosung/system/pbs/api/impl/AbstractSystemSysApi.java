package com.fosung.system.pbs.api.impl;

import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.system.pbs.api.SystemSysApi;

import java.util.List;
import java.util.Map;

public abstract class AbstractSystemSysApi extends AppIBaseController implements SystemSysApi {
    @Override
    public ResponseParam querySysTemDict(String dictType) {
        return null;
    }

    @Override
    public ResponseParam queryResourceByApp(Long appId) {
        return null;
    }

    @Override
    public List<Map<String,Object>> queryDictByTypes(String project, String dictType){
        return null;
    }

    /**
     * 查询租户列表
     *
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author liuke
     * @date 2022/8/22 10:20
     */
    @Override
    public List<Map<String, Object>> queryProjectList() {
        return null;
    }
}
