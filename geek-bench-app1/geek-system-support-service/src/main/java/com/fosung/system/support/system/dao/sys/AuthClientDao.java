package com.fosung.system.support.system.dao.sys;

import com.fosung.framework.dao.config.mybatis.page.MybatisPage;
import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.fosung.system.support.system.entity.sys.AuthClientEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Set;

public interface AuthClientDao extends AppJPABaseDao<AuthClientEntity, Long> {

    @MybatisQuery
    MybatisPage<AuthClientEntity> queryAuthClientPage(@Param("param") Map<String,Object> param, Pageable pageable);

    @MybatisQuery
    Set<Map<String,Object>> queryAppByUserId(@Param("param") Map<String,Object> param);

    @MybatisQuery
    Set<Map<String,Object>> queryUserOrgsByUserAndClient(@Param("param") Map<String,Object> param);

    @MybatisQuery
    AuthClientEntity queryAuthClientById(Long id);
}
