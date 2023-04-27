package com.geek.system.support.system.dao.sys;

import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.geek.system.support.system.entity.sys.SysUserResourceEntity;
import org.apache.ibatis.annotations.Param;

public interface SysUserResourceDao extends AppJPABaseDao<SysUserResourceEntity, Long>{

    @MybatisQuery
    Integer deleteByUserIdAndRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

}