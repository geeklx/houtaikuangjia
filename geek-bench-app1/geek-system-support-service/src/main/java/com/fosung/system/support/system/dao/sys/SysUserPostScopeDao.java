package com.fosung.system.support.system.dao.sys;

import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.fosung.system.support.system.entity.sys.SysPostEntity;
import com.fosung.system.support.system.entity.sys.SysUserPostScopeEntity;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface SysUserPostScopeDao extends AppJPABaseDao<SysUserPostScopeEntity, Long>{


    @MybatisQuery
    List<SysPostEntity> queryPostByUserIds(@Param("params") HashMap<String, Object> userParams);
}