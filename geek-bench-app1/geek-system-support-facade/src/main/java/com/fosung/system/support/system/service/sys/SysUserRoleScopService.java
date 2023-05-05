package com.fosung.system.support.system.service.sys;

import com.fosung.framework.common.support.service.AppBaseDataService;
import com.fosung.system.support.system.entity.sys.SysUserRoleScopEntity;

import java.util.HashMap;
import java.util.List;


public interface SysUserRoleScopService extends AppBaseDataService<SysUserRoleScopEntity, Long> {

    /**
     * 描述: 删除角色管理范围
     * @param searchBindParam
     * @return void
     * @author fuhao
     * @date 2021/12/6 9:09
     **/
    void deleteUserRoleScope(HashMap<String, Object> searchBindParam);

    /**
     * 描述: 删除角色不等于空的
     * @param searchBindParam
     * @return void
     * @author fuhao
     * @date 2022/1/13 17:27
     **/
    void deleteRoleIdIsNot(HashMap<String, Object> searchBindParam);

    /**
     * 描述: 查询全部用户绑定的范围
     * @param searchBindParam
     * @return java.util.List<com.fosung.system.support.system.entity.sys.SysUserRoleScopEntity>
     * @author fuhao
     * @date 2021/12/6 9:26
     **/
    List<SysUserRoleScopEntity> queryAllUserRoleScope(HashMap<String, Object> searchBindParam);
}

