package com.fosung.system.support.system.dao.sys;

import com.fosung.framework.dao.config.mybatis.page.MybatisPage;
import com.fosung.system.support.system.entity.sys.SysResourceEntity;
import com.fosung.system.support.system.vo.SysResourceMenuVo;
import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface SysResourceDao extends AppJPABaseDao<SysResourceEntity, Long>{

    @MybatisQuery
    List<SysResourceMenuVo> selectByUserIdAndAppCode(@Param("params")Map<String,Object> params);

    @MybatisQuery
    List<Long> selectAppByAppCodeAndRole(@Param("params")Map<String,Object> params);

    @MybatisQuery
    List<SysResourceMenuVo> selectUserResource(@Param("params")Map<String,Object> params);

    @MybatisQuery
    MybatisPage<SysResourceEntity> queryPage(@Param("params") Map<String,Object> params, Pageable pageable);

    @MybatisQuery
    SysResourceEntity queryById(@Param("params")Map<String,Object> params);
}