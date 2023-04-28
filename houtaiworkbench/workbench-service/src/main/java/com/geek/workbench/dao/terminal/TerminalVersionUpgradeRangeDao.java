package com.geek.workbench.dao.terminal;

import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.geek.workbench.entity.terminal.TerminalVersionUpgradeRangeEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 描述:  终端版本更新范围持久层服务
 * @createDate: 2022/2/23 17:18
 * @author: gaojian
 */
public interface TerminalVersionUpgradeRangeDao extends AppJPABaseDao<TerminalVersionUpgradeRangeEntity, Long>{

    /**
     * 描述:  删除版本历史范围变更记录
     * @createDate: 2022/2/23 17:18
     * @author: gaojian
     * @modify:
     * @param id
     * @return: java.lang.Integer
     */
    @MybatisQuery
    Integer deleteHistoryLog(@Param("id") Long id);

    /**
     * 描述:  查询真实的全部
     *
     * @param searchParams
     * @createDate: 2022/2/23 18:05
     * @author: gaojian
     * @modify:
     * @return: java.util.List<com.geek.workbench.entity.terminal.TerminalVersionUpgradeRangeEntity>
     */
    @MybatisQuery
    List<TerminalVersionUpgradeRangeEntity> queryRealAll(@Param("params") Map<String, Object> searchParams);

}