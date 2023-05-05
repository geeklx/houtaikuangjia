package com.fosung.workbench.service.sys;

import com.fosung.framework.common.support.service.AppBaseDataService;
import com.fosung.workbench.entity.sys.SysDictDataEntity;

public interface SysDictDataService extends AppBaseDataService<SysDictDataEntity, Long> {



    String getItemName(String dictValue, String dictType, Long projectId);
}

