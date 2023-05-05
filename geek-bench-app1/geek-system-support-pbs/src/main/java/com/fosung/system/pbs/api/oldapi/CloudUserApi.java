package com.fosung.system.pbs.api.oldapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fosung.system.pbs.dto.*;
import com.fosung.system.pbs.dto.support.dt.CloudPostDto;
import com.fosung.system.pbs.dto.support.dt.UserTranslationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fosung.framework.web.http.ResponseParam;
/**
 *
 * 用户对外接口类
 *
 * @author liuke
 * @date  2022/2/23 15:43
 * @version
*/
@FeignClient(name = "CloudUserApi", url = "${app.pbs.client.url}")
@RequestMapping("/{source}/api/cloud/user")
public interface CloudUserApi {

	/**
	 * 根据用户id查询用户信息
	 *
	 * @param source
	 * @param userid
	 * @return
	 */
	@PostMapping(value = "/get")
	CloudUser queryByUserId(@PathVariable("source") String source, @RequestParam(name = "userid") String userid);

	/**
	 * 根据身份证号查询用户
	 *
	 * @param identifyId
	 * @return
	 */
	@PostMapping(value = "/get/identifyId")
	CloudUser queryByIdentifyId(@PathVariable("source") String source,
								@RequestParam(name = "identifyId") String identifyId);

	/**
	 * 根据用户名查询用户
	 *
	 * @param userName
	 * @return
	 */
	@PostMapping(value = "/get/querybyusername")
	ResponseParam queryByUserName(@PathVariable("source") String source,
								  @RequestParam(name = "userName") String userName);


	/**
	 * 查询组织机构下的用户
	 *
	 * @param source
	 * @param orgId
	 * @return
	 */
	@PostMapping(value = "/list")
	List<CloudUser> queryOrgUsers(@PathVariable("source") String source, @RequestParam(name = "orgId") String orgId);


	/**
	 * 分页查询组织机构下的用户(返回值不带有分页信息,只需要纯数据集合)
	 * @author lwq
	 * @param source
	 * @param user  组织ID\组织code\分页数据
	 * @return
	 */
	@PostMapping(value = "/queryPageUserByOrg")
	List<CloudUser> queryPageUserByOrg(@PathVariable("source") String source, @RequestBody UserDTO user);



	/**
	 * 查询组织机构下的用户数量
	 *
	 * @param source
	 * @param user:  orgId   hasChild  excludeUserId
	 * @return
	 */
	@PostMapping(value = "/userCount")
	Integer queryCountOrgUsers(@PathVariable("source") String source, @RequestBody UserDTO user);

	/**
	 * 根据姓名和身份证号查询认证用户信息
	 *
	 * @param realName
	 * @param idCard
	 * @return
	 */
	@PostMapping("/query")
	Map<String, Object> queryByPhoneAndIDCard(@PathVariable("source") String source,
											  @RequestParam("realName") String realName, @RequestParam("idCard") String idCard);

	/**
	 * 批量查询用户
	 *
	 * @param ids
	 * @return
	 */
	@PostMapping(value = "/query/ids", produces = "application/json")
	List<CloudUser> queryByUserIds(@PathVariable("source") String source, @RequestBody ArrayList<String> ids);

	/**
	 * 根据组织批量查询本级及直属子级的所有用户
	 *
	 * @param orgId  组织ID
	 * @return
	 */
	@RequestMapping(value = "/queryUserByOrd")
	List<CloudUser> queryUserByOrd(@PathVariable("source")  String source,
								   @RequestParam(name = "orgId")String orgId);

	/**
	 * 处理用户新增工单
	 *
	 * @param userDto
	 * @return
	 */
	@PostMapping(value = "/save")
	ResponseParam save(@PathVariable("source") String source, @RequestBody UserTranslationDto userDto);

	/**
	 * 删除用户
	 *
	 * @param userDto
	 * @return
	 */
	@PostMapping("/delete")
	ResponseParam delete(@PathVariable("source") String source, @RequestBody UserTranslationDto userDto);

	/**
	 * 更新用户状态
	 *
	 * @param idCard
	 * @param status
	 * @return
	 */
	@PostMapping(value = "/update/status")
	ResponseParam updateStatus(@PathVariable("source") String source, @RequestParam("idCard") String idCard,
							   @RequestParam("status") String status);

	/**
	 * 根据组织、角色、人员名称 分页查询
	 * @param source
	 * @param user
	 * @return
	 */
	@PostMapping("/page")
	ResponseParam queryPage(@PathVariable("source") String source, @RequestBody UserDTO user);

	/**
	 * 根据组织和角色查询上级组织对应角色的用户;用于工作流程节点动态获取节点所属用户
	 * @param user 当前组织id，roleIds[] 角色ID
	 * @return
	 */
	@PostMapping("/org/role")
	ResponseParam queryByOrgAndRole(@PathVariable("source") String source, @RequestBody UserDTO user);

	/**
	 * 保存用户信息--全国资源库使用
	 *
	 * @param cloudUserDto
	 * @return
	 */
	@PostMapping("/saveResourceUser")
	ResponseParam saveResourceUser(@PathVariable("source") String source, @RequestBody CloudUserDto cloudUserDto);

	/**
	 * 根据灯塔userId查询济南用户
	 * @param source
	 * @param ids 灯塔党组织下的党员id
	 * @return
	 */
	@PostMapping("/queryDTUserByOutId")
	ResponseParam queryUserByDTUserId(@PathVariable("source") String source, @RequestBody List<String> ids);

	/**
	 * 查询用户
	 * @param source
	 * @param sysUserDto 过滤条件
	 * @return
	 */
	@PostMapping("/queryUser")
	List<CloudUser> queryUser(@PathVariable("source") String source, @RequestBody SysUserDto sysUserDto);

	/**
	 * 添加任职
	 * @param source
	 * @param userPostDto
	 * @return
	 */
	@PostMapping(value = "/savePost")
	ResponseParam savePost(@PathVariable("source") String source, @RequestBody UserPostDto userPostDto);

	/**
	 * 删除任职
	 * @param source
	 * @param userPostDto
	 * @return
	 */
	@PostMapping("/deletePost")
	ResponseParam deletePost(@PathVariable("source") String source, @RequestBody UserPostDto userPostDto);

	/**
	 * 批量操作职务
	 * @param source
	 * @param userPostDto
	 * @return
	 */
	@PostMapping(value = "/batchOperatePost")
	ResponseParam batchOperatePost(@PathVariable("source") String source, @RequestBody UserPostDto userPostDto);

	/**
	 * 根据组织和职务分页查询人员
	 * @param source
	 * @param sysUserDto
	 * @return
	 */
	@PostMapping("/queryByOrgAndPost")
	ResponseParam queryByOrgAndPost(@PathVariable("source") String source, @RequestBody SysUserDto sysUserDto);

	/**
	 * 分页查询用户
	 * @param source
	 * @param sysUserDto 过滤条件
	 * @return
	 */
	@PostMapping("/queryPageUser")
	ResponseParam queryPageUser(@PathVariable("source") String source, @RequestBody SysUserDto sysUserDto);

	/**
	 * 分页查询组织下任职和所属的人员
	 * @param source
	 * @param sysUserDto
	 * @return
	 */
	@PostMapping("/queryPageUserByOrgAndPost")
	ResponseParam queryPageUserByOrgAndPost(@PathVariable("source") String source, @RequestBody SysUserDto sysUserDto);

	/**
	 * 查询组织下的任职人员
	 * @param source
	 * @param orgIds
	 * @param postCode 可为空
	 * @return
	 */
	@PostMapping("/queryUserByPost")
	List<CloudUser> queryUserByPost(@PathVariable("source") String source,
									@RequestParam("orgIds") Long[] orgIds,
									@RequestParam("postCode") String postCode);

	/**
	 *根据用户id查询用户所有基本信息（用户基本信息，角色信息，资源信息，岗位信息，组织信息）
	 *
	 * @param userId
	 * @author liuke
	 * @date 2021/4/23 9:27
	 * @return com.fosung.framework.web.http.ResponseParam
	 */
	@PostMapping("/getuserdetails")
	ResponseParam getUserDetailByUserId(@PathVariable("source") String source,@RequestParam("userId") Long userId);

	/**
	 *根据用户id查询用户有的系统app
	 *
	 * @param source
	 * @author liuke
	 * @date 2021/4/27 14:40
	 * @return com.fosung.framework.web.http.ResponseParam
	 */
	@PostMapping("/getplateformappsbyuserid")
	ResponseParam getPlateFormAppsByUserId(@PathVariable("source") String source,@RequestParam("userId") Long userId);

	/**
	 *查询app下所有用户
	 *
	 * @author liuke
	 * @date 2021/4/27 16:46
	 * @return com.fosung.framework.web.http.ResponseParam
	 */
	@PostMapping("getusersbyappid")
	ResponseParam getUsersByAppId(@PathVariable("source") String source,@RequestParam("appId") Long appId);

	/**
	 *根据用户名 应用编码获取资源
	 *
	 * @param source
	 * @param appCode
	 * @param userId
	 * @author liuke
	 * @date 2021/4/30 9:27
	 * @return com.fosung.framework.web.http.ResponseParam
	 */
	@PostMapping("queryResourceByAppCodeAndUserId")
	ResponseParam queryResourceByAppCodeAndUserId(@PathVariable("source") String source, @RequestParam("appCode")String appCode, @RequestParam("userId") String userId);

	/**
	 *根据用户名角色 应用编码获取资源
	 *
	 * @param source
	 * @param appCode
	 * @param userId
	 * @author liuke
	 * @date 2021/4/30 9:27
	 * @return com.fosung.framework.web.http.ResponseParam
	 */
	@PostMapping("queryResourceByAppCodeAndUserIdAndRole")
	ResponseParam queryResourceByAppCodeAndUserIdAndRole(@PathVariable("source") String source,@RequestParam("appCode")String appCode,@RequestParam("userId") String userId,@RequestParam("roleId") String roleId);

	/**
	 * 根据组织查询本级及下级任职相关信息
	 * @param orgCode
	 * @return
	 */
	@PostMapping("/queryPostByOrg")
	List<CloudPostDto> queryPostByOrg(@PathVariable("source") String source,
									  @RequestParam("orgCode") String orgCode,
									  @RequestParam(value = "levelTypes", required = false) String levelTypes);
}

