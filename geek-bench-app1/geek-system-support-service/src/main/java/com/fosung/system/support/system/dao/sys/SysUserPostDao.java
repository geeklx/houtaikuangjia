package com.fosung.system.support.system.dao.sys;

import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.fosung.system.support.system.entity.sys.SysUserPostEntity;
import org.apache.ibatis.annotations.Param;

public interface SysUserPostDao extends AppJPABaseDao<SysUserPostEntity, Long>{

    @MybatisQuery
    void deleteByPostId(@Param("userId") Long roleId,
                        @Param("appId") Long appId);


    @MybatisQuery
    void deleteByUserId(@Param("userId") Long userId,
                        @Param("appId") Long appId);

}