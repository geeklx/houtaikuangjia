package com.geek.system.pbs.api.oldapi;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;

public abstract class AbstractCloudSysApi extends AppIBaseController implements CloudSysApi {

	@Override
	public List<Map<String, String>> queryAppDictionary(@PathVariable("source") String source,
			@RequestParam(name = "code") String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> dtDict(@PathVariable("source") String source, @RequestParam("dictType") String dictType,
			@RequestParam("code") String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> queryByCode(@PathVariable("source") String source, @RequestParam("code") String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, String>> queryAppDictionExcludeAndInclude(@PathVariable("source") String source,
			@RequestParam("code") String code, @RequestParam("excludeCodes") String excludeCodes,
			@RequestParam("includeCodes") String includeCodes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseParam queryUserAppList(@PathVariable("source") String source, @RequestParam("userId") Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseParam queryLikePath(@PathVariable("source") String source, @RequestParam("code") String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseParam querySysTemDict(@PathVariable("source") String source,@RequestParam("dictType") String dictType){
		return null;
	}
	@Override
	public ResponseParam queryResourceByApp(String source, Long appId) {
		return null;
	}

	@Override
	public ResponseParam queryResourceByAppCodeAndUserId(@PathVariable("source") String source,@RequestParam("appCode")String appCode,@RequestParam("userId") Long userId){
		return null;
	}

	@Override
	public Map<String, Object> getPostByCode(@PathVariable("source") String source, @RequestParam("code") String code) {
		return null;
	}

	@Override
	public Map<String, Object> getPost(@PathVariable("source") String source, @RequestParam("postId") Long postId) {
		return null;
	}

	@Override
	public List<Map> queryByIdentityId(@PathVariable("source") String source, @RequestParam("identityId") Long identityId) {
		return null;
	}

	@Override
	public List<Map> queryAllPost(@PathVariable("source") String source) {
		return null;
	}

	@Override
	public List<Map> queryAllIdentity(@PathVariable("source") String source) {
		return null;
	}

	@Override
	public List<Map> queryPostByType(@PathVariable("source") String source, @RequestParam("type") String type) {
		return null;
	}
}
