package com.fosung.system.pbs.api;


import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.framework.web.mvc.config.session.common.AppUserInfo;
import com.fosung.system.pbs.dto.entity.AppSysUser;
import com.fosung.system.pbs.dto.entity.SysAppUserRole;
import com.fosung.system.pbs.dto.resp.SysUserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@FeignClient(name = "SystemUserApi", url = "${app.pbs.client.url}")
@RequestMapping(value = "/api/system/user")
public interface SystemUserApi {


    /**
     * 根据用户id查询用户信息
     *
     * @param userid
     * @return
     */
    @PostMapping("query/by/userid")
    ResponseParam queryByUserId(@RequestParam("userid") String userid) ;

    /**
     * 根据用户hash查询用户信息
     *
     * @param hash
     * @retur*/
    @PostMapping("query/by/hash")
    ResponseParam queryByHash(@RequestParam("hash") String hash,@RequestParam(value = "projectCode",required = false,defaultValue = "") String projectCode) ;

    /**
     * 根据身份证号查询用户
     *
     * @param identifyId
     * @return
     */
    @PostMapping("query/by/identifyid")
    ResponseParam queryByIdentifyId(@RequestParam("identifyId") String identifyId,@RequestParam(value = "projectCode",required = false,defaultValue = "") String projectCode);

    /**
     * 根据用户名获取用户信息
     * @param userName
     * @return
     */
    @PostMapping("query/by/username")
    ResponseParam queryByUserName(@RequestParam(value = "userName") String userName,@RequestParam(value = "projectCode",required = false,defaultValue = "") String projectCode);

    /**
     * 根据手机号获取用户信息
     * @param telephone
     * @return
     */
    @PostMapping("query/by/telephone")
    ResponseParam queryByTelephone(@RequestParam("telephone") String telephone,@RequestParam(value = "projectCode",required = false,defaultValue = "") String projectCode);

    /**
     * 根据微信编码获取用户信息
     * @param wxcode
     * @return
     */
    @PostMapping("query/by/wxcode")
    ResponseParam queryByWxCode(@RequestParam("wxcode") String wxcode,@RequestParam(value = "projectCode",required = false,defaultValue = "") String projectCode);

    /**
     * 查询组织机构下的用户
     *
     * @param orgId
     * @return
     */
    @PostMapping("query/org/users")
    ResponseParam queryOrgUsers(@RequestParam("orgId") String orgId);

    /**
     * 根据姓名和身份证号查询认证用户信息
     *
     * @param realName
     * @param idCard
     * @return
     */
    @RequestMapping("query/by/phone/and/idcard")
    ResponseParam queryByPhoneAndIDCard(@RequestParam("realName") String realName, @RequestParam("idCard") String idCard) ;

    /**
     * 根据组织批量查询本级及直属子级的所有用户
     *
     * @param orgId  组织ID
     * @return
     */
    @RequestMapping("query/user/by/ord")
    ResponseParam queryUserByOrd(@RequestParam("orgId") String orgId);


//    /**
//     * 分页查询组织下任职和所属的人员
//     * @param user
//     * @return
//     */
//    @RequestMapping("query/page/user/by/org")
//    ResponseParam queryPageUserByOrg( @RequestBody SysUserDto user);

    /**
     * 批量查询用户
     *
     * @param ids
     * @return
     */
    @RequestMapping("query/by/userids")
    ResponseParam queryByUserIds(@RequestBody ArrayList<String> ids);

    /**
     * 查询组织机构下的用户数量
     *
     * @return
     */
    @RequestMapping("query/count/org/users")
    ResponseParam queryCountOrgUsers(@RequestParam("orgId") String orgId);


    /**
     *根据用户id查询用户所有基本信息（用户基本信息，角色信息，资源信息，岗位信息，组织信息）
     *
     * @param userId
     * @author liuke
     * @date 2021/4/23 9:27
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("get/userdetail/by/userid")
    ResponseParam getUserDetailByUserId(@RequestParam("userId") Long userId);

    /**
     *根据用户id查询用户有的系统app
     *
     * @param userId
     * @author liuke
     * @date 2021/4/27 14:40
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("get/apps/by/userid")
    ResponseParam getPlateFormAppsByUserId(@RequestParam("userId") Long userId);

    /**
     * 根据appId查询用户
     * @param appId
     * @return
     */
    @RequestMapping("get/users/by/appid")
    ResponseParam getUsersByAppId(@RequestParam("appId") Long appId);

    /**
     *根据用户名 应用角色编码获取资源
     *

     * @param userId
     * @author liuke
     * @date 2021/4/30 9:27
     * @return com.fosung.framework.web.http.ResponseParam
     */

    @RequestMapping("query/resource/by/userid/and/role")
    ResponseParam queryResourceByAppCodeAndUserIdAndRole( @RequestParam("appCode") String appCode,@RequestParam("userId") Long userId,@RequestParam("roleId") String roleId) ;

    /**
     *根据应用编码与角色id查询资源
     *
     * @param appCode
     * @param roleId
     * @author liuke
     * @date 2022/6/28 14:05
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("query/resource/by/role")
    ResponseParam queryResourceByAppCodeAndRole(  @RequestParam("appCode") String appCode,@RequestParam("roleId") String roleId);

    /**
     *根据应用编码与角色id查询app
     *
     * @param appCode
     * @param roleId
     * @author liuke
     * @date 2022/6/28 14:05
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("query/app/by/role")
    ResponseParam queryAppByAppCodeAndRole(  @RequestParam String appCode,@RequestParam String roleId);
    /**
     *根据用户名 应用编码获取资源
     *
     * @param appCode
     * @param userId
     * @author liuke
     * @date 2021/4/30 9:27
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("/query/resource/by/appcode/and/userid")
    ResponseParam queryResourceByAppCodeAndUserId(@RequestParam("appCode") String appCode, @RequestParam("userId") String userId) ;


    /**
     * 查询全部用户
     * @return
     */
    @PostMapping("/all/user")
     ResponseParam queryUser();


    @PostMapping("/maillist")
     ResponseParam queryMailListByOrgId(@RequestParam("orgId") Long orgId);

    @PostMapping("/allmaillist")
    ResponseParam queryAllMailListByOrgId(@RequestBody Map<String, Object> map );

    @PostMapping("/allmaillazylist")
    ResponseParam queryAllMailLazy(@RequestParam("orgId") String orgId,@RequestParam("projectCode") String projectCode,@RequestParam("orgType") String orgType);

    /**
     *保存用户信息
     *
     * @param user
     * @author liuke
     * @date 2021/12/9 13:38
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("saveuser")
    ResponseParam saveUserById(@RequestBody JSONObject user);

    @PostMapping("queryuserinfo")
    AppUserInfo getUserInfo(@RequestParam("idCardHash") String idCardHash,@RequestParam("clientId") String clientId);

    /**
     *
     * 根据单个角色查询用户
     *
     * @author liuke
     * @date  2022/6/22 14:14
     * @version
    */
    @PostMapping("queryuser/byrole")
    ResponseParam queryUserByRoleCode(@RequestParam("roleCode") String roleCode);

    /**
     * 根据多个角色查询用户
     * @param project
     * @param roleCode
     * @return
     */
    @PostMapping("queryuser/byroles/{project}")
    ResponseParam queryUserByRoleCodes(@PathVariable("project") String project,@RequestParam("roleCodes") String[] roleCode);

    /**
     * 描述: 通过租户id查询租户下全部的用户
     * @param projectCode
     * @return java.util.List<com.fosung.system.pbs.dto.resp.SysUserEntity>
     * @author fuhao
     * @date 2022/3/3 10:09
     **/
    @PostMapping("queryall/byproject")
    List<SysUserEntity> queryAllByProjectId(@RequestParam("projectCode") String projectCode);

    @PostMapping("/queryalluser/{projectCode}")
    List<SysUserEntity> queryAllUser(@PathVariable("projectCode") String projectCode,@RequestBody Map<String,Object> map);

    /**
     *根据条件查询全部管理员用户
     *
     * @param projectCode
     * @param map
     * @author liuke
     * @date 2022/3/16 14:43
     * @return java.util.List<com.fosung.system.support.system.entity.sys.SysUserEntity>
     */
    @PostMapping("/querymanageruser/{projectCode}")
    List<Map<String,Object>> queryAllManagerUser(@PathVariable("projectCode") String projectCode,@RequestBody Map<String,Object> map);


    /**
     * 顶级组织记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query/worker/byorg")
    ResponseParam queryWorkerByScopOrg(@RequestParam(value = "realName" ,required = false) String realName,
                                              @RequestParam("orgIds") ArrayList<Long> orgIds,
                                              @RequestParam("pageNum") int pageNum,
                                              @RequestParam("pageSize") int pageSize);

    /**
     * 顶级组织记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query/countworker/byorg")
    ResponseParam queryWorkerCountByScopOrg(@RequestParam("orgIds") ArrayList<Long> orgIds);

    /**
     * 查询用户
     *
     * @param project
     * @param sysUserDto 过滤条件
     * @return
     */
    @PostMapping("/queryUser/{project}")
    List<Map<String, Object>> queryUser(@PathVariable("project") String project, @RequestBody Map<String, Object> sysUserDto);

    /**
     *根据手机号删除用户
     *
     * @param project
     * @param telephones
     * @author liuke
     * @date 2022/5/8 10:15
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping(value = "deluserbytelephone/{project}")
    ResponseParam delUserByPhone(@PathVariable("project") String project,@RequestParam("telephones") String[] telephones);

    /**
     *批量删除
     *
     * @param userIds
     * @author liuke
     * @date 2022/5/6 10:30
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping(value = "deluser/{project}")
    ResponseParam delUser(@PathVariable("project") String project,@RequestParam("userIds") Long[] userIds);

    /**
     *新增用户接口
     *
     * @param project
     * @param sysUsers
     * @author liuke
     * @date 2022/5/5 10:45
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping(value = "saveuser/{project}")
    ResponseParam saveUser(@PathVariable("project") String project,@RequestBody List<AppSysUser> sysUsers);


    /**
     *修改用户信息
     *
     * @param project
     * @param sysUser
     * @author liuke
     * @date 2022/5/6 10:53
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping(value = "updateuser/{project}")
    ResponseParam updateUser(@PathVariable("project") String project,@RequestBody AppSysUser sysUser);


    /**
     *根据条件分页查询全部管理员用户
     *
     * @param projectCode
     * @param map (roleCode,realName,orgId)
     * @author liuke
     * @date 2022/3/16 14:43
     * @return java.util.List<com.fosung.system.support.system.entity.sys.SysUserEntity>
     */
    @PostMapping("/querymanageruserpage/{projectCode}")
    ResponseParam queryPageManagerUser(@PathVariable("projectCode") String projectCode,@RequestBody Map<String,Object> map);

    /**
     * 查询用户下角色
     *
     * @param project
     * @return
     */
    @PostMapping("/queryUserRole/{project}")
    List<SysAppUserRole> queryUserRole(@PathVariable("project") String project, @RequestParam("userId") Long userId, @RequestParam("appCode") String appCode);

}
