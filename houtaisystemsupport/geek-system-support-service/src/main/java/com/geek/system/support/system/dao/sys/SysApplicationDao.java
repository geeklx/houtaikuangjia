package com.geek.system.support.system.dao.sys;

import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.geek.system.support.system.entity.sys.SysApplicationEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysApplicationDao extends AppJPABaseDao<SysApplicationEntity, Long>{

    @MybatisQuery
   List<SysApplicationEntity> selectByProjectId(@Param("projectId") Long projectId);
}