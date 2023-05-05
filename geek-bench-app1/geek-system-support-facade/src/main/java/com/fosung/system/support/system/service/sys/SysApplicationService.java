package com.fosung.system.support.system.service.sys;

import com.fosung.framework.common.support.service.AppBaseDataService;
import com.fosung.system.support.system.entity.sys.SysApplicationEntity;

import java.util.List;

public interface SysApplicationService extends AppBaseDataService<SysApplicationEntity, Long> {


    List<SysApplicationEntity> selectByProjectId(Long projectId);

    void deleteInfo(List<Long> collect);
}

