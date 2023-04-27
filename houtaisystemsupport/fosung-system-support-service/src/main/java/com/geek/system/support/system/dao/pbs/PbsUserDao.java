package com.geek.system.support.system.dao.pbs;


import com.fosung.framework.dao.config.mybatis.page.MybatisPage;
import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.geek.system.support.system.entity.sys.SysUserEntity;
import org.apache.ibatis.annotations.Param;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface PbsUserDao extends AppJPABaseDao<SysUserEntity, Long> {


    /**
     * 查询组织下工作人员
     * @param sysUserDto
     * @param of
     * @return
     */
    @MybatisQuery
    MybatisPage<Map<String,Object>> queryWorkerByScopOrg(@Param("params") Map<String, Object> sysUserDto, Pageable of);

    /**
     * 查询组织想工作人员总数
     * @param sysUserDto
     * @return
     */
    @MybatisQuery
    int queryWorkerCountByScopOrg(@Param("params") Map<String, Object> sysUserDto);

    /**
     * 根据id批量删除
     * @param param
     * @return
     */
    @MybatisQuery
    int deleteByIds(@Param("param") Map<String, Object> param);

    /**
     * 分页查询管理员
     * @param sysUserDto
     * @param of
     * @return
     */
    @MybatisQuery
    MybatisPage<Map<String,Object>> queryPageManagerUsers(@Param("params") Map<String, Object> sysUserDto, Pageable of);

    /**
     * 批量保存用户
     * @param infos
     * @return
     */
    @MybatisQuery
    int saveinfo(@Param("infos") List<SysUserEntity> infos);

    /**
     *查询角色
     *
     * @param params
     * @author liuke
     * @date 2022/5/26 11:44
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @MybatisQuery
    List<Map<String,Object>> queryUserRole(@Param("params") Map<String, Object> params);


}