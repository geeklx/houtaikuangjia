package com.geek.system.support.system.service.sys;

import com.fosung.framework.common.support.service.AppBaseDataService;
import com.geek.system.support.system.entity.sys.SysUserPostEntity;


public interface SysUserPostService extends AppBaseDataService<SysUserPostEntity, Long> {


    void deleteByPostId(Long roleId, Long appId);

    void deleteByUserId(Long userId, Long appId);

}

