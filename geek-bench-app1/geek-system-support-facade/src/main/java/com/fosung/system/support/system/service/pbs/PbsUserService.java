package com.fosung.system.support.system.service.pbs;



import com.fosung.system.support.system.entity.sys.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;


public interface PbsUserService  {




    /**
     * 分页查询组织下工作人员
     * @param sysUserDto
     * @param of
     * @return
     */
    Page<Map<String,Object>> queryWorkerByScopOrg(Map<String, Object> sysUserDto, Pageable of);

    /**
     * 查询工作人员
     * @param sysUserDto
     * @return
     */
    int queryWorkerCountByScopOrg(@Param("params") Map<String, Object> sysUserDto);

    int deleteByIds(Map<String, Object> param);

    Page<Map<String,Object>> queryPageManagerUsers(@Param("params") Map<String, Object> sysUserDto, Pageable of);

    /**
     * 批量保存用户
     * @param infos
     * @return
     */
    int saveinfo(List<SysUserEntity> infos);

    List<Map<String,Object>> queryUserRole(Map<String, Object> params);
}

