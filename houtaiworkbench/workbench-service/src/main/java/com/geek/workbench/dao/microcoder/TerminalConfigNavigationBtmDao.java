package com.geek.workbench.dao.microcoder;

import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.geek.workbench.entity.microcoder.TerminalConfigNavigationBtmEntity;
import org.apache.ibatis.annotations.Param;

public interface TerminalConfigNavigationBtmDao extends AppJPABaseDao<TerminalConfigNavigationBtmEntity,Long> {

    /**
     * 描述: 根据id修改导航信息
     * @param navigationConfigurationInfo
     * @return int
     * @author fuhao
     * @date 2021/11/4 14:24
     **/
    @MybatisQuery
    int updateById(@Param("navigationConfigurationInfo")TerminalConfigNavigationBtmEntity navigationConfigurationInfo);

    /**
     * 描述: 查询终端下最大的排序
     * @param terminalConfigNavigationBtmEntity
     * @return java.lang.Integer
     * @author fuhao
     * @date 2021/11/4 14:22
     **/
    @MybatisQuery
    Integer queryMaxNum(@Param("params") TerminalConfigNavigationBtmEntity terminalConfigNavigationBtmEntity);

    /**
     * 描述: 级联删除底部导航信息
     * @param
     * @return void
     * @author fuhao
     * @date 2021/11/9 16:03
     **/
    @MybatisQuery
    void deleteByExample();
}
