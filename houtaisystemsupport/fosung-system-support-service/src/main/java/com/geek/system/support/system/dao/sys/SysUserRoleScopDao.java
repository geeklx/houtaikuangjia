package com.geek.system.support.system.dao.sys;

import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.geek.system.support.system.entity.sys.SysUserRoleScopEntity;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface SysUserRoleScopDao extends AppJPABaseDao<SysUserRoleScopEntity, Long>{

    /**
     * 描述: 删除角色管理范围
     * @param searchBindParam
     * @return void
     * @author fuhao
     * @date 2021/12/6 9:09
     **/
    @MybatisQuery
    void deleteUserRoleScope(@Param("params") HashMap<String, Object> searchBindParam);

    /**
     * 描述: 删除角色不等于空的
     * @param searchBindParam
     * @return void
     * @author fuhao
     * @date 2022/1/13 17:27
     **/
    @MybatisQuery
    void deleteRoleIdIsNot(@Param("params") HashMap<String, Object> searchBindParam);

    /**
     * 描述: 查询全部用户绑定的范围
     * @param searchBindParam
     * @return java.util.List<com.fosung.system.support.system.entity.sys.SysUserRoleScopEntity>
     * @author fuhao
     * @date 2021/12/6 9:26
     **/
    @MybatisQuery
    List<SysUserRoleScopEntity> queryAllUserRoleScope(@Param("params")HashMap<String, Object> searchBindParam);
}