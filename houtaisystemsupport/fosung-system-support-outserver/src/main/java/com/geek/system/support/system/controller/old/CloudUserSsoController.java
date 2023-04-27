package com.geek.system.support.system.controller.old;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.common.util.UtilString;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.system.support.system.dict.AuthStatus;
import com.geek.system.support.system.entity.sys.SysUserEntity;
import com.geek.system.support.system.entity.sys.SysUserPostScopeEntity;
import com.geek.system.support.system.service.mq.SysMQProducerService;
import com.geek.system.support.system.service.sys.SysUserPostScopeService;
import com.geek.system.support.system.service.sys.SysUserService;
import com.google.api.client.util.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/{source}/api/user")
public class CloudUserSsoController extends AppIBaseController{

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysMQProducerService sysMQProducerService;

	@Autowired
	private SysUserPostScopeService sysUserPostScopeService;

	/**
	 * 描述: 用户注册激活
	 * @param dto
	 * @return com.fosung.framework.web.http.ResponseParam
	 * @author fuhao
	 * @date 2022/2/15 16:38
	 **/
	@PostMapping(value = "/active")
	ResponseParam active(@PathVariable("source") String source,@RequestBody Map<String, Object> dto){
		if(UtilCollection.sizeIsEmpty(dto) || StringUtils.isBlank(source)){
			return ResponseParam.fail().message("参数不能为空");
		}
		// 通过用户名查询用户信息
		SysUserEntity sysUserEntity = sysUserService.get(Long.parseLong((String) dto.get("userId")));
		if(sysUserEntity == null){
			return ResponseParam.fail().message("用户不存在!");
		}
		// 激活用户
		sysUserEntity.setTelephone(dto.get("telephone") == null ? null : (String) dto.get("telephone"));
		sysUserEntity.setUserName(dto.get("telephone") == null ? null : (String) dto.get("telephone"));
		sysUserEntity.setPassword(dto.get("password") == null ? null : (String) dto.get("password"));
		sysUserEntity.setAuthStatus(AuthStatus.AUTH);
		sysUserEntity.setAuthTime(new Date());
		sysUserService.update(sysUserEntity,Arrays.asList("telephone","userName","password","authStatus","authTime"));
		// 发送mq消息
		Map<String,Object> searchParams = Maps.newHashMap();
		searchParams.put("userId",sysUserEntity.getId());
		sysUserEntity.setIdentityId("PEOPLE");
		List<SysUserPostScopeEntity> list = sysUserPostScopeService.queryAll(searchParams);
		if(!UtilCollection.sizeIsEmpty(list)){
			Set<String> code=list.stream().map(SysUserPostScopeEntity::getIdentify).filter(s -> UtilString.equals(s,"CADRE")).collect(Collectors.toSet());
			if(!UtilCollection.sizeIsEmpty(code)){
				sysUserEntity.setIdentityId("CADRE");
			}
		}
		sysMQProducerService.sendUserEditMessage(sysUserEntity,"active");
		return ResponseParam.success().message("激活成功!");
	}

	/**
	 * 描述: 忘记密码（sso页面）
	 * @param dto
	 * @return com.fosung.framework.web.http.ResponseParam
	 * @author fuhao
	 * @date 2022/2/15 16:40
	 **/
	@PostMapping("/reset/password")
	public ResponseParam resetPassword(@PathVariable("source") String source,@RequestBody Map<String, Object> dto){
		if(UtilCollection.sizeIsEmpty(dto)){
			return ResponseParam.fail().message("参数不能为空");
		}
		if(dto.get("telephone") != null){
			// 判断手机号是否存在
			HashMap<String, Object> userParams = Maps.newHashMap();
			userParams.put("projectCode",source);
			userParams.put("telephone",dto.get("telephone"));
			//userParams.put("del",false);
			List<SysUserEntity> sysUserEntities = sysUserService.queryAll(userParams);
			if (UtilCollection.sizeIsEmpty(sysUserEntities)) {
				return ResponseParam.fail().message("用户不存在!");
			}
			// 修改密码
			SysUserEntity sysUserEntity = sysUserEntities.get(0);
			sysUserEntity.setPassword((String) dto.get("password"));
			sysUserService.update(sysUserEntity, Arrays.asList("password"));
			return ResponseParam.updateSuccess();
		}
		return ResponseParam.updateFail();
	}

	/**
	 * 描述: 管理端修改密码（sso页面）
	 * @param dto
	 * @return com.fosung.framework.web.http.ResponseParam
	 * @author fuhao
	 * @date 2022/2/18 13:57
	 **/
	@PostMapping("/manage/reset/password")
	public ResponseParam manageResetPassword(@PathVariable("source") String source,@RequestBody Map<String, Object> dto){
		if(UtilCollection.sizeIsEmpty(dto)){
			return ResponseParam.fail().message("参数不能为空");
		}
		if(dto.get("userId") != null){
			SysUserEntity sysUserEntity = sysUserService.get(Long.parseLong((String) dto.get("userId")));
			if (sysUserEntity == null) {
				return ResponseParam.fail().message("用户不存在!");
			}
			sysUserEntity.setPassword((String) dto.get("password"));
			sysUserService.update(sysUserEntity, Arrays.asList("password"));
			return ResponseParam.updateSuccess();
		}
		return ResponseParam.updateFail();
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
	@PostMapping(value = "/update/phone/{phone}/{userId}/{code}")
	public ResponseParam updatePhone(@PathVariable("source") String source,
									 @PathVariable("phone") String phone,
									 @PathVariable("userId") Long userId,
									 @PathVariable("code") String code){
		SysUserEntity sysUserEntity = sysUserService.get(userId);
		if(sysUserEntity == null){
			return ResponseParam.fail().message("用户不存在!");
		}
		sysUserEntity.setTelephone(phone);
		sysUserEntity.setUserName(phone);
		sysUserService.update(sysUserEntity, Arrays.asList("telephone","userName"));
		//发送mq消息
		Map<String,Object> searchParams = Maps.newHashMap();
		searchParams.put("userId",sysUserEntity.getId());
		sysUserEntity.setIdentityId("PEOPLE");
		List<SysUserPostScopeEntity> list = sysUserPostScopeService.queryAll(searchParams);
		if(!UtilCollection.sizeIsEmpty(list)){
			Set<String> codes=list.stream().map(SysUserPostScopeEntity::getIdentify).filter(s -> UtilString.equals(s,"CADRE")).collect(Collectors.toSet());
			if(!UtilCollection.sizeIsEmpty(codes)){
				sysUserEntity.setIdentityId("CADRE");
			}
		}
		sysMQProducerService.sendUserEditMessage(sysUserEntity,"updatePhone");
		return ResponseParam.updateSuccess();
	}


	/**
	 * 描述: 根据手机号和激活状态查询人员（sso页面）
	 * @param telephone
	 * @param authStatus
	 * @return com.fosung.framework.web.http.ResponseParam
	 * @author fuhao
	 * @date 2022/2/15 16:50
	 **/
	@PostMapping(value = "/queryUserByPhoneAndStatus")
	public Map<String, Object> queryUserByPhoneAndStatus(@PathVariable("source") String source,@RequestParam(value = "telephone", required = true) String telephone,@RequestParam(value = "authStatus", required = true) String authStatus){
		HashMap<String, Object> userParams = Maps.newHashMap();
		userParams.put("projectCode",source);
		userParams.put("telephone",telephone);
		userParams.put("authStatus",authStatus);
		List<SysUserEntity> sysUserEntities = sysUserService.queryAll(userParams);
		if(UtilCollection.isNotEmpty(sysUserEntities)){
			SysUserEntity sysUserEntity = sysUserEntities.get(0);
			sysUserEntity.setHeadUrl(sysUserEntity.getHeadImgUrl());
			sysUserEntity.setContactDetails(sysUserEntity.getFixedTelephone());
			Map<String, Object> result = UtilDTO.toDTO(sysUserEntity,
					null, getDtoCallbackHandler());
			return result;
		}
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
	@PostMapping(value = "/queryUserByOrgsAndStatus")
	public List<Map<String, Object>> queryUserByOrgsAndStatus(@PathVariable("source") String source,@RequestParam(value = "orgIds", required = true) List<Long> orgIds, @RequestParam(value = "authStatus", required = true) String authStatus){
		HashMap<String, Object> userParams = Maps.newHashMap();
		userParams.put("projectCode",source);
		userParams.put("orgIds",orgIds);
		userParams.put("authStatus",authStatus);
		List<SysUserEntity> sysUserEntities = sysUserService.queryAll(userParams);
		sysUserEntities.forEach(sysUserEntity -> {
			sysUserEntity.setHeadUrl(sysUserEntity.getHeadImgUrl());
			sysUserEntity.setContactDetails(sysUserEntity.getFixedTelephone());
		});
		List<Map<String, Object>> result = UtilDTO.toDTO(sysUserEntities,
				null, getDtoCallbackHandler());
		return result;
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

