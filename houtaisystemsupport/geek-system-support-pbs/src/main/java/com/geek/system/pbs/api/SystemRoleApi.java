package com.geek.system.pbs.api;

import com.fosung.framework.web.http.ResponseParam;
import com.geek.system.pbs.dto.AppSysRoleDto;
import com.geek.system.pbs.dto.ShelvesUserRoleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "SystemRoleApi", url = "${app.pbs.client.url}")
@RequestMapping(value = "/api/system/role")
public interface SystemRoleApi {

    /**
     *
     * 获取所有角色列表
     * @author liuke
     * @date 2021/11/1 14:19
     * @return com.geek.framework.web.http.ResponseParam
     */
    @RequestMapping("list")
    ResponseParam getRole() ;

    /**
     *
     * 查询角色下的人员
     * @author gaojian
     * @date 2021/11/1 14:19
     * @return com.geek.framework.web.http.ResponseParam
     */
    @RequestMapping("list/user")
    ResponseParam getRoleUser(@RequestParam("appId") Long appId, @RequestParam("roleCode") String roleCode) ;

    /**
     *根据应用id查询所有角色
     *
     * @param appId
     * @author liuke
     * @date 2021/11/1 14:26
     * @return com.geek.framework.web.http.ResponseParam
     */
    @RequestMapping("list/by/app")
    ResponseParam getRoleByApp(Long appId);

    /**
     * 查询项目下所有角色
     * @param project
     * @param sysRoleDto
     * @return
     */
    @RequestMapping("list/param/{project}")
    ResponseParam getRoleByParam(@PathVariable("project") String project, @RequestBody AppSysRoleDto sysRoleDto);


    /**
     *
     * 批量授权
     *
     * @author liuke
     * @version
     */
    @PostMapping("batch/shelves/{project}")
    ResponseParam batchShelves(@PathVariable("project") String project, @RequestBody ShelvesUserRoleDto shelvesUserRoleDto);

    /**
     *
     * 批量授权
     *
     * @author liuke
     * @version
     */
    @PostMapping("query/appbyrole/{project}")
    ResponseParam queryAppByRole(@PathVariable("project") String project, @RequestParam("roleId") Long roleId);

    }
