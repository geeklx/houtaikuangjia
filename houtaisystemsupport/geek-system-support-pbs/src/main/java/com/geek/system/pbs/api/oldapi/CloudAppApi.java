package com.geek.system.pbs.api.oldapi;

import com.fosung.framework.web.http.ResponseParam;
import com.geek.system.pbs.dto.support.cloudapp.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@FeignClient(name = "CloudAppApi" , url = "${app.pbs.client.url}")
@RequestMapping("/{source}/api/cloud/app")
public interface CloudAppApi {


    /**
     * 查询应用终端信息
     * @param clientId 终端client_id
     * @return CloudAppTerminal 返回终端信息
     * @Author byb
     * @date 2019/11/28
     */
    @PostMapping(value = "/terminal/info")
    CloudAppTerminal queryAppTerminal(@PathVariable("source") String source, @RequestParam(name = "client_id") String clientId) ;

    /**
     * 查询应用列表信息
     * @param terminalTypes 终端类型，不同的终端类型使用逗号分隔
     * @return
     */
    @PostMapping(value = "/list")
    List<CloudApp> queryApp(@PathVariable("source") String source,@RequestParam(name = "terminalTypes") String terminalTypes) ;

    /**
     * 查询应用终端列表
     * @param appIds 应用id，不同的id使用逗号分隔
     * @param terminalType
     * @return
     */
    @PostMapping(value = "/terminal/list")
    List<CloudAppTerminal> queryAppTerminal(@PathVariable("source") String source,@RequestParam(name = "appIds") String appIds,
                                            @RequestParam(name = "terminalType") String terminalType) ;
    /**
     *
     * @Title: queryProjList
     * @Description:  查询所有项目信息
     * @param @param name 项目名称
     * @param @param logonChannel 登录渠道
     * @param @return
     * @return List<CloudProj>
     * @throws
     */
    @PostMapping(value = "/projList")
    List<CloudProj> queryProjList(@PathVariable("source") String source,@RequestParam(name = "name") String name ,
                                  @RequestParam(name = "logonChannel") String logonChannel) ;

    /**
     *
     * @Title: queryProjById
     * @Description: 通过项目id查询项目信息
     * @param @param id
     * @param @return
     * @return CloudProj
     * @throws
     */
    @PostMapping(value = "/queryProjById")
    CloudProj queryProjById(@PathVariable("source") String source,@RequestParam(name = "id") Long id ) ;

    /**
     * 获取所有应用的默认角色
     * @return
     */
    @PostMapping(value = "/query_app_default_role")
    List<CloudAppRoleAdmin> queryAllAppDefaultRole(@PathVariable("source") String source) ;

    /**
     * 保存用户默认应用角色
     * @param cloudAppRoleAdmins
     * @return
     */
    @PostMapping(value = "/save_user_admin_role")
    ResponseParam saveUserAdminRole(@PathVariable("source") String source,@RequestBody List<CloudAppRoleAdmin> cloudAppRoleAdmins);

    /**
     * 校验安全中心是否有绑定的角色
     * @param orgCode
     * @return
     */
    @PostMapping(value = "/terminal/checkBindTerminalByOrgCode")
    ResponseParam checkBindTerminalByOrgCode(@PathVariable("source") String source,@RequestParam(name = "orgCode") String orgCode);

    /**
     * 根据应用id查询系统下的角色
     * @param appIds
     * @return
     */
    @PostMapping(value = "/queryRoleByApp")
    List<CloudAppRole> queryRoleByApp(@PathVariable("source") String source,@RequestParam(name = "appIds") ArrayList<Long> appIds);


}
