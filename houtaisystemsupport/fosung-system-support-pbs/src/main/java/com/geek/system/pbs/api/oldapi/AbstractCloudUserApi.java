package com.geek.system.pbs.api.oldapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.geek.system.pbs.dto.*;
import com.geek.system.pbs.dto.support.dt.CloudPostDto;
import com.geek.system.pbs.dto.support.dt.UserTranslationDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;

public abstract class AbstractCloudUserApi extends AppIBaseController implements CloudUserApi {

	@Override
	public CloudUser queryByUserId(@PathVariable("source") String source,
								   @RequestParam(name = "userid") String userid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CloudUser queryByIdentifyId(@PathVariable("source") String source,
									   @RequestParam(name = "identifyId") String identifyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseParam queryByUserName(@PathVariable("source") String source,
										 @RequestParam(name = "userName") String userName){
		return null;
	}

	@Override
	public List<CloudUser> queryOrgUsers(@PathVariable("source") String source,
										 @RequestParam(name = "orgId") String orgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> queryByPhoneAndIDCard(@PathVariable("source") String source,
													 @RequestParam("realName") String realName, @RequestParam("idCard") String idCard) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<CloudUser> queryUserByOrd(@PathVariable("source") String source, @RequestParam(name = "orgId")String orgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseParam save(@PathVariable("source") String Source, @RequestBody UserTranslationDto userDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseParam saveResourceUser(@PathVariable("source") String source, @RequestBody CloudUserDto cloudUserDto) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ResponseParam delete(@PathVariable("source") String Source, @RequestBody UserTranslationDto userDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseParam updateStatus(@PathVariable("source") String Source, @RequestParam("idCard") String idCard,
									  @RequestParam("status") String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseParam queryPage(@PathVariable("source") String source, @RequestBody UserDTO user) {
		return null;
	}

	@Override
	public ResponseParam queryByOrgAndRole(@PathVariable("source") String source, @RequestBody UserDTO user) {

		return null;
	}

	@Override
	public ResponseParam queryUserByDTUserId(@PathVariable("source") String source, @RequestBody List<String> ids) {

		return null;
	}

	@Override
	public List<CloudUser> queryUser(@PathVariable("source") String source, @RequestBody SysUserDto sysUserDto) {

		return null;
	}
	@Override
	public ResponseParam savePost(@PathVariable("source") String source, @RequestBody UserPostDto userPostDto) {
		return null;
	}

	@Override
	public ResponseParam deletePost(@PathVariable("source") String source, @RequestBody UserPostDto userPostDto) {
		return null;
	}

	@Override
	public ResponseParam batchOperatePost(@PathVariable("source") String source, @RequestBody UserPostDto userPostDto) {
		return null;
	}

	@Override
	public ResponseParam queryByOrgAndPost(@PathVariable("source") String source, @RequestBody SysUserDto sysUserDto) {
		return null;
	}

	@Override
	public ResponseParam queryPageUser(@PathVariable("source") String source, @RequestBody SysUserDto sysUserDto) {
		return null;
	}

	/**
	 * 分页查询组织机构下的用户
	 * @author lwq
	 * @param source
	 * @param user   组织ID\组织code\分页数据
	 * @return
	 */
	@Override
	public List<CloudUser> queryPageUserByOrg(@PathVariable("source") String source, @RequestBody  UserDTO user) {
		return null;
	}


	/**
	 * 根据人员ID查询人员详情
	 * @param source
	 * @param ids
	 * @return
	 */
	@Override
	public List<CloudUser> queryByUserIds(@PathVariable("source") String source, @RequestBody ArrayList<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * 查询组织机构下的用户数量
	 *
	 * @param source
	 * @param user   :  orgId   hasChild  excludeUserId
	 * @return
	 */
	@Override
	public Integer queryCountOrgUsers(String source, UserDTO user) {
		return null;
	}

	/**
	 * 分页查询组织下任职和所属的人员
	 * @param source
	 * @param sysUserDto
	 * @return
	 */
	@Override
	public ResponseParam queryPageUserByOrgAndPost(@PathVariable("source") String source, @RequestBody SysUserDto sysUserDto) {
		return null;
	}

	@Override
	public List<CloudUser> queryUserByPost(@PathVariable("source") String source, @RequestParam("orgIds") Long[] orgIds, @RequestParam("postCode") String postCode) {
		return null;
	}

	@Override
	public ResponseParam getUserDetailByUserId(@PathVariable("source") String source,@RequestParam("userId") Long userId){
		return  null;
	}

	@Override
	public ResponseParam getPlateFormAppsByUserId(@PathVariable String source,@RequestParam("userId")Long userId){
		return null;
	}

	@Override
	public ResponseParam getUsersByAppId(@PathVariable String source,@RequestParam("appId") Long appId){
		return null;
	}

	@Override
	public ResponseParam queryResourceByAppCodeAndUserId(@PathVariable("source") String source, @RequestParam("appCode")String appCode, @RequestParam("userId") String userId){
		return null;
	}


	@Override
	public ResponseParam queryResourceByAppCodeAndUserIdAndRole(@PathVariable("source") String source,@RequestParam("appCode")String appCode,@RequestParam("userId") String userId,@RequestParam("roleId") String roleId){
		return null;
	}

	@Override
	public List<CloudPostDto> queryPostByOrg(@PathVariable("source") String source,
											 @RequestParam("orgCode") String orgCode,
											 @RequestParam(value = "levelTypes", required = false) String levelTypes) {
		return null;
	}
}

