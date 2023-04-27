package com.geek.workbench.dao.config;

import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.geek.workbench.entity.config.ConfigManageEntity;
import com.geek.workbench.entity.config.TerminalConfigManageEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ConfigManageDao extends AppJPABaseDao<ConfigManageEntity, Long>{

    @MybatisQuery
    List<ConfigManageEntity> queryByTerminalId(@Param("terminalId") Long terminalId);

    /**
     * 描述:  修改终端运行配置信息
     * @createDate: 2021/11/12 11:29
     * @author: gaojian
     * @modify:
     * @param terminalConfigManageEntity
     * @return: void
     */
    @MybatisQuery
    void updateTerminalRunConfig(@Param("param") TerminalConfigManageEntity terminalConfigManageEntity);

    /**
     * 描述:  删除终端运行配置信息
     * @createDate: 2021/11/12 11:29
     * @author: gaojian
     * @modify:
     * @param terminalConfigManageEntity
     * @return: void
     */
    @MybatisQuery
    void deleteTerminalRunConfig(@Param("param") TerminalConfigManageEntity terminalConfigManageEntity);

}