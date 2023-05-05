package com.fosung.system.support.system.dao.sys;

import com.fosung.framework.dao.config.mybatis.page.MybatisPage;
import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.fosung.system.support.system.entity.sys.SysOrgEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface SysOrgDao extends AppJPABaseDao<SysOrgEntity, Long>{

    @MybatisQuery
    SysOrgEntity selectByUserId(@Param("userId") Long userId);

    @MybatisQuery
    MybatisPage<SysOrgEntity> queryPage(@Param("searchParam") Map<String,Object> searchParam, Pageable pageable);

    @MybatisQuery
    List<SysOrgEntity> queryOrgsByParentId(@Param("searchParam") Map<String,Object> searchParam);


    @MybatisQuery
    SysOrgEntity queryParentOrgById(@Param("searchParam") Map<String,Object> searchParam);

    @MybatisQuery
    List<Map<String,Object>> queryScopOrgByUser(@Param("searchParam") Map<String,Object> searchParam);



}