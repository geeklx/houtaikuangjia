package com.geek.system.pbs.api.oldapi;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fosung.framework.web.http.ResponseParam;

@FeignClient(name = "CloudSysApi", url = "${app.pbs.client.url}")
@RequestMapping("/{source}/api/cloud/app")
public interface CloudSysApi {

	/**
	 * @param @param  code
	 * @param @return
	 * @return List<Map>
	 * @throws
	 * @Title: queryAppDictionary
	 * @Description: 查询字典项(包括枚举类型和数据库字典项)
	 */
	@PostMapping(value = "/dictionary/runtime")
	List<Map<String, String>> queryAppDictionary(@PathVariable("source") String source,
			@RequestParam(name = "code") String code);

	/**
	 * 灯塔字典项
	 *
	 * @param dictType
	 * @param code
	 * @return
	 */
	@PostMapping(value = "/dt/dict")
	Map<String, Object> dtDict(@PathVariable("source") String source, @RequestParam("dictType") String dictType,
			@RequestParam("code") String code);

	@PostMapping(value = "/dictionary")
	Map<String, Object> queryByCode(@PathVariable("source") String source, @RequestParam("code") String code);

	@PostMapping("/dictionary/condition")
	List<Map<String, String>> queryAppDictionExcludeAndInclude(@PathVariable("source") String source,
			@RequestParam("code") String code, @RequestParam("excludeCodes") String excludeCodes,
			@RequestParam("includeCodes") String includeCodes);

	@PostMapping("/dictionary/path")
	ResponseParam queryLikePath(@PathVariable("source") String source, @RequestParam("code") String code);
	/* @PostMapping("/post")
	ResponseParam queryAllPost();*/

	/**
	 *获取系统管理字典数据
	 *
	 * @param source
	 * @param dictType
	 * @date 2021/4/23 9:47
	 * @return com.fosung.framework.web.http.ResponseParam
	 */
	@PostMapping("/querysystemdict")
	ResponseParam querySysTemDict(@PathVariable("source") String source,@RequestParam("dictType") String dictType);

	@PostMapping("/query")
	ResponseParam queryUserAppList(@PathVariable("source") String source, @RequestParam("userId") Long userId);

	/**
	 *获取app下所有资源
	 *
	 * @param source
	 * @param appId
	 * @author liuke
	 * @date 2021/4/30 9:27
	 * @return com.fosung.framework.web.http.ResponseParam
	 */
	@PostMapping("queryresourcebyapp")
	ResponseParam queryResourceByApp(@PathVariable("source") String source,@RequestParam("appId")Long appId);


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
	ResponseParam queryResourceByAppCodeAndUserId(@PathVariable("source") String source,@RequestParam("appCode")String appCode,@RequestParam("userId") Long userId);

	@PostMapping("/getPostByCode")
	Map<String, Object> getPostByCode(@PathVariable("source") String source, @RequestParam("code") String code);

	@PostMapping("/getPost")
	Map<String, Object> getPost(@PathVariable("source") String source, @RequestParam("postId") Long postId);

	@PostMapping("/queryByIdentityId")
	List<Map> queryByIdentityId(@PathVariable("source") String source, @RequestParam("identityId") Long identityId);

	@PostMapping("/queryAllPost")
	List<Map> queryAllPost(@PathVariable("source") String source);

	@PostMapping("/queryAllIdentity")
	List<Map> queryAllIdentity(@PathVariable("source") String source);

	@PostMapping("/queryPostByType")
	List<Map> queryPostByType(@PathVariable("source") String source, @RequestParam("type") String type);
}
