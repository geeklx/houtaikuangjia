package com.geek.workbench.dao.terminal;

import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.geek.workbench.entity.terminal.TerminalApplicationConfigEntity;
import com.geek.workbench.entity.terminal.TerminalConfigCategoryEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TerminalConfigCategoryDao extends AppJPABaseDao<TerminalConfigCategoryEntity, Long>{


    /**
     * 功能描述: 最新排序
     *
     * @param terminalConfigCategory
     * @return java.lang.Integer
     * @author fuhao
     * @date 2021/10/21 17:56
     */
    @MybatisQuery
    Integer getMaxNum(@Param("params") TerminalConfigCategoryEntity terminalConfigCategory);

    /**
     * 功能描述: 获取分类绑定应用的信息
     *
     * @param searchParam
     * @return TerminalApplicationConfigEntity
     * @author fuhao
     * @date 2021/10/26 9:08
     */
    @MybatisQuery
    List<TerminalApplicationConfigEntity> queryCategoryBindApp(@Param("params") Map<String, Object> searchParam);
}