package com.fosung.system.support.system.service.sys;

import com.fosung.framework.common.support.service.AppBaseDataService;
import com.fosung.system.support.system.entity.sys.SysRoleAppEntity;

import java.util.List;


public interface SysRoleAppService extends AppBaseDataService<SysRoleAppEntity, Long> {


    List<SysRoleAppEntity> getRoleAppByRole(Long roleId);

}

