package com.geek.system.support.system.controller.old;

import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.system.support.system.dict.AuthStatus;
import com.geek.system.support.system.dict.UserStatus;
import com.geek.system.support.system.entity.sys.SysUserEntity;
import com.geek.system.support.system.service.sys.SysProjectService;
import com.geek.system.support.system.service.sys.SysUserService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/{source}/api/cloud/auth")
public class CloudAuthController {

	@Autowired
	private SysProjectService sysProjectService;
	@Autowired
	private SysUserService sysUserService;

	/**
	 * 用户名和密码登陆
	 *
	 * @param username
	 * @param password
	 * @param logonChannelType
	 * @return
	 */
	@PostMapping("/login")
	Map<String,Object> loginByUserNameAndPassWord(@PathVariable("source") String source,
												  @RequestParam(name = "username") String username, @RequestParam(name = "password") String password,
												  @RequestParam(name = "logonChannelType") String logonChannelType) {

		return null;
	}

	/**
	 * 用户名和密码登陆
	 *
	 * @param username
	 * @param password
	 * @param logonChannelType
	 * @param loginSource 登陆系统来源
	 * @return
	 */
	@PostMapping("/systemLogin")
	Map<String,Object> systemLogin(@PathVariable("source") String source,
							  @RequestParam(name = "username") String username,
							  @RequestParam(name = "password") String password,
							  @RequestParam(name = "logonChannelType") String logonChannelType,
							  @RequestParam(name = "loginSource", required = false) String loginSource){

		return null;
	}

	/**
	 * 刷新token登陆
	 *
	 * @param refreshToken
	 * @param logonChannelType
	 * @return
	 */
	@PostMapping("/refresh/token")
	Map<String,Object> loginByRefreshToken(@PathVariable("source") String source,
			@RequestParam(name = "refreshToken") String refreshToken,
			@RequestParam(name = "logonChannelType") String logonChannelType){

		return null;
	}

	/**
	 * 用户注册接口
	 *
	 * @param realName
	 * @param idCard
	 * @param telephone
	 * @param password
	 * @return
	 */
	@PostMapping("/register")
	ResponseParam register(@PathVariable("source") String source, @RequestParam("realName") String realName,
			@RequestParam("idCard") String idCard, @RequestParam("telephone") String telephone,
			@RequestParam("password") String password){
		Long projectId = sysProjectService.getProjectId(source);
		// 查询人员信息
		HashMap<String, Object> searchParams = Maps.newHashMap();
		searchParams.put("projectId",projectId);
		searchParams.put("realName",realName);
		searchParams.put("idCard",idCard);
		searchParams.put("del",false);
		List<SysUserEntity> sysUserEntities = sysUserService.queryAll(searchParams);
		if(UtilCollection.isNotEmpty(sysUserEntities)){
			return ResponseParam.fail().message("用户不存在!");
		}
		SysUserEntity sysUserEntity = sysUserEntities.get(0);
		if(sysUserEntity.getStatus().equals(UserStatus.INVALID.name())){
			return ResponseParam.fail().message("用户已禁用!");
		}
		if(sysUserEntity.getAuthStatus().equals(AuthStatus.AUTH.name())){
			return ResponseParam.fail().message("用户已激活!");
		}
		HashMap<String, Object> checkTelephone = Maps.newHashMap();
		checkTelephone.put("del",false);
		checkTelephone.put("projectId",projectId);
		checkTelephone.put("userName",telephone);
		if(sysUserService.isExist(checkTelephone)){
			return ResponseParam.fail().message("手机号已被注册!");
		}
		sysUserEntity.setPassword(password);
		sysUserEntity.setTelephone(telephone);
		sysUserEntity.setUserName(telephone);
		sysUserEntity.setAuthStatus(AuthStatus.AUTH);
		sysUserEntity.setAuthTime(new Date());
		sysUserService.update(sysUserEntity,
				Arrays.asList("password","telephone","userName","authStatus","authTime"));
		return ResponseParam.success().data(true);
	}

	/**
	 * 忘记密码
	 * @param telephone
	 * @param password
	 * @return
	 */
	@PostMapping("/reset/password")
	ResponseParam resetPassword(@PathVariable("source") String source, @RequestParam("telephone") String telephone,
			@RequestParam("password") String password){
		Long projectId = sysProjectService.getProjectId(source);
		// 查询人员信息
		HashMap<String, Object> searchParams = Maps.newHashMap();
		searchParams.put("projectId",projectId);
		searchParams.put("telephone",telephone);
		searchParams.put("del",false);
		List<SysUserEntity> sysUserEntities = sysUserService.queryAll(searchParams);
		if(UtilCollection.sizeIsEmpty(sysUserEntities)){
			return ResponseParam.fail().message("用户不存在!");
		}
		searchParams.put("authStatus",AuthStatus.AUTH);
		List<SysUserEntity> checkAuthStatus = sysUserService.queryAll(searchParams);
		if(UtilCollection.sizeIsEmpty(checkAuthStatus)){
			return ResponseParam.fail().message("用户未激活!");
		}
		SysUserEntity sysUserEntity = checkAuthStatus.get(0);
		if(sysUserEntity.getPassword().equals(sysUserService.encodPassword(password))){
			return ResponseParam.fail().message("原密码与新密码不能相同!");
		}
		sysUserEntity.setPassword(password);
		sysUserService.update(sysUserEntity,Arrays.asList("password"));
		return ResponseParam.success().data(true);
	}

	/**
	 * 更新密码
	 * @param telephone
	 * @param originPassword
	 * @param newPassword
	 * @return
	 */
	@PostMapping("/update/password")
	ResponseParam updatePassword(@PathVariable("source") String source, @RequestParam("telephone") String telephone,
			@RequestParam("originPassword") String originPassword, @RequestParam("newPassword") String newPassword){
		Long projectId = sysProjectService.getProjectId(source);
		// 查询人员信息
		HashMap<String, Object> searchParams = Maps.newHashMap();
		searchParams.put("projectId",projectId);
		searchParams.put("telephone",telephone);
		searchParams.put("del",false);
		List<SysUserEntity> checkUser = sysUserService.queryAll(searchParams);
		if(UtilCollection.sizeIsEmpty(checkUser)){
			return ResponseParam.fail().data("用户不存在!");
		}
		searchParams.put("authStatus", AuthStatus.AUTH);
		List<SysUserEntity> checkAuthStatus = sysUserService.queryAll(searchParams);
		if (UtilCollection.sizeIsEmpty(checkAuthStatus)) {
			return ResponseParam.fail().message("用户未激活!");
		}
		SysUserEntity sysUserEntity = checkAuthStatus.get(0);
		if(!sysUserEntity.getPassword().equals(sysUserService.encodPassword(originPassword))){
			return ResponseParam.fail().message("原密码不正确!");
		}
		if(sysUserEntity.getPassword().equals(sysUserService.encodPassword(newPassword))){
			return ResponseParam.fail().message("原密码与新密码不能相同!");
		}
		sysUserEntity.setPassword(newPassword);
		sysUserService.update(sysUserEntity,Arrays.asList("password"));
		return ResponseParam.success().data(true);

	}
}
