package com.fosung.workbench.service.terminal;

import com.fosung.framework.common.support.service.AppBaseDataService;
import com.fosung.workbench.entity.terminal.TerminalVersionUpgradeRangeEntity;

import java.util.List;
import java.util.Map;

/**
 * 描述:  终端版本更新范围服务
 * @createDate: 2022/2/23 17:18
 * @author: gaojian
 */
public interface TerminalVersionUpgradeRangeService extends AppBaseDataService<TerminalVersionUpgradeRangeEntity, Long> {

    /**
     * 描述:  删除版本历史范围变更记录
     * @createDate: 2022/2/23 17:18
     * @author: gaojian
     * @modify:
     * @param id
     * @return: java.lang.Integer
     */
    Integer deleteHistoryLog(Long id);

    /**
     * 描述:  查询真实的全部
     * @createDate: 2022/2/23 18:05
     * @author: gaojian
     * @modify:
     * @param searchParams
     * @return: java.util.List<com.fosung.workbench.entity.terminal.TerminalVersionUpgradeRangeEntity>
     */
    List<TerminalVersionUpgradeRangeEntity> queryRealAll(Map<String,Object> searchParams);

}

