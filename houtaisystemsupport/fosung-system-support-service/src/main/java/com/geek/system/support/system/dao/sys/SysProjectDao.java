package com.geek.system.support.system.dao.sys;

import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.geek.system.support.system.entity.sys.SysProjectEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface SysProjectDao extends AppJPABaseDao<SysProjectEntity, Long>{

    /**
     * 描述: 根据租户id物理删除
     * @param delParam
     * @return void
     * @author fuhao
     * @date 2021/12/27 15:09
     **/
    @MybatisQuery
    void deleteAllByProjectId(@Param("params") Map<String,Object> delParam);

    /**
     * 描述: 根据租户id逻辑删除
     * @param delParam
     * @return void
     * @author fuhao
     * @date 2021/12/27 15:10
     **/
    @MybatisQuery
    void updateAllByProjectId(@Param("params")Map<String, Object> delParam);
}