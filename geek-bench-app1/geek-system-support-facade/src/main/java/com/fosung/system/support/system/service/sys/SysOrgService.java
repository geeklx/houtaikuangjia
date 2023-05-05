package com.fosung.system.support.system.service.sys;

import com.fosung.framework.common.support.service.AppBaseDataService;
import com.fosung.system.support.system.entity.sys.ReturnTreeData;
import com.fosung.system.support.system.entity.sys.SysOrgEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SysOrgService extends AppBaseDataService<SysOrgEntity, Long> {


    List<Map<String,Object>> getOutResource(String url, Map<String,Object> searchMaap);

    Set<Long> getOrgIds(Long orgId);

    Set<SysOrgEntity> getOrgsByParentId(Long orgId);

    Set<SysOrgEntity> getChildOrgsByParentId(Long orgId);

    SysOrgEntity getOrgByUserId(Long userId);

    List<ReturnTreeData> queryUserOrgTree(Map<String, Object> searchParams);


    List<ReturnTreeData> queryOrgTree(Long projectId,String orgType);

    List<SysOrgEntity> queryOrgsByParentId(Map<String,Object> searchParam);

    List<ReturnTreeData> queryOrgsByParentId(Long orgId);

    List<SysOrgEntity> getOrgByRoleIdAndUserId(Long userId,Long roleId,Long projectId,String orgType);

    List<SysOrgEntity> getPartyOrgByRoleIdAndUserId(Long userId,Long roleId,Long projectId);

    List<SysOrgEntity> getLazyOrgByRoleIdAndUserId(Long userId,Long roleId,Long projectId);

    List<SysOrgEntity> getPartyLazyOrgByRoleIdAndUserId(Long userId,Long roleId,Long projectId);

    List<ReturnTreeData> queryOrgAndUserTree(Long orgId);

    List<ReturnTreeData> queryOrgAndUserTreeLazy(String orgId,Long projectId,String orgType);

    List<ReturnTreeData> queryOrgTreeLazy(String orgId,Long projectId,String orgType);

    List<ReturnTreeData> queryAllOrgAndUserTree(Long projectId,String orgType);

    List<ReturnTreeData> queryReturnOrgTree(Long orgId);

    Page<SysOrgEntity> queryPage(Map<String, Object> searchParam, Pageable pageable);

    void deleteInfo(List<Long> collect);

    List<SysOrgEntity> getParentOrgs(Long parentId);

    List<SysOrgEntity> getParentOrgs(Long id,Long rootId);

    List<SysOrgEntity> getParentOrgs(Long id,String levelType);

    SysOrgEntity queryParentOrgById(Map<String,Object> searchParam);

    List<Map<String,Object>> queryScopOrgByUser(Map<String,Object> searchParam);

}

