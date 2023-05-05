package com.fosung.workbench.dao.sys;

import com.fosung.framework.dao.config.mybatis.page.MybatisPage;
import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.fosung.workbench.entity.sys.SysDictTypeEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface SysDictTypeDao extends AppJPABaseDao<SysDictTypeEntity, Long>{

    /**
     * 描述: 字典项分页查询
     * @param searchParam
     * @param pageable
     * @return org.springframework.data.domain.Page<com.fosung.system.support.system.entity.sys.SysDictTypeEntity>
     * @author fuhao
     * @date 2022/1/18 15:52
     **/
    @MybatisQuery
    MybatisPage<SysDictTypeEntity> queryPage(@Param("params") Map<String, Object> searchParam, Pageable pageable);

    /**
     * 根据类型查询字典
     * @param params
     * @return
     */
    @MybatisQuery
    List<Map<String,Object>> queryDictByType(@Param("params") Map<String, Object> params);

    /**
     * 批量插入
     * @param infos
     */
    @MybatisQuery
    void saveinfo(@Param("infos") List<SysDictTypeEntity> infos);
}