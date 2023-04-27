package com.geek.system.support.system.dao.pbs;


import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.geek.system.support.system.entity.sys.SysRoleEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PbsRoleDao extends AppJPABaseDao<SysRoleEntity, Long> {


    @MybatisQuery
    List<Map<String,Object>> queryAppByRole(@Param("param") Map<String,Object> param);

}