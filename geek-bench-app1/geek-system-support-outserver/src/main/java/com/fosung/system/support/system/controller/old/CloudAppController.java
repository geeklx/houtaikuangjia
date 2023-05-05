package com.fosung.system.support.system.controller.old;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.system.support.system.dto.out.CloudAppDto;
import com.fosung.system.support.system.entity.sys.AuthClientEntity;
import com.fosung.system.support.system.entity.sys.SysProjectEntity;
import com.fosung.system.support.system.service.sys.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/{source}/api/cloud/app")
public class CloudAppController extends AppIBaseController {

	@Autowired
	private SysProjectService sysProjectService;

	@Autowired
	private AuthClientService authClientService;

	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysUserService sysUserService;


	/**
	 * 查询应用终端信息
	 * @param clientId 终端client_id
	 * @return CloudAppTerminal 返回终端信息
	 * @Author byb
	 * @date 2019/11/28
	 */
	@PostMapping(value = "/terminal/info")
	Map<String,Object> queryAppTerminal(@PathVariable("source") String source,@RequestParam(name = "client_id") String clientId) {
		Long projectId = sysProjectService.getProjectId(source);
		Map<String,Object> searchParam = Maps.newHashMap();
		searchParam.put("projectId",projectId);
		searchParam.put("clientId",clientId);
		List<AuthClientEntity> lists =  authClientService.queryAll(searchParam);
		if(UtilCollection.sizeIsEmpty(lists)){
			return null;
		}
		return parseAuthClientToMap(lists.get(0));
	}

	/**
	 * 查询应用列表信息
	 * @param terminalTypes 终端类型，不同的终端类型使用逗号分隔
	 * @return
	 */
	@PostMapping(value = "/list")
	List<Map<String,Object>> queryApp(@PathVariable("source") String source,@RequestParam(name = "terminalTypes") String terminalTypes) {
		List<Map<String,Object>> rlists = Lists.newArrayList();
		Long projectId = sysProjectService.getProjectId(source);
		Map<String,Object> searchParam = Maps.newHashMap();
		searchParam.put("projectId",projectId);
		searchParam.put("types", Lists.newArrayList(terminalTypes.split(",")));
		List<AuthClientEntity> lists =  authClientService.queryAll(searchParam);
		if(UtilCollection.sizeIsEmpty(lists)){
			return rlists;
		}
		for (AuthClientEntity list : lists) {
			rlists.add(new LinkedHashMap<String , Object>(){
				{
					put("id",list.getId());
					put("name",list.getName());
					put("code",list.getCode());
					put("icon",list.getIcon());
				}
			});
		}
		return rlists;
	}

	/**
	 * 查询应用终端列表
	 * @param appIds 应用id，不同的id使用逗号分隔
	 * @param terminalType
	 * @return
	 */
	@PostMapping(value = "/terminal/list")
	List<Map<String,Object>> queryAppTerminalList(@PathVariable("source") String source,@RequestParam(name = "appIds") String appIds,
											@RequestParam(name = "terminalType") String terminalType) {
		List<Map<String,Object>> rlists = Lists.newArrayList();
		Long projectId = sysProjectService.getProjectId(source);
		Map<String,Object> searchParam = Maps.newHashMap();
		searchParam.put("appIds",Lists.newArrayList(appIds.split(",")));
		searchParam.put("type", terminalType);
		searchParam.put("projectId",projectId);
		List<AuthClientEntity> lists =  authClientService.queryAll(searchParam);
		if(UtilCollection.sizeIsEmpty(lists)){
			return rlists;
		}
		for (AuthClientEntity list : lists) {
			rlists.add(parseAuthClientToMap(list));
		}
		return rlists;
	}
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
	List<Map<String,Object>> queryProjList(@PathVariable("source") String source,@RequestParam(name = "name") String name ,
								  @RequestParam(name = "logonChannel") String logonChannel) {
		List<Map<String,Object>> lists = Lists.newArrayList();
		Map<String,Object> searchParams = Maps.newHashMap();
		searchParams.put("projectNameL",name);
		List<SysProjectEntity> sysProjectEntityList = sysProjectService.queryAll(searchParams);
		if (UtilCollection.sizeIsEmpty(sysProjectEntityList)){
			return lists;
		}
		for (SysProjectEntity sysProjectEntity : sysProjectEntityList) {
		lists.add(parseProjectToMap(sysProjectEntity));
		}
		return lists;
	}

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
	Map<String,Object> queryProjById(@PathVariable("source") String source,@RequestParam(name = "id") Long id ) {
        SysProjectEntity sysProjectEntity = sysProjectService.get(id);
		if(sysProjectEntity==null){
			return null;
		}
		return parseProjectToMap(sysProjectEntity);
	}

	/**
	 * 获取所有应用的默认角色
	 * @return
	 */
	@PostMapping(value = "/query_app_default_role")
	List<Map<String,Object>> queryAllAppDefaultRole(@PathVariable("source") String source) {
		return null;
	}

	/**
	 * 保存用户默认应用角色
	 * @param cloudAppRoleAdmins
	 * @return
	 */
	@PostMapping(value = "/save_user_admin_role")
	ResponseParam saveUserAdminRole(@PathVariable("source") String source,@RequestBody List<Map<String,Object>> cloudAppRoleAdmins){
		return null;
	}

	/**
	 * 校验安全中心是否有绑定的角色
	 * @param orgCode
	 * @return
	 */
	@PostMapping(value = "/terminal/checkBindTerminalByOrgCode")
	ResponseParam checkBindTerminalByOrgCode(@PathVariable("source") String source,@RequestParam(name = "orgCode") String orgCode){
		return null;
	}

	/**
	 * 根据应用id查询系统下的角色
	 * @param appIds
	 * @return
	 */
	@PostMapping(value = "/queryRoleByApp")
	List<Map<String,Object>> queryRoleByApp(@PathVariable("source") String source,@RequestParam(name = "appIds") ArrayList<Long> appIds){
		return sysRoleService.queryRoleByAppIds(sysProjectService.getProjectId(source),appIds);

	}

	private Map<String ,Object> parseAuthClientToMap(AuthClientEntity authClientEntity){
		Map<String,Object> map = Maps.newHashMap();
		map.put("id",authClientEntity.getId());
		map.put("appId",authClientEntity.getAppId());
		map.put("name",authClientEntity.getName());
		map.put("code",authClientEntity.getCode());
		map.put("clientId",authClientEntity.getClientId());
		map.put("clientSecret",authClientEntity.getClientSecret());
		map.put("scope",authClientEntity.getScope());
		map.put("authorizedGrantTypes",authClientEntity.getAuthorizedGrantTypes());
		map.put("accessTokenValidity",authClientEntity.getAccessTokenValidity());
		map.put("webServerRedirectUri",authClientEntity.getWebServerRedirectUri());
		map.put("refreshTokenValidity",authClientEntity.getRefreshTokenValidity());
		map.put("autoApprove",authClientEntity.getAutoApprove());
		map.put("icon",authClientEntity.getIcon());
		map.put("url",authClientEntity.getUrl());
		map.put("type",authClientEntity.getType());
		return map;
	}

	private Map<String,Object> parseProjectToMap(SysProjectEntity sysProjectEntity){
		Map<String,Object> map = Maps.newHashMap();
		map.put("success",true);
		map.put("message","查询项目信息成功");
		map.put("id",sysProjectEntity.getId());
		map.put("name",sysProjectEntity.getProjectName());
		map.put("logonChannel","");
		map.put("logonChannelName","");
		return map;
	}

	@PostMapping("/query")
	public ResponseParam queryUserAppList(@PathVariable("source") String source, @RequestParam("userId") Long userId) {
		// 通过用户id查询租户id
		List<CloudAppDto> sysUserResourceEntities = sysUserService.queryUserAppList(userId);
		Map<String, List<CloudAppDto>> groupByAppCategorys = sysUserResourceEntities.stream().collect(Collectors.groupingBy(CloudAppDto::getAppTypeCode));
		Set<String> appCategorys = groupByAppCategorys.keySet();
		List<CloudAppDto> resultList = Lists.newArrayList();
		for (String category :appCategorys) {
			List<CloudAppDto> authClientEntities = groupByAppCategorys.get(category);
			CloudAppDto cloudAppDto = new CloudAppDto();
			for (CloudAppDto app : sysUserResourceEntities){
				if(category.equals(app.getAppTypeCode())){
					cloudAppDto.setAppTypeCode(app.getAppTypeCode());
					cloudAppDto.setAppTypeName(app.getAppTypeName());
					cloudAppDto.setNum(app.getNum());
				}
			}
			cloudAppDto.setAppDetailArray(authClientEntities);
			resultList.add(cloudAppDto);
		}
		List<Map<String, Object>> result = UtilDTO.toDTO(resultList,
				null, getDtoCallbackHandler());
		return ResponseParam.success().data(result);
	}

	/**
	 * 获取PO到DTO转换的接口。主要用于在前端展示数据之前首先将数据格式处理完成。
	 *
	 * @return
	 */
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
