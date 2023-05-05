package com.fosung.workbench.dao.terminal;

import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.fosung.workbench.entity.microcoder.TerminalConfigNavigationBtmEntity;
import com.fosung.workbench.entity.terminal.TerminalNavigationTurnUrl;
import org.apache.ibatis.annotations.Param;

/**
 * 描述:  终端导航跳转路径持久层
 * @createDate: 2021/11/05 15:06
 * @author: gaojian
 * @version V1.0
 */
public interface TerminalNavigationTurnUrlDao extends AppJPABaseDao<TerminalNavigationTurnUrl, Long>{

    /**
     * 描述:  修改终端底部导航跳转路径
     * @createDate: 2021/11/20 10:48
     * @author: gaojian
     * @modify:
     * @param terminalConfigNavigationBtmEntity
     * @return: void
     */
    @MybatisQuery
    void updateNavigationBtmUrl(@Param("param") TerminalConfigNavigationBtmEntity terminalConfigNavigationBtmEntity);
}