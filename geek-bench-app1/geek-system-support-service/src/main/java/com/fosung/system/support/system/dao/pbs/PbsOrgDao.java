package com.fosung.system.support.system.dao.pbs;


import com.fosung.framework.dao.config.mybatis.page.MybatisPage;
import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.fosung.system.support.system.entity.sys.SysOrgEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface PbsOrgDao extends AppJPABaseDao<SysOrgEntity, Long> {

    /**
     * 查询组织
     * @param param
     * @return
     */
    @MybatisQuery
    List<Map<String,Object>> queryOrgBylevel(@Param("param") Map<String, Object> param);

    /**
     * 查询组织总数
     * @param param
     * @return
     */
    @MybatisQuery
    int queryOrgCountBylevel(@Param("param") Map<String, Object> param);

    /**
     *分页查询
     *
     * @param searchParam
     * @param pageable
     * @author liuke
     * @date 2022/4/7 14:41
     * @return com.fosung.framework.dao.config.mybatis.page.MybatisPage<com.fosung.system.support.system.entity.sys.SysOrgEntity>
     */
    @MybatisQuery
    MybatisPage<SysOrgEntity> queryByPage(@Param("searchParam") Map<String,Object> searchParam, Pageable pageable);

    @MybatisQuery
    int deleteByIds(@Param("param") Map<String, Object> param);

    @MybatisQuery
    int saveinfo(@Param("infos") List<SysOrgEntity> infos);

}