package com.geek.workbench.dao.terminal;

import com.fosung.framework.dao.config.mybatis.page.MybatisPage;
import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.geek.workbench.dto.terminal.TerminalBasicDto;
import com.geek.workbench.entity.terminal.TerminalBasicEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface TerminalBasicDao extends AppJPABaseDao<TerminalBasicEntity,Long> {

    /**
     * 描述: 终端信息分页查询
     * @param searchParam
     * @param pageable
     * @return com.fosung.framework.dao.config.mybatis.page.MybatisPage<com.fosung.workbench.entity.terminal.TerminalBasicEntity>
     * @author fuhao
     * @date 2021/10/26 14:44
     **/
    @MybatisQuery
    MybatisPage<TerminalBasicEntity> queryTerminalBasicInfoByPage(@Param("params") TerminalBasicDto terminalBasicDto, Pageable pageable);

    /**
     * 描述: 终端下拉框
     * @param searchParam
     * @return java.util.List<com.fosung.workbench.entity.terminal.TerminalBasicEntity>
     * @author fuhao
     * @date 2021/10/26 14:44
     **/
    @MybatisQuery
    List<TerminalBasicEntity> queryTerminalOptions(@Param("params") Map<String,Object> searchParam);

    /**
     * 描述: 根据终端id删除所有信息
     * @param delParam
     * @return void
     * @author fuhao
     * @date 2021/10/26 14:44
     **/
    @MybatisQuery
    void deleteAllByTerminal(@Param("params") Map<String,Object> delParam);

    /**
     * 描述:获取终端树结构
     * @param
     * @return java.util.List<java.util.Map>
     * @author fuhao
     * @date 2021/10/28 14:35
     **/
    @MybatisQuery
    List<TerminalBasicEntity> queryTerminalTree(@Param("params") TerminalBasicEntity terminalBasic);

    /**
     * 描述: 获取项目数结构
     * @param
     * @return java.util.List<com.fosung.workbench.entity.terminal.TerminalBasicEntity>
     * @author fuhao
     * @date 2021/10/28 16:44
     **/
    @MybatisQuery
    List<TerminalBasicEntity> queryProjectTree();
}
