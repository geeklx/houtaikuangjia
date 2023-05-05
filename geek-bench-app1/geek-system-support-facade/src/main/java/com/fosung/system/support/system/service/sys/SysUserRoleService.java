package com.fosung.system.support.system.service.sys;

import java.util.List;
import java.util.Map;

import com.fosung.framework.common.support.service.AppBaseDataService;
import com.fosung.system.support.system.entity.sys.SysUserRoleEntity;


public interface SysUserRoleService extends AppBaseDataService<SysUserRoleEntity, Long> {


    void deleteByRoleId(Long roleId );

    void deleteByUserId(Long userId);

    void reBindRole(List<SysUserRoleEntity> sysUserRoleEntities);

    /**
     * 描述: 根据角色编码查询人员
     * @createDate: 2021/11/1 19:20
     * @author: gaojian
     * @modify:
     * @param appId
     * @param roleCode
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String,Object>> queryUserByRoleCode(Long appId, String roleCode);

}

