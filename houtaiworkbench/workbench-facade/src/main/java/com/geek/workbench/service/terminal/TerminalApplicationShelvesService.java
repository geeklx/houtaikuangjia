package com.geek.workbench.service.terminal;

import java.util.Set;

import com.geek.workbench.dto.terminal.TerminalApplicationShelvesDto;
import com.geek.workbench.entity.terminal.TerminalApplicationShelvesEntity;
import com.fosung.framework.common.support.service.AppBaseDataService;

public interface TerminalApplicationShelvesService extends AppBaseDataService<TerminalApplicationShelvesEntity, Long> {


    /**
     * 功能描述: 保存授权信息
     *
     * @param terminalApplicationShelves
     * @return
     * @author fuhao
     * @date 2021/10/15 16:41
     */
    void save(TerminalApplicationShelvesDto terminalApplicationShelves);

    Set<Long> getUserRangeIds(Long terminalId,String userId);

    Set<Long> getIdenRangeIds(Long terminalId,Set<String> identity);

    Set<Long> getOrgRangeIds(Long terminalId,Set<String> org);

    /**
     * 描述:  根据角色查询授权信息
     * @createDate: 2022/8/1 15:48
     * @author: gaojian
     * @modify:
     * @param terminalCode
     * @param roleId
     * @return: java.util.Set<java.lang.Long>
     */
    Set<Long> getRoleRangeIds(String terminalCode,String roleId);

    Set<Long> getAreaRangeIds(Long terminalId,String areaCode);

}

