package com.geek.system.support.system.dao.sys;

import com.fosung.framework.dao.config.mybatis.page.MybatisPage;
import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.geek.system.support.system.entity.sys.*;
import com.geek.system.support.system.entity.sys.SysProjectApp;
import com.geek.system.support.system.entity.sys.SysRoleAppEntity;
import com.geek.system.support.system.entity.sys.SysRoleEntity;
import com.geek.system.support.system.entity.sys.SysRoleResourceEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface SysRoleDao extends AppJPABaseDao<SysRoleEntity, Long>{

    /**
     * 描述: 分页查询
     * @param params
     * @param pageable
     * @return com.fosung.framework.dao.config.mybatis.page.MybatisPage<com.fosung.system.support.system.entity.sys.SysRoleEntity>
     * @author fuhao
     * @date 2021/11/27 15:53
     **/
    @MybatisQuery
    MybatisPage<SysRoleEntity> queryPage(@Param("params") Map<String,Object> params, Pageable pageable);

    /**
     * 描述: 查询角色权限
     * @param searchParam
     * @return java.util.List<com.fosung.system.support.system.entity.sys.SysRoleResourceEntity>
     * @author fuhao
     * @date 2021/11/27 15:54
     **/
    @MybatisQuery
    List<SysRoleResourceEntity> queryRolePower(@Param("params") Map<String, Object> searchParam);

    /**
     * 描述: 查询角色绑定应用
     * @param searchParam
     * @return java.util.List<com.fosung.system.support.system.entity.sys.SysRoleResourceEntity>
     * @author fuhao
     * @date 2022/2/16 16:04
     **/
    @MybatisQuery
    List<SysRoleAppEntity> queryRolePowerApp(@Param("params") Map<String, Object> searchParam);

    /**
     * 描述: 查询租户绑定应用信息
     * @param params
     * @return java.util.List<com.fosung.system.support.system.entity.sys.SysApplicationEntity>
     * @author fuhao
     * @date 2021/12/15 14:53
     **/
    @MybatisQuery
    List<SysProjectApp> queryProjectApp(@Param("params") Map<String, Object> params);

    @MybatisQuery
    List<Map<String,Object>> queryRoleByAppIds(@Param("projectId") Long projectId,@Param("ids") List<Long> ids);

    @MybatisQuery
    void deleteByRoleId(@Param("roleId") Long roleId,@Param("tableName") String tableName);
}