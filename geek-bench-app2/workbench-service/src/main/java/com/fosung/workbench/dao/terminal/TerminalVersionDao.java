package com.fosung.workbench.dao.terminal;

import com.fosung.framework.dao.config.mybatis.page.MybatisPage;
import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.fosung.workbench.entity.terminal.TerminalVersionEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface TerminalVersionDao extends AppJPABaseDao<TerminalVersionEntity,Long>{

    /**
     * 分页查询终端版本
     * @param searchParam
     * @param pageable
     * @return MybatisPage
     * @author fuhao
     * @date 2021/10/14 9:18
     */
    @MybatisQuery
    MybatisPage<TerminalVersionEntity> queryByPageTerminalVersion(@Param("params") Map<String, Object> searchParam, Pageable pageable);

    /**
     * 查询纯数字最新版本号
     * @param terminalVersion
     * @return Integer
     * @author fuhao
     * @date 2021/10/14 9:18
     */
    @MybatisQuery
    Integer getMaxVersionNum(@Param("params") TerminalVersionEntity terminalVersion);
}
