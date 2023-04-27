package com.geek.workbench.dao.terminal;

import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.geek.workbench.entity.terminal.TerminalConfigCommonEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 类的描述
 *
 * @author fuhao
 * @version V1.0
 * @date 2021/10/9 17:37
 */
public interface TerminalConfigCommonDao extends AppJPABaseDao<TerminalConfigCommonEntity,Long> {
    @MybatisQuery
    List<TerminalConfigCommonEntity> getAllCommonConfigurationInfo(@Param("terminalCommonConfigurationInfo") TerminalConfigCommonEntity TerminalConfigCommonEntity);

    @MybatisQuery
    void updateConfigValue(@Param("terminalCommonConfigurationInfo") TerminalConfigCommonEntity TerminalConfigCommonEntity);
}
