package com.geek.system.support.system.service.pbs;


import com.geek.system.support.system.entity.sys.SysOrgEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


public interface PbsOrgService {

    /**
     * 查询组织
     * @param param
     * @return
     */
    List<Map<String,Object>> queryOrgBylevel(Map<String, Object> param);

    /**
     * 查询组织总数
     * @param param
     * @return
     */
    int queryOrgCountBylevel( Map<String, Object> param);


    Page<SysOrgEntity> queryByPage(Map<String, Object> searchParam, Pageable pageable);

    /**
     * 批量删除
     * @param param
     * @return
     */
    int deleteByIds(Map<String, Object> param);

    /**
     * 批量保存
     * @param infos
     * @return
     */
    int saveinfo(@Param("infos") List<SysOrgEntity> infos);
}

