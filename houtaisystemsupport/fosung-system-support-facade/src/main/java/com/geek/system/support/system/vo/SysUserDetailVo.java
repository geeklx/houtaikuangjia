package com.geek.system.support.system.vo;

import com.geek.system.support.system.entity.sys.SysPostEntity;
import com.geek.system.support.system.entity.sys.SysOrgEntity;
import com.geek.system.support.system.entity.sys.SysUserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserDetailVo extends SysUserEntity {


    /**
     * 用户包含的角色
     */
    private List<SysRoleAndResourceVo> sysRoleAndResourceVos;

    /**
     * 用户包含的app
     */
   // private List<SysApplicationEntity> pfApplicationEntities;

    /**
     * 用户的岗位
     */
    private List<SysPostEntity> sysPostEntities;

    /**
     * 组织信息
     */
    private SysOrgEntity sysOrgEntity;
}
