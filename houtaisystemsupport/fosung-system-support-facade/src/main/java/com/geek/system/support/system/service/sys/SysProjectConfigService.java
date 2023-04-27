package com.geek.system.support.system.service.sys;

import com.fosung.framework.common.support.service.AppBaseDataService;
import com.geek.system.support.system.entity.sys.SysProjectConfigEntity;



public interface SysProjectConfigService extends AppBaseDataService<SysProjectConfigEntity, Long> {


    SysProjectConfigEntity getByProjectId(Long projectId);
}

