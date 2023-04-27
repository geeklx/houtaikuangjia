package com.geek.system.support.system.service.sys;

import java.util.List;
import java.util.Map;

import com.geek.system.support.system.entity.sys.SysResourceAppEntity;
import com.fosung.framework.common.support.service.AppBaseDataService;

public interface SysResourceAppService extends AppBaseDataService<SysResourceAppEntity, Long> {


    List<Map<String,Object>> getWorkBenchApp(Long appId);

}

