package com.fosung.system.pbs.api.oldapi;

import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.system.pbs.dto.CloudAuthUser;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.fosung.framework.web.http.ResponseParam;

public abstract class AbstractCloudAuthApi extends AppIBaseController implements CloudAuthApi {

	@Override
	public CloudAuthUser loginByUserNameAndPassWord(@PathVariable("source") String source,
													@RequestParam(name = "username") String username, @RequestParam(name = "password") String password,
													@RequestParam(name = "logonChannelType") String logonChannelType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CloudAuthUser systemLogin(@PathVariable("source") String source,
									 @RequestParam(name = "username") String username,
									 @RequestParam(name = "password") String password,
									 @RequestParam(name = "logonChannelType") String logonChannelType,
									 @RequestParam(name = "loginSource", required = false) String loginSource) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CloudAuthUser loginByRefreshToken(@PathVariable("source") String source,
			@RequestParam(name = "refreshToken") String refreshToken,
			@RequestParam(name = "logonChannelType") String logonChannelType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseParam register(@PathVariable("source") String source, @RequestParam("realName") String realName,
			@RequestParam("idCard") String idCard, @RequestParam("telephone") String telephone,
			@RequestParam("password") String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseParam resetPassword(@PathVariable("source") String source,
			@RequestParam("telephone") String telephone, @RequestParam("password") String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseParam updatePassword(@PathVariable("source") String source,
			@RequestParam("telephone") String telephone, @RequestParam("originPassword") String originPassword,
			@RequestParam("newPassword") String newPassword) {
		// TODO Auto-generated method stub
		return null;
	}

}
