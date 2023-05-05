package com.fosung.system.support.system.service.sys;

import java.util.List;

import com.fosung.framework.common.support.service.AppBaseDataService;
import com.fosung.system.support.system.entity.sys.SysRoleResourceEntity;


public interface SysRoleResourceService extends AppBaseDataService<SysRoleResourceEntity, Long> {


    List<SysRoleResourceEntity> getRoleResourceByRole(Long roleId);

}

