package com.fosung.system.pbs.api.oldapi;

import com.fosung.system.pbs.dto.CloudAuthUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fosung.framework.web.http.ResponseParam;

@FeignClient(name = "CloudAuthApi", url = "${app.pbs.client.url}")
@RequestMapping("/{source}/api/cloud/auth")
public interface CloudAuthApi {

	/**
	 * 用户名和密码登陆
	 *
	 * @param username
	 * @param password
	 * @param logonChannelType
	 * @return
	 */
	@PostMapping("/login")
	CloudAuthUser loginByUserNameAndPassWord(@PathVariable("source") String source,
											 @RequestParam(name = "username") String username, @RequestParam(name = "password") String password,
											 @RequestParam(name = "logonChannelType") String logonChannelType);

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
	CloudAuthUser systemLogin(@PathVariable("source") String source,
							  @RequestParam(name = "username") String username,
							  @RequestParam(name = "password") String password,
							  @RequestParam(name = "logonChannelType") String logonChannelType,
							  @RequestParam(name = "loginSource", required = false) String loginSource);

	/**
	 * 刷新token登陆
	 *
	 * @param refreshToken
	 * @param logonChannelType
	 * @return
	 */
	@PostMapping("/refresh/token")
	CloudAuthUser loginByRefreshToken(@PathVariable("source") String source,
			@RequestParam(name = "refreshToken") String refreshToken,
			@RequestParam(name = "logonChannelType") String logonChannelType);

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
			@RequestParam("password") String password);

	/**
	 * 忘记密码
	 * @param telephone
	 * @param password
	 * @return
	 */
	@PostMapping("/reset/password")
	ResponseParam resetPassword(@PathVariable("source") String source, @RequestParam("telephone") String telephone,
			@RequestParam("password") String password);

	/**
	 * 更新密码
	 * @param telephone
	 * @param originPassword
	 * @param newPassword
	 * @return
	 */
	@PostMapping("/update/password")
	ResponseParam updatePassword(@PathVariable("source") String source, @RequestParam("telephone") String telephone,
			@RequestParam("originPassword") String originPassword, @RequestParam("newPassword") String newPassword);
}
