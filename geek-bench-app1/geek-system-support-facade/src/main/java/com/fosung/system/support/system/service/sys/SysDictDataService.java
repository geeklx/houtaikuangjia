package com.fosung.system.support.system.service.sys;

import com.fosung.framework.common.support.service.AppBaseDataService;
import com.fosung.system.support.system.entity.sys.SysDictDataEntity;

public interface SysDictDataService extends AppBaseDataService<SysDictDataEntity, Long> {



    String getItemName(String dictValue, String dictType,Long projectId);
}

