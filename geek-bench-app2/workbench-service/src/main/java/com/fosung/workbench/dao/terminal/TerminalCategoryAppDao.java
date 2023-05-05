package com.fosung.workbench.dao.terminal;

import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.fosung.workbench.entity.terminal.TerminalApplicationConfigEntity;
import com.fosung.workbench.entity.terminal.TerminalCategoryAppEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface TerminalCategoryAppDao extends AppJPABaseDao<TerminalCategoryAppEntity, Long>{

    /**
     * 描述: 逻辑删除绑定应用信息
     * @param
     * @return void
     * @author fuhao
     * @date 2021/10/27 16:54
     **/
    @MybatisQuery
    void deleteByExample(@Param("params") Map<String, Object> delParam);
}