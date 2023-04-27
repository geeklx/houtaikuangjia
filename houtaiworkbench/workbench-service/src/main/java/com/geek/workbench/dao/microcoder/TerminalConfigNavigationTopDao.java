package com.geek.workbench.dao.microcoder;

import com.fosung.framework.dao.config.mybatis.page.MybatisPage;
import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.geek.workbench.entity.microcoder.TerminalConfigNavigationTop;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @Description 顶部导航持久化接口
 * @Author gaojian
 * @Date 2021/11/04 10:25
 * @Version V1.0
 */
public interface TerminalConfigNavigationTopDao extends AppJPABaseDao<TerminalConfigNavigationTop, Long>{

    /**
     * 描述:  分页查询导航信息
     * @createDate: 2021/11/9 14:55
     * @author: gaojian
     * @modify:
     * @param terminalConfigNavigationTop
     * @return: com.fosung.framework.dao.config.mybatis.page.MybatisPage<java.util.Map>
     */
    @MybatisQuery
    List<Map<String,Object>> queryInfo(@Param("param") TerminalConfigNavigationTop terminalConfigNavigationTop);

    /**
     * 描述:  分页查询导航信息
     * @createDate: 2021/11/9 14:55
     * @author: gaojian
     * @modify:
     * @param terminalConfigNavigationTop
     * @param pageable
     * @return: com.fosung.framework.dao.config.mybatis.page.MybatisPage<java.util.Map>
     */
    @MybatisQuery
    MybatisPage<Map<String,Object>> queryInfoPage(@Param("param") TerminalConfigNavigationTop terminalConfigNavigationTop, Pageable pageable);

    /**
     * 描述:  分页查询导航信息
     * @createDate: 2021/11/9 14:55
     * @author: gaojian
     * @modify:
     * @param id
     * @return: com.fosung.framework.dao.config.mybatis.page.MybatisPage<java.util.Map>
     */
    @MybatisQuery
    void deleteAllTopMenu(@Param("id") Long id);

    /**
     * 描述:  修改所有菜单
     * @createDate: 2021/11/9 15:10
     * @author: gaojian
     * @modify:
     * @param terminalConfigNavigationTop
     * @return: void
     */
    @MybatisQuery
    void updateAllTopMenu(@Param("param") TerminalConfigNavigationTop terminalConfigNavigationTop);

    /**
     * 查询所有地域
     * @return
     */
    @MybatisQuery
    List<Map<String,Object>> queryAreaList();
}