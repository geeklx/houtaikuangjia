package com.geek.workbench.service.terminal;

import com.geek.workbench.dto.terminal.TerminalApplicationBindDto;
import com.geek.workbench.entity.terminal.TerminalApplicationBindEntity;
import org.springframework.data.domain.Page;
import com.fosung.framework.common.support.service.AppBaseDataService;

public interface TerminalApplicationBindService extends AppBaseDataService<TerminalApplicationBindEntity, Long> {

    /**
     * 查询终端应用版本信息分页
     * @param terminalApplicationBindDto
     * @return
     * @author fuhao
     * @date 2021/10/15 10:16
     */
    Page<TerminalApplicationBindEntity> queryTerminalAppVersion(TerminalApplicationBindDto terminalApplicationBindDto);

    /**
     * 终端应用版本发布下线功能
     * @param terminalApplicationBindDto
     * @author fuhao
     * @date 2021/10/15 10:16
     */
    void updateStatus(TerminalApplicationBindDto terminalApplicationBindDto);

    /**
     * 描述:  根据应用id删除绑定信息
     * @createDate: 2021/10/21 14:34
     * @author: gaojian
     * @modify:
     * @param appId
     * @return: void
     */
    void deleteBindByAppId(Long appId);

    /**
     * 描述:  根据版本id删除终端绑定的版本
     * @createDate: 2021/10/21 14:34
     * @author: gaojian
     * @modify:
     * @param versionId
     * @return: void
     */
    void deleteBindByVersionId(Long versionId);
}

