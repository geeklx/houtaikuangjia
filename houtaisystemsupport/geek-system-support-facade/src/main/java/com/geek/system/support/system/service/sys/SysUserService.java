package com.geek.system.support.system.service.sys;


import com.fosung.framework.common.exception.AppException;
import com.fosung.framework.common.support.service.AppBaseDataService;
import com.fosung.framework.web.mvc.config.session.common.AppUserInfo;
import com.geek.system.support.system.dto.out.CloudAppDto;
import com.geek.system.support.system.dto.sys.ShelvesUserRoleDto;
import com.geek.system.support.system.dto.sys.SysUserDto;
import com.geek.system.support.system.dto.sys.SysUserRoleScopDto;
import com.geek.system.support.system.entity.sys.*;
import com.geek.system.support.system.vo.SysUserDetailVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;


public interface SysUserService extends AppBaseDataService<SysUserEntity, Long> {


    void bindRole(Long userId, Set<Long> roleIds, Long projectId);

    void rebindRole(Long userId, Set<Long> roleIds, Long projectId);

    SysUserDetailVo getUserDetails(Long userId);

    SysUserDetailVo getAuthUserDetails(Map<String, Object> searchParam);

    SysUserDetailVo getMoreAuthUserDetails(Map<String, Object> searchParam);

    List<SysUserEntity> getSysUserByOrgId(Long orgId);

    List<ReturnTreeData> getCuurentUserByOrgId(Long orgId);

    Long getSysUserCountByOrgId(Long orgId);

    String encodPassword(String passowrd);

    SysUserEntity activeByPhone(SysUserDto sysUserDto);

    String verify(String realName, String idCard);

    boolean resetPassword(SysUserDto sysUserDto);

    boolean manageResetPassword(SysUserDto sysUserDto);

    SysUserEntity updatePhone(String phone, Long userId, String code);

    List<ReturnTreeData> queryUser(Map<String, Object> searchParams);

    /**
     * 描述: 保存用户信息
     *
     * @param sysUser
     * @return void
     * @author fuhao
     * @date 2021/12/1 9:43
     **/
    void saveInfo(SysUserEntity sysUser) throws Exception;

    /**
     * 描述: 查询管理角色
     *
     * @param sysRoleDto
     * @return List<SysRoleScopEntity>
     * @author fuhao
     * @date 2021/12/2 16:26
     **/
    List<Map<String, Object>> queryUserRoleScope(SysUserRoleScopDto sysRoleDto);

    /**
     * 描述: 只查询管理角色
     *
     * @param sysRoleDto
     * @return List<SysRoleScopEntity>
     * @author fuhao
     * @date 2021/12/2 16:26
     **/
    List<Map<String, Object>> queryOnlyUserRoleScope(SysUserRoleScopDto sysRoleDto);

    /**
     * 描述: 查询资源授权绑定角色
     *
     * @param sysRoleDto
     * @return com.geek.system.support.system.entity.sys.SysRoleScopEntity
     * @author fuhao
     * @date 2021/12/20 9:42
     **/
    SysRoleScopEntity queryUserResourceScope(SysUserRoleScopDto sysRoleDto);

    /**
     * 描述: 查询授权管理范围
     *
     * @param sysUserRoleScopDto
     * @return List<SysOrgEntity>
     * @author fuhao
     * @date 2021/12/4 13:49
     **/
    List<SysOrgEntity> managerScope(SysUserRoleScopDto sysUserRoleScopDto);

    /**
     * 描述: 绑定用户管理范围
     *
     * @param sysUserRoleScopDto
     * @return void
     * @author fuhao
     * @date 2021/12/4 15:41
     **/
    void bindManagerScope(SysUserRoleScopDto sysUserRoleScopDto);


    /**
     * 查询会话共享用户信息
     *
     * @param idCardHash
     * @return com.geek.framework.web.mvc.config.session.common.AppUserInfo
     * @author liuke
     * @date 2021/12/15 10:16
     */
    AppUserInfo getUserInfo(String idCardHash, String clientId);

    /**
     * 描述: 查询用户绑定的角色
     *
     * @param userId
     * @return java.util.List<java.lang.String>
     * @author fuhao
     * @date 2021/12/16 14:08
     **/
    List<String> queryUserBindRole(Long userId);

    /**
     * 根据角色编码查询用户
     *
     * @param searchParam
     * @return java.util.List<com.geek.system.support.system.entity.sys.SysUserEntity>
     * @author liuke
     * @date 2022/1/13 11:38
     */
    List<SysUserEntity> queryUserByRoleCode(Map<String, Object> searchParam);

    /**
     * 描述: 获取城市下拉框
     *
     * @param
     * @return java.util.List<com.geek.system.support.system.entity.sys.SysCityEntity>
     * @author fuhao
     * @date 2022/1/14 11:16
     **/
    List<SysCityEntity> getCityProvinceInfo();

    /**
     * 描述: 通过省查市
     *
     * @param province
     * @return java.util.List<com.fosung.system.support.system.entity.sys.SysCityEntity>
     * @author fuhao
     * @date 2022/2/10 15:40
     **/
    List<SysCityEntity> getCityInfoByProvince(String province);

    /**
     * 描述: 通过市查区县
     *
     * @param code
     * @return java.util.List<com.fosung.system.support.system.entity.sys.SysCityEntity>
     * @author fuhao
     * @date 2022/2/10 15:44
     **/
    List<SysCityEntity> getCityInfoByCode(String code);

    /**
     * 描述: 批量授权
     *
     * @param projectId
     * @param file
     * @return com.fosung.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2022/1/17 14:23
     **/
    HashSet batchSaveShelves(Long projectId, MultipartFile file) throws Exception;

    /**
     * 批量授权
     *
     * @author liuke
     * @date 2022/6/9 9:12
     * @version
     */
    void batchShelves(ShelvesUserRoleDto shelvesUserRoleDto);


    /**
     * 批量保存用户
     *
     * @param file
     * @return
     */
    HashSet batchSaveUsers(MultipartFile file) throws Exception;

    /**
     * 描述: 用户授权
     *
     * @param sysUserRoleScopDto
     * @return void
     * @author fuhao
     * @date 2022/1/17 14:22
     **/
    void saveShelves(SysUserRoleScopDto sysUserRoleScopDto) throws AppException;

    /**
     * 描述: 批量撤销授权
     *
     * @param sysUserRoleScopDto
     * @return void
     * @author fuhao
     * @date 2022/1/17 15:38
     **/
    void batchDelShelves(List<SysUserRoleScopDto> sysUserRoleScopDto);

    /**
     * 描述: 查询用户绑定的岗位
     *
     * @param id
     * @return java.util.List<java.lang.String>
     * @author fuhao
     * @date 2022/2/9 16:10
     **/
    List<SysUserPostScopeEntity> queryUserBindPost(Long id);

    /**
     * 校验灯塔用户
     *
     * @param id
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author liuke
     * @date 2022/2/8 14:16
     */
    Map<String, Object> checkDtUser(String id);

    /**
     * 描述:
     *
     * @param searchParam
     * @return java.util.List<com.fosung.system.support.system.entity.sys.SysUserEntity>
     * @author fuhao
     * @date 2022/2/17 11:30
     **/
    List<SysUserEntity> queryAllUserInfo(Map<String, Object> searchParam);

    /**
     * 描述: 根据组织和职务分页查询人员
     *
     * @param sysUserDto
     * @param of
     * @return org.springframework.data.domain.Page<com.fosung.system.support.system.entity.sys.SysUserEntity>
     * @author fuhao
     * @date 2022/2/17 17:27
     **/
    Page<SysUserEntity> queryUserByOrgAndPost(Map<String, Object> sysUserDto, PageRequest of);

    /**
     * 描述: 查询组织下任职人员
     *
     * @param searchParams
     * @return java.util.List<com.fosung.system.support.system.entity.sys.SysUserEntity>
     * @author fuhao
     * @date 2022/2/17 18:46
     **/
    List<SysUserEntity> queryUserByPost(HashMap<String, Object> searchParams);

    /**
     * 根据角色code、组织code，获取上级组织授权的用户id，上级组织不存在一直往上查
     *
     * @author liuke
     * @date 2022/2/22 13:51
     * @version
     */
    Set<String> queryParentOrdUserByOrgAndRole(String orgCode, String roleCode);

    /**
     * 13、查询当前组织和应用下的管理员
     *
     * @author liuke
     * @date 2022/2/22 13:51
     * @version
     */
    Set<String> queryParentOrdUserByOrgIdAndAppId(Map<String, Object> searchParams);

    /**
     * 13、查询当前组织和应用下的管理员
     *
     * @author liuke
     * @date 2022/2/22 13:51
     * @version
     */
    Set<Map<String, Object>> queryParentOrdUserMapByOrgIdAndAppId(Map<String, Object> searchParams);


    /**
     * 查询当组织和应用下的管理员
     *
     * @param searchParams
     * @return java.util.Set<java.util.Map < java.lang.String, java.lang.Object>>
     * @author liuke
     * @date 2022/2/22 15:05
     */
    Set<Map<String, Object>> queryUsersByOrgIdsAndAppIds(Map<String, Object> searchParams);

    /**
     * 描述: 查询用户已经授权的应用
     *
     * @param userId
     * @return java.util.List<com.fosung.system.support.system.entity.sys.SysUserResourceEntity>
     * @author fuhao
     * @date 2022/2/22 9:20
     **/
    List<CloudAppDto> queryUserAppList(Long userId);

    /**
     * 根据角色编码和用户名查询
     *
     * @param searchParams
     * @return
     */
    List<SysUserEntity> queryAllUserByRoleCode(Map<String, Object> searchParams);

    /**
     * 查询管理组织的人
     *
     * @param searchParams
     * @return
     */
    List<Map<String, Object>> queryManagerUsers(Map<String, Object> searchParams);

    /**
     * 校验是否含有重复用户名
     *
     * @param userIds
     * @return
     */
    boolean checkSameSysUser(List<Long> userIds);

}

