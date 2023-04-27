package com.geek.system.support.system.controller.old;

import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.common.util.UtilString;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.system.support.system.entity.sys.SysOrgEntity;
import com.geek.system.support.system.service.sys.*;
import com.geek.system.support.system.service.sys.AuthClientService;
import com.geek.system.support.system.service.sys.SysOrgService;
import com.geek.system.support.system.service.sys.SysUserService;
import com.google.api.client.util.Lists;
import com.google.api.client.util.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/{source}/api/cloud/app")
public class CloudUserAuthController extends AppIBaseController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysOrgService sysOrgService;

    @Autowired
    protected AuthClientService authClientService;

    /**
     * 查询用户对应应用终端列表信息
     *
     * @param userId        用户ID
     * @param terminalTypes 终端类型，不同的终端类型使用逗号分隔
     * @param projectIds    所属项目id，不同的项目类型使用逗号分隔
     * @param appType       应用分类
     * @return List<CloudAppTerminalCommon> 返回终端列表
     * @Author byb
     * @date 2019/11/28
     */
    @PostMapping(value = "/terminals")
    List<Map<String,Object>> queryAppTerminals(@PathVariable("source") String source,@RequestParam(name = "userId") String userId,
                                                   @RequestParam(name = "terminalTypes", required = false) String terminalTypes,
                                                   @RequestParam(name = "projectIds", required = false) String projectIds,
                                                   @RequestParam(name = "appType", required = false) String appType){

        if(!UtilString.isEmpty(terminalTypes)){
            terminalTypes="'"+terminalTypes.replace(",","','")+"'";
        }
        Map<String,Object> param = Maps.newHashMap();
        param.put("userId",Long.valueOf(userId));
        param.put("terminalTypes",terminalTypes);
        param.put("projectIds",projectIds);
        param.put("appType",appType);
        return Lists.newArrayList(authClientService.queryAppByUserId(param));
    }

    /**
     * @Description: 根据用户ID、客户端ID 查询当前应用下用户管理的角色-组织机构权限信息
     * @Param: userId 用户id
     * @Param: clientId 客户端id
     * @Return:
     * @Author: byb
     * @Date: 2019/12/6
     **/
    @PostMapping(value = "/userorgs")
    List<Map<String,Object>> queryUserOrgCodes(@PathVariable("source") String source,@RequestParam(name = "userId") String userId,
                                                  @RequestParam(name = "clientId") String clientId){
        Map<String,Object> param = Maps.newHashMap();
        param.put("userId",Long.valueOf(userId));
        param.put("clientId",clientId);
        return Lists.newArrayList(authClientService.queryUserOrgsByUserAndClient(param));

    }

    /**
     * 根据角色code、组织id，获取上级用户id
     *
     * @param roleCode
     * @param orgCode
     * @return List<String>
     */
    @PostMapping(value = "/getparentuser")
    List<String> queryParentUser(@PathVariable("source") String source,@RequestParam(name = "roleCode") String roleCode,
                                 @RequestParam(name = "orgCode") String orgCode){
        return Lists.newArrayList(sysUserService.queryParentOrdUserByOrgAndRole(orgCode,roleCode));

    }

    /**
     * 获取上级组织管理员
     *
     * @param roleCode
     * @param orgId
     * @return
     */
    @PostMapping(value = "/queryParentManager")
    List<String> queryParentManager(@PathVariable("source") String source,@RequestParam("roleCode") String roleCode,
                                    @RequestParam("orgId") String orgId){
        SysOrgEntity sysOrgEntity = sysOrgService.get(Long.valueOf(orgId));
        if(sysOrgEntity == null){
            return Lists.newArrayList();
        }
        return Lists.newArrayList(sysUserService.queryParentOrdUserByOrgAndRole(sysOrgEntity.getOrgCode(),roleCode));
    }

    /**
     * 查询当前组织和应用下的管理员
     *
     * @param orgId
     * @param appId
     * @return
     */
    @PostMapping("/queryOrgManager")
    ResponseParam queryOrgManager(@PathVariable("source") String source,@RequestParam("orgId") String orgId,
                                  @RequestParam("appId") String appId){
        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("orgId",Long.valueOf(orgId));
        searchParam.put("appId",Long.valueOf(appId));
        Set<String> ids = sysUserService.queryParentOrdUserByOrgIdAndAppId(searchParam);
        return ResponseParam.success().data(ids);

    }

    /**
     * 根据组织和应用,角色code查询管理员
     *
     * @param orgId 组织id
     * @param appId 应用id
     * @param roleCode 角色编码，可传用逗号隔开的字符串
     * @return
     */
    @PostMapping("/queryOrgAndRoleCodeManager")
    ResponseParam queryOrgAndRoleCodeManager(@PathVariable("source") String source,@RequestParam("orgId") String orgId,
                                             @RequestParam("appId") String appId,
                                             @RequestParam("roleCode") String roleCode){
        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("orgId",Long.valueOf(orgId));
        searchParam.put("appId",Long.valueOf(appId));
        searchParam.put("roleCode",roleCode);
        Set<String> ids = sysUserService.queryParentOrdUserByOrgIdAndAppId(searchParam);
        return ResponseParam.success().data(ids);

    }

    /**
     * 根据组织和应用,角色code查询管理员
     * @param orgId
     * @param appId
     * @param roleCode 可传用逗号隔开的字符串
     * @param userId
     * @return
     */
    @PostMapping("/queryOrgAndRoleCodeAndUserManager")
    List<Map<String,Object>> queryOrgAndRoleCodeManager(@PathVariable("source") String source,@RequestParam("orgId") String orgId,
                                                  @RequestParam("appId") String appId,
                                                  @RequestParam("roleCode") String roleCode,
                                                  @RequestParam(value = "userId", required = false) Long userId){
        List<Map<String,Object>> lists = Lists.newArrayList();
        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("orgId",Long.valueOf(orgId));
        searchParam.put("appId",Long.valueOf(appId));
        searchParam.put("roleCode",roleCode);
        searchParam.put("userId",userId);
        Set<Map<String,Object>> users = sysUserService.queryParentOrdUserMapByOrgIdAndAppId(searchParam);
        for (Map<String,Object> user : users) {
            Map<String,Object> map = Maps.newHashMap();
            map.put("appId",null);
            map.put("id",null);
            map.put("name",null);
            map.put("code",roleCode);
            map.put("userId",user.get("id"));
            map.put("realName",user.get("name"));
            lists.add(map);
        }
        return lists;

    }

    /**
     * 查询当组织和应用下的管理员
     *
     * @param cloudAdminParam 查询参数
     * @return
     */
    @PostMapping("/queryManagerByOrgArray")
    ResponseParam queryManagerByOrgArray(@PathVariable("source") String source,@RequestBody Map<String,Object> cloudAdminParam){
        cloudAdminParam.put("appId",cloudAdminParam.get("appId")==null?null:Long.valueOf(cloudAdminParam.get("appId").toString()));
        cloudAdminParam.put("orgIds",cloudAdminParam.get("orgId")==null?null:((List<?>)cloudAdminParam.get("orgId")).stream().map(map -> Long.valueOf(map.toString())).collect(Collectors.toList()));
        cloudAdminParam.put("roleIds",cloudAdminParam.get("roleId")==null?null:((List<?>)cloudAdminParam.get("roleId")).stream().map(map -> Long.valueOf(map.toString())).collect(Collectors.toList()));

        return ResponseParam.success().data(sysUserService.queryUsersByOrgIdsAndAppIds(cloudAdminParam));
    }

    /**
     * 根据条件查询管理员
     *
     * @param cloudAdminParam 查询参数
     * @return
     */
    @PostMapping("/queryManager")
    List<Map<String,Object>> queryManager(@PathVariable("source") String source,@RequestBody Map<String,Object> cloudAdminParam){
        cloudAdminParam.put("appId",cloudAdminParam.get("appId")==null?null:Long.valueOf(cloudAdminParam.get("appId").toString()));
        cloudAdminParam.put("orgIds",cloudAdminParam.get("orgId")==null?null:((List<?>)cloudAdminParam.get("orgId")).stream().map(map -> Long.valueOf(map.toString())).collect(Collectors.toList()));
        cloudAdminParam.put("roleIds",cloudAdminParam.get("roleId")==null?null:((List<?>)cloudAdminParam.get("roleId")).stream().map(map -> Long.valueOf(map.toString())).collect(Collectors.toList()));
        cloudAdminParam.put("userId",cloudAdminParam.get("userId")==null?null:Long.valueOf(cloudAdminParam.get("userId").toString()));
        return Lists.newArrayList(sysUserService.queryUsersByOrgIdsAndAppIds(cloudAdminParam));
    }
    public DTOCallbackHandler getDtoCallbackHandler() {

        //创建转换接口类
        DTOCallbackHandler dtoCallbackHandler = new DTOCallbackHandler() {
            @Override
            public void doHandler(Map<String, Object> dtoMap, Class<?> itemClass) {

            }
        };

        return getDTOCallbackHandlerProxy(dtoCallbackHandler, true);
    }

}

