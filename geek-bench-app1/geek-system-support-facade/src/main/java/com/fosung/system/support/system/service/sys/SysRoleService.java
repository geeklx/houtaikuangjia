package com.fosung.system.support.system.service.sys;

import com.fosung.framework.common.support.service.AppBaseDataService;
import com.fosung.system.support.system.dto.sys.SysRoleDto;
import com.fosung.system.support.system.dto.sys.SysRoleResourceDto;
import com.fosung.system.support.system.entity.sys.*;
import com.fosung.system.support.system.vo.SysRoleAndResourceVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface SysRoleService extends AppBaseDataService<SysRoleEntity, Long> {

    List<SysRoleEntity> queryRoleListByUserId(Long userId);

//    void bindResource(Long roleId, Set<Long> resourceIds );
//
//    void rebindResource(Long roleId,Set<Long> resourceIds);

    /**
     * 描述: 重新绑定角色
     * @param sysRoleResourceDto
     * @return void
     * @author fuhao
     * @date 2021/11/29 16:50
     **/
//    void rebindResource(SysRoleResourceDto sysRoleResourceDto);

    /**
     * 描述: 取消角色已绑定的资源
     * @param sysRoleResourceDto
     * @return void
     * @author fuhao
     * @date 2022/2/16 18:17
     **/
    void delBindResource(SysRoleResourceDto sysRoleResourceDto);

    /**
     * 描述: 角色绑定资源
     * @param sysRoleResourceDto
     * @return void
     * @author fuhao
     * @date 2021/11/29 16:50
     **/
    void bindResource(SysRoleResourceEntity sysRoleResourceDto );

    void rebindUser(Long roleId,Set<Long> userIds);

    SysRoleEntity getRoleByCode(String roleCode);

    SysRoleEntity getRoleById(String roleId);

    List<SysRoleAndResourceVo> getRoleAndResourceByUser(Long userId);

    /**
     * 描述: 分页查询
     * @param searchParam
     * @param pageable
     * @return org.springframework.data.domain.Page<com.fosung.system.support.system.entity.sys.SysRoleEntity>
     * @author fuhao
     * @date 2021/11/27 10:07
     **/
    Page<SysRoleEntity> queryPage(Map<String, Object> searchParam, Pageable pageable);

    /**
     * 描述: 项目下查询全部的角色
     * @param sysRoleDto
     * @return List
     * @author fuhao
     * @date 2021/11/27 10:05
     **/
    List<SysRoleEntity> queryAllRole(SysRoleDto sysRoleDto);

    /**
     * 描述: 绑定角色管理范围
     * @param sysRoleScop
     * @return void
     * @author fuhao
     * @date 2021/11/27 10:58
     **/
    void saveInfo(SysRoleDto sysRoleScop);

    /**
     * 描述: 获取角色权限信息
     * @param sysRoleResourceDto
     * @return List
     * @author fuhao
     * @date 2021/11/29 15:38
     **/
    List<SysProjectApp> queryRolePower(SysRoleResourceDto sysRoleResourceDto);

    /**
     * 描述: 获取角色绑定应用
     * @param sysRoleResourceDto
     * @return java.util.List<com.fosung.system.support.system.entity.sys.SysRoleAppEntity>
     * @author fuhao
     * @date 2021/11/29 15:59
     **/
    List<SysRoleAppEntity> queryRoleBindApp(SysRoleResourceDto sysRoleResourceDto);

    /**
     * 查询应用下角色id
     * @param appCode
     * @param projectId
     * @return
     */
    List<SysRoleAppEntity> queryRoleByApp(String appCode,Long projectId);

    /**
     * 描述: 获取角色绑定资源
     * @param sysRoleResourceDto
     * @return java.util.List<com.fosung.system.support.system.entity.sys.SysResourceEntity>
     * @author fuhao
     * @date 2021/11/29 16:30
     **/
    List<SysResourceEntity> queryRoleBindResource(SysRoleResourceDto sysRoleResourceDto);

    /**
     * 描述: 获取角色下绑定的资源
     * @param sysRoleResourceDto
     * @return java.util.List<com.fosung.system.support.system.entity.sys.SysRoleResourceEntity>
     * @author fuhao
     * @date 2021/12/20 11:36
     **/
    List<SysRoleResourceEntity>  queryBindRolePower(SysRoleResourceDto sysRoleResourceDto);

    /**
     *
     * 懒加载查询灯塔党组织
     * @param parentId 
     * @author liuke
     * @date 2022/2/9 14:37
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String,Object>> queryDtRoleList(String parentId);

    /**
     * 描述: 角色重新绑定应用
     * @param sysRoleResourceDto
     * @return void
     * @author fuhao
     * @date 2022/2/16 15:17
     **/
    void rebindApp(SysRoleResourceDto sysRoleResourceDto);

    /**
     * 描述: 取消角色已绑定的应用
     * @param sysRoleResourceDto
     * @return void
     * @author fuhao
     * @date 2022/2/16 18:19
     **/
    void delBindApp(SysRoleResourceDto sysRoleResourceDto);

    /**
     * 描述: 角色绑定应用
     * @param sysRoleResourceDto
     * @return void
     * @author fuhao
     * @date 2022/2/16 18:23
     **/
    void bindApp(SysRoleResourceEntity sysRoleResourceDto);

    List<Map<String,Object>> queryRoleByAppIds( Long projectId, List<Long> ids);

    void deleteByRoleId(Long roleId,String tableName);

    /**
     * 检验是否授权了用户权限中心角色
     * @param roleIds
     * @return
     */
    boolean checkHasSysUser(List<Long> roleIds);
}

