package com.geek.system.support.system.service.sys;

import com.geek.system.support.system.entity.sys.SysUserResourceEntity;
import com.fosung.framework.common.support.service.AppBaseDataService;

import java.util.List;

public interface SysUserResourceService extends AppBaseDataService<SysUserResourceEntity, Long> {


    void saveAfterDelete(List<SysUserResourceEntity> sysUserResourceEntities);

    void delUserResource(List<SysUserResourceEntity> sysUserResourceEntities);

}

