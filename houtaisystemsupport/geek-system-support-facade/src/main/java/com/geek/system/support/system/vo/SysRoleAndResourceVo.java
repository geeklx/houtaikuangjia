package com.geek.system.support.system.vo;

import com.geek.system.support.system.entity.sys.SysResourceEntity;
import com.geek.system.support.system.entity.sys.SysRoleEntity;
import com.geek.system.support.system.entity.sys.SysUserRoleScopEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SysRoleAndResourceVo extends SysRoleEntity {

    private Long roleId;

    /**
     * 角色包含的资源
     */
    private List<SysResourceEntity> sysResourceEntities;

    /**
     * 角色管理范围 组织id
     */
    private Long orgId;

    /**
     * 角色管理范围组织编码
     */
    private String orgCode;

    private List<SysUserRoleScopEntity> sysUserRoleScopEntity;

}
