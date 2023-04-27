package com.geek.system.support.system.service.sys;

import com.fosung.framework.common.support.service.AppBaseDataService;
import com.geek.system.support.system.entity.sys.SysDictDataEntity;

public interface SysDictDataService extends AppBaseDataService<SysDictDataEntity, Long> {



    String getItemName(String dictValue, String dictType,Long projectId);
}

