package com.fosung.system.pbs.api.oldapi;

import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.system.pbs.dto.support.dt.CloudUserSsoDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public abstract class AbstractCloudUserSsoApi extends AppIBaseController implements CloudUserSsoApi {

	/**
	 * 描述: 用户注册激活
	 * @param dto
	 * @return com.fosung.framework.web.http.ResponseParam
	 * @author fuhao
	 * @date 2022/2/15 16:38
	 **/
	@Override
	public ResponseParam active(@PathVariable("source") String source,@RequestBody CloudUserSsoDto dto) {
		return null;
	}

	/**
	 * 描述: 忘记密码（sso页面）
	 * @param dto
	 * @return com.fosung.framework.web.http.ResponseParam
	 * @author fuhao
	 * @date 2022/2/15 16:40
	 **/
	@Override
	public ResponseParam resetPassword(@PathVariable("source") String source,@RequestBody CloudUserSsoDto dto){
		return null;
	}

	/**
	 * 描述: 管理端修改密码（sso页面）
	 * @param dto
	 * @return com.fosung.framework.web.http.ResponseParam
	 * @author fuhao
	 * @date 2022/2/18 13:57
	 **/
	@Override
	public ResponseParam manageResetPassword(@PathVariable("source") String source,CloudUserSsoDto dto) {
		return null;
	}

	/**
	 * 描述: 更新注册手机号（sso页面）
	 * @param phone
	 * @param userId
	 * @param code
	 * @return com.fosung.framework.web.http.ResponseParam
	 * @author fuhao
	 * @date 2022/2/15 16:42
	 **/
	@Override
	public ResponseParam updatePhone(@PathVariable("source") String source,@PathVariable("phone") String phone,@PathVariable("userId") String userId,@PathVariable("code") String code){
		return null;
	}

	/**
	 * 描述: 根据容联token获取用户信息（sso页面）
	 * @param token
	 * @return com.fosung.framework.web.http.ResponseParam
	 * @author fuhao
	 * @date 2022/2/15 16:45
	 **/
	@Override
	public ResponseParam getByToken(@PathVariable("source") String source,@RequestParam(value = "token", required = true) String token){
		return null;
	}


	/**
	 * 描述: 根据手机号和激活状态查询人员（sso页面）
	 * @param telephone
	 * @param authStatus
	 * @return com.fosung.framework.web.http.ResponseParam
	 * @author fuhao
	 * @date 2022/2/15 16:50
	 **/
	@Override
	public Map<String, Object> queryUserByPhoneAndStatus(@PathVariable("source") String source,@RequestParam(value = "telephone", required = true) String telephone,@RequestParam(value = "authStatus", required = true) String authStatus){
		return null;
	}


	/**
	 * 描述: 根据组织编码集合和激活状态查询人员（sso页面）
	 * @param orgIds
	 * @param authStatus
	 * @return com.fosung.framework.web.http.ResponseParam
	 * @author fuhao
	 * @date 2022/2/15 16:50
	 **/
	@Override
	public List queryUserByOrgsAndStatus(@PathVariable("source") String source,@RequestParam(value = "orgIds", required = true) List<Long> orgIds, @RequestParam(value = "authStatus", required = true) String authStatus){
		return null;
	}

}

