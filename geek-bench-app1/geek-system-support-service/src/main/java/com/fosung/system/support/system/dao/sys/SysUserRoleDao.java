package com.fosung.system.support.system.dao.sys;

import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.fosung.system.support.system.entity.sys.SysUserRoleEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysUserRoleDao extends AppJPABaseDao<SysUserRoleEntity, Long>{

    @MybatisQuery
    void deleteByRoleId(@Param("roleId") Long roleId);

    @MybatisQuery
    void deleteByUserId(@Param("userId") Long userId);

    /**
     * 描述:  根据角色编码查询用户信息
     * @createDate: 2021/11/1 19:22
     * @author: gaojian
     * @modify:
     * @param appId
     * @param roleCode
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @MybatisQuery
    List<Map<String,Object>> queryUserByRoleCode(@Param("appId") Long appId, @Param("roleCode") String roleCode);

}