package com.geek.system.support.system.dao.sys;

import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.geek.system.support.system.entity.sys.SysProjectApp;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface SysProjectAppDao extends AppJPABaseDao<SysProjectApp, Long>{

    /**
     * 描述: 通过应用id查询租户信息
     * @param searchParam
     * @return java.util.List<com.fosung.system.support.system.entity.sys.SysProjectApp>
     * @author fuhao
     * @date 2021/12/31 10:13
     **/
    @MybatisQuery
    List<SysProjectApp> queryInfo(@Param("params") HashMap<String, Object> searchParam);
}