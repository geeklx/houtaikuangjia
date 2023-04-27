package com.geek.system.support.system.dao.sys;

import com.fosung.framework.dao.config.mybatis.page.MybatisPage;
import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.geek.system.support.system.entity.sys.SysPostEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface SysPostDao extends AppJPABaseDao<SysPostEntity, Long>{

    /**
     * 描述: 岗位分页查询
     * @param searchParam
     * @param pageable
     * @return org.springframework.data.domain.Page<com.geek.system.support.system.entity.sys.SysPostEntity>
     * @author fuhao
     * @date 2022/2/8 14:32
     **/
    @MybatisQuery
    MybatisPage<SysPostEntity> queryPage(@Param("params") Map<String,Object> searchParam, Pageable pageable);

}