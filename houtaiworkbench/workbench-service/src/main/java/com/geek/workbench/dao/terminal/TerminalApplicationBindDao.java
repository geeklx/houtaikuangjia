package com.geek.workbench.dao.terminal;

import com.fosung.framework.dao.config.mybatis.page.MybatisPage;
import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.geek.workbench.entity.terminal.TerminalApplicationBindEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.Map;


public interface TerminalApplicationBindDao extends AppJPABaseDao<TerminalApplicationBindEntity, Long> {

    /**
     * 查询终端应用版本信息分页
     * @param searchParam
     * @return
     * @author fuhao
     * @date 2021/10/15 10:16
     */
    @MybatisQuery
    MybatisPage<TerminalApplicationBindEntity> queryTerminalAppVersion(@Param("params") Map<String,Object> searchParam, Pageable pageable);

    /**
     * 发布下线 终端应用版本功能
     * @param params
     * @author fuhao
     * @date 2021/10/15 10:16
     */
    @MybatisQuery
    void updateBind(@Param("params") Map<String,Object> params);

    /**
     * 发布下线 终端应用功能
     * @param searchParam
     * @author fuhao
     * @date 2021/10/15 10:16
     */
    @MybatisQuery
    void updateTerminalAppConfig(@Param("params") Map<String, Object> searchParam);

    /**
     * 描述:  根据应用id删除全部被绑定的信息
     * @createDate: 2021/10/21 14:35
     * @author: gaojian
     * @modify:
     * @param appId
     * @return: void
     */
    @MybatisQuery
    void deleteAllByAppId(@Param("appId") Long appId);

    /**
     * 描述:  根据应用版本id删除应用绑定信息
     * @createDate: 2021/10/22 14:33
     * @author: gaojian
     * @modify:
     * @param appVersionId
     * @return: void
     */
    @MybatisQuery
    void deleteAllByAppVersionId(@Param("appVersionId") Long appVersionId);
}

