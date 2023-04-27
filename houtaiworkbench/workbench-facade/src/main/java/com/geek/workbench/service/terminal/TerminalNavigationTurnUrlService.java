package com.geek.workbench.service.terminal;

import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.support.service.AppBaseDataService;
import com.geek.workbench.entity.terminal.TerminalNavigationTurnUrl;

import java.util.List;

/**
 * 描述:  终端导航跳转路径服务层
 * @createDate: 2021/11/05 15:06
 * @author: gaojian
 * @version V1.0
 */
public interface TerminalNavigationTurnUrlService extends AppBaseDataService<TerminalNavigationTurnUrl, Long> {


    /**
     * 描述:  保存导航跳转路径
     * @createDate: 2021/11/20 9:52
     * @author: gaojian
     * @modify:
     * @param terminalNavigationTurnUrl
     * @return: void
     */
    void saveInfo(TerminalNavigationTurnUrl terminalNavigationTurnUrl);

    /**
     * 描述:  修改导航跳转路径
     * @createDate: 2021/11/20 9:53
     * @author: gaojian
     * @modify:
     * @param terminalNavigationTurnUrl
     * @return: void
     */
    void updateInfo(TerminalNavigationTurnUrl terminalNavigationTurnUrl);

    /**
     * 描述:  删除信息
     * @createDate: 2021/11/20 9:53
     * @author: gaojian
     * @modify:
     * @param list
     * @return: void
     */
    void deleteInfo(List<AppBaseIdParam> list);
}

