package com.fosung.workbench.service.busi;

import com.fosung.framework.common.support.service.AppBaseDataService;
import com.fosung.workbench.dto.busi.BusiOrgAdmDto;
import com.fosung.workbench.entity.busi.BusiOrgAdmEntity;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface BusiOrgAdmService extends AppBaseDataService<BusiOrgAdmEntity, Long> {

    /**
     * 保存地域信息
     * @param busiOrgAdmDto
     * @return
     */
    Map saveSelectedAdm(BusiOrgAdmDto busiOrgAdmDto);

    /**
     * 获取组织地域信息
     * @return
     */
    ConcurrentHashMap queryAdmTree(BusiOrgAdmDto busiOrgAdmDto);
    /**
     * 查询地域信息
     */
    ConcurrentHashMap querySelectedAdm(BusiOrgAdmDto busiOrgAdmDto);
}

