package com.geek.workbench.service.config;

import com.fosung.framework.common.support.service.AppBaseDataService;
import com.geek.workbench.entity.config.TerminalConfigApiEntity;

import java.util.List;
import java.util.Map;

/**
 * @Description 终端接口配置组服务层
 * @Author gaojian
 * @Date 2021/10/24 10:25
 * @Version V1.0
 */
public interface TerminalConfigApiService extends AppBaseDataService<TerminalConfigApiEntity, Long> {

    /**
     * 描述:  保存授权信息
     * @createDate: 2021/10/24 20:47
     * @author: gaojian
     * @modify:
     * @param list
     * @return: void
     */
    void saveInfo(List<TerminalConfigApiEntity> list);

    /**
     * 描述:  查询应用信息
     * @createDate: 2021/10/25 11:08
     * @author: gaojian
     * @modify:
     * @param terminalConfigApiEntity
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    Map<String,Object> queryInfo(TerminalConfigApiEntity terminalConfigApiEntity);

    List<TerminalConfigApiEntity> getCacheTerminalApiByTerminal(Long terminalId);
}

