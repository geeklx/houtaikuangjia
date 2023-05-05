package com.fosung.system.pbs.api.oldapi;


import com.fosung.framework.web.http.ResponseParam;
import com.fosung.system.pbs.dto.support.cloudapp.CloudAdminParam;
import com.fosung.system.pbs.dto.support.cloudapp.CloudAppRole;
import com.fosung.system.pbs.dto.support.cloudapp.CloudAppTerminalCommon;
import com.fosung.system.pbs.dto.support.cloudapp.CloudUserControlScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * 对外提供安全中心的sdk接口
 *
 * @author liuke
 * @date  2022/2/23 15:41
 * @version
*/
@FeignClient(name = "CloudAppApi", url = "${app.pbs.client.url}")
@RequestMapping("/{source}/api/cloud/app")
public interface CloudUserAuthApi {

    /**
     *查询用户对应应用终端列表信息
     *
     * @param userId
     * @param terminalTypes
     * @param projectIds
     * @param appType
     * @author liuke
     * @date 2022/2/23 15:41
     * @return java.util.List<com.fosung.system.pbs.dto.support.cloudapp.CloudAppTerminalCommon>
     */
    @PostMapping(value = "/terminals")
    List<CloudAppTerminalCommon> queryAppTerminals(@PathVariable("source") String source, @RequestParam(name = "userId") String userId,
                                                   @RequestParam(name = "terminalTypes", required = false) String terminalTypes,
                                                   @RequestParam(name = "projectIds", required = false) String projectIds,
                                                   @RequestParam(name = "appType", required = false) String appType);

    /**
     *根据用户ID、客户端ID 查询当前应用下用户管理的角色-组织机构权限信息
     *
     * @param userId
     * @param clientId
     * @author liuke
     * @date 2022/2/23 15:42
     * @return java.util.List<com.fosung.system.pbs.dto.support.cloudapp.CloudUserControlScope>
     */
    @PostMapping(value = "/userorgs")
    List<CloudUserControlScope> queryUserOrgCodes(@PathVariable("source") String source,@RequestParam(name = "userId") String userId,
                                                  @RequestParam(name = "clientId") String clientId);

    /**
     *根据角色code、组织id，获取上级用户id
     *
     * @param roleCode
     * @param orgCode
     * @author liuke
     * @date 2022/2/23 15:42
     * @return java.util.List<java.lang.String>
     */
    @PostMapping(value = "/getparentuser")
    List<String> queryParentUser(@PathVariable("source") String source,@RequestParam(name = "roleCode") String roleCode,
                                 @RequestParam(name = "orgCode") String orgCode);

    /**
     *获取上级组织管理员
     *
     * @param roleCode
     * @param orgId
     * @author liuke
     * @date 2022/2/23 15:42
     * @return java.util.List<java.lang.String>
     */
    @PostMapping(value = "/queryParentManager")
    List<String> queryParentManager(@PathVariable("source") String source,@RequestParam("roleCode") String roleCode,
                                    @RequestParam("orgId") String orgId);

    /**
     *查询当前组织和应用下的管理员
     *
     * @param orgId
     * @param appId
     * @author liuke
     * @date 2022/2/23 15:42
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/queryOrgManager")
    ResponseParam queryOrgManager(@PathVariable("source") String source,@RequestParam("orgId") String orgId,
                                  @RequestParam("appId") String appId);

    /**
     *根据组织和应用,角色code查询管理员
     *
     * @param orgId
     * @param appId
     * @param roleCode
     * @author liuke
     * @date 2022/2/23 15:42
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/queryOrgAndRoleCodeManager")
    ResponseParam queryOrgAndRoleCodeManager(@PathVariable("source") String source,@RequestParam("orgId") String orgId,
                                             @RequestParam("appId") String appId,
                                             @RequestParam("roleCode") String roleCode);

    /**
     *根据组织和应用,角色code查询管理员
     *
     * @param orgId
     * @param appId
     * @param roleCode
     * @param userId
     * @author liuke
     * @date 2022/2/23 15:42
     * @return java.util.List<com.fosung.system.pbs.dto.support.cloudapp.CloudAppRole>
     */
    @PostMapping("/queryOrgAndRoleCodeAndUserManager")
    List<CloudAppRole> queryOrgAndRoleCodeManager(@PathVariable("source") String source,@RequestParam("orgId") String orgId,
                                                  @RequestParam("appId") String appId,
                                                  @RequestParam("roleCode") String roleCode,
                                                  @RequestParam(value = "userId", required = false) String userId);

    /**
     *查询当组织和应用下的管理员
     *
     * @param cloudAdminParam
     * @author liuke
     * @date 2022/2/23 15:42
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/queryManagerByOrgArray")
    ResponseParam queryManagerByOrgArray(@PathVariable("source") String source,@RequestBody CloudAdminParam cloudAdminParam);

    /**
     *根据条件查询管理员
     *
     * @param cloudAdminParam
     * @author liuke
     * @date 2022/2/23 15:43
     * @return java.util.List<com.fosung.system.pbs.dto.support.cloudapp.CloudAppRole>
     */
    @PostMapping("/queryManager")
    List<CloudAppRole> queryManager(@PathVariable("source") String source,@RequestBody CloudAdminParam cloudAdminParam);
}
