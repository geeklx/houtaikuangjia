package com.fosung.system.support.system.service.sys;



import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.support.service.AppBaseDataService;
import com.fosung.system.support.system.dto.sys.SysResourceDto;
import com.fosung.system.support.system.entity.sys.ReturnTreeData;
import com.fosung.system.support.system.entity.sys.SysResourceEntity;
import com.fosung.system.support.system.vo.SysResourceMenuVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SysResourceService extends AppBaseDataService<SysResourceEntity, Long> {

    List<SysResourceEntity> queryResourceByRoleId(Set<Long> roleId, SysResourceDto sysResourceDto);

    List<SysResourceEntity> queryResourceByRoleId(Long roleId,SysResourceDto sysResourceDto);

    List<SysResourceEntity> queryResourceByUserId(Long userId,SysResourceDto sysResourceDto);

    List<SysResourceMenuVo> selectByUserIdAndAppCode(Map<String,Object> params);

    /**
     *
     * 根据角色id应用编码查询资源树
     * @param params
     * @author liuke
     * @date 2022/6/28 14:02
     * @return java.util.List<com.fosung.system.support.system.vo.SysResourceMenuVo>
     */
    List<SysResourceMenuVo> selectByAppCodeAndRole(Map<String,Object> params);

    List<Long> selectAppByAppCodeAndRole(Map<String,Object> params);

    Page<SysResourceEntity> queryPage(Map<String, Object> searchParam, Pageable pageable);

    /**
     * 描述: 资源级联删除
     * @param
     * @return void
     * @author fuhao
     * @date 2021/11/29 10:43
     **/
    void deleteInfo(List<AppBaseIdParam> list);

    /**
     *查询应用下全部资源
     *
     * @param appId
     * @author liuke
     * @date 2021/12/3 14:29
     * @return java.util.List<com.fosung.system.support.system.entity.sys.SysResourceEntity>
     */
    List<SysResourceEntity> getResoueceByAppId(Long appId);

    List<ReturnTreeData> getAppResourceTree(Long projectId);

    List<ReturnTreeData> getAppResourceCheckHas(Long projectId,Long userId,Long roleId);

    List<ReturnTreeData> getAppResourceCheckHasAndNoRole(Long projectId,Long userId,Long roleId);

    List<ReturnTreeData> getRoleResource(Long projectId,Long appId,Long roleId);
}

