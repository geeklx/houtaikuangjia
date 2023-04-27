package com.geek.system.support.system.dao.sys;

import com.fosung.framework.dao.config.mybatis.page.MybatisPage;
import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.geek.system.support.system.dto.out.CloudAppDto;
import com.geek.system.support.system.entity.sys.*;
import com.geek.system.support.system.entity.sys.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SysUserDao extends AppJPABaseDao<SysUserEntity, Long>{

    @MybatisQuery
    List<SysRoleScopEntity> queryAllSysRoleScop(@Param("params") HashMap<String, Object> searchParam);

    @MybatisQuery
    List<SysUserResourceEntity> queryBindResource(@Param("params") Map<String, Object> searchParam);

    @MybatisQuery
    List<SysUserResourceEntity> queryBindApp(@Param("params") Map<String, Object> searchParam);

    @MybatisQuery
    List<String> queryUserBindRole(@Param("params") Map<String, Object> searchParam);

    @MybatisQuery
    SysUserEntity queryUserByParams(@Param("params") Map<String, Object> searchParam);

    @MybatisQuery
    List<SysUserEntity> queryMoreUserByParams(@Param("params") Map<String, Object> searchParam);

    @MybatisQuery
    List<SysUserEntity> queryUserByRoleCode(@Param("params") Map<String, Object> searchParam);

    @MybatisQuery
    List<SysCityEntity> getCityProvinceInfo();

    @MybatisQuery
    void deleteAllShelves(@Param("params") Map<String, Object> delMap);

    @MybatisQuery
    List<SysUserPostScopeEntity> queryUserBindPost(@Param("userId") Long id);

    @MybatisQuery
    List<SysCityEntity> getCityInfoByProvince(@Param("province") String province);

    @MybatisQuery
    List<SysCityEntity> getCityInfoByCode(@Param("code") String code);

    @MybatisQuery
    List<SysUserEntity> queryAllUserInfo(@Param("params") Map<String, Object> searchParam);

    @MybatisQuery
    MybatisPage<SysUserEntity> queryUserByOrgAndPost(@Param("params") Map<String, Object> sysUserDto, PageRequest of);

    @MybatisQuery
    List<SysUserEntity> queryUserByPost(@Param("params") HashMap<String, Object> searchParams);

    @MybatisQuery
    Set<String> queryParentOrdUserByOrgAndRole(@Param("searchParam") Map<String, Object> searchParams);

    @MybatisQuery
    Set<String> queryParentOrdUserByOrgIdAndAppId(@Param("param") Map<String, Object> searchParams);

    @MybatisQuery
    Set<Map<String,Object>> queryParentOrdUserMapByOrgIdAndAppId(@Param("param") Map<String, Object> searchParams);

    @MybatisQuery
    Set<Map<String,Object>> queryUsersByOrgIdsAndAppIds(@Param("params")Map<String, Object> searchParams);

    @MybatisQuery
    List<CloudAppDto> queryUserAppList(@Param("userId") Long userId);

    @MybatisQuery
    List<SysUserEntity> queryAllUserByRoleCode(@Param("params")Map<String, Object> searchParams);

    @MybatisQuery
    List<Map<String,Object>> queryManagerUsers(@Param("params")Map<String, Object> searchParams);

    @MybatisQuery
    List<String> querySameUserName(@Param("ids") List<Long> ids);

}