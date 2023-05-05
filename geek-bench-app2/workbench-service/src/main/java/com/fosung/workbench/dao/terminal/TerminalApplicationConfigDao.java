package com.fosung.workbench.dao.terminal;

import com.fosung.framework.dao.config.mybatis.page.MybatisPage;
import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.fosung.workbench.dto.application.ApplicationQueryDto;
import com.fosung.workbench.dto.terminal.TerminalApplicationConfigDto;
import com.fosung.workbench.dto.terminal.TerminalApplicationQueryDto;
import com.fosung.workbench.entity.terminal.TerminalApplicationConfigEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface TerminalApplicationConfigDao extends AppJPABaseDao<TerminalApplicationConfigEntity,Long> {

    @MybatisQuery
    MybatisPage<TerminalApplicationConfigEntity> queryTerminalAppByPage(@Param("params") Map<String,Object> searchParam, Pageable pageable);

    /**
     * 描述:  根据终端包名和终端类型查询终端绑定应用信息
     * @createDate: 2021/12/1 14:33
     * @author: gaojian
     * @modify:
     * @param searchParam
     * @return: java.util.List<com.fosung.workbench.entity.terminal.TerminalApplicationConfigEntity>
     */
    @MybatisQuery
    List<TerminalApplicationConfigEntity> queryTerminalApp(@Param("params") Map<String, Object> searchParam);

    /**
     * 描述:  根据版本id修改应用状态
     * @createDate: 2021/10/22 14:43
     * @author: gaojian
     * @modify:
     * @param terminalAppConfigDto
     * @return: java.lang.Integer
     */
    @MybatisQuery
    Integer updateStatusByVersionId(@Param("params") TerminalApplicationConfigDto terminalAppConfigDto);

    /**
     * 描述:  查询应用信息信息
     * @createDate: 2021/10/21 16:49
     * @author: gaojian
     * @modify:
     * @param pageable
     * @param applicationQueryDto
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    @MybatisQuery
    MybatisPage<Map<String,Object>> queryApplication(@Param("params") ApplicationQueryDto applicationQueryDto, Pageable pageable);

    /**
     * 描述:  根据应用id删除应用信息
     * @createDate: 2021/11/6 17:37
     * @author: gaojian
     * @modify:
     * @param appId
     * @return: java.lang.Integer
     */
    @MybatisQuery
    Integer deleteByAppId(@Param("appId") Long appId);
}
