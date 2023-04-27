package com.geek.workbench.service.search;

import com.geek.workbench.dict.SearchOperateType;
import com.geek.workbench.entity.terminal.TerminalApplicationConfigEntity;

/**
 * @Description 统一搜索服务
 * @Author gaojian
 * @Date 2022/3/2 10:26
 * @Version V1.0
 */
public interface UniSearchService {

    /**
     * 描述:  同步数据到统一搜索
     * @createDate: 2022/3/2 10:30
     * @author: gaojian
     * @modify:
     * @param terminalApplicationConfigEntity
     * @param searchOperateType
     * @return: void
     */
    void searchSynchronizationData(TerminalApplicationConfigEntity terminalApplicationConfigEntity,
                                          SearchOperateType searchOperateType);
}
