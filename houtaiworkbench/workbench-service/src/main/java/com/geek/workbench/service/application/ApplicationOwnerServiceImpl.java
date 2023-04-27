package com.geek.workbench.service.application;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.fosung.framework.common.json.JsonMapper;
import com.fosung.framework.common.util.UtilString;
import com.geek.workbench.dao.application.ApplicationOwnerDao;
import com.geek.workbench.entity.application.ApplicationOwnerEntity;
import com.geek.workbench.util.CacheConstants;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
@Slf4j
@Service
public class ApplicationOwnerServiceImpl extends AppJPABaseDataServiceImpl<ApplicationOwnerEntity, ApplicationOwnerDao>
	implements ApplicationOwnerService{

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("terminalId","terminalId:EQ");
				put("userId","userId:EQ");
				put("appConfigId","appConfigId:EQ");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}

	/**
	 * 我的应用
	 * @param userId
	 * @param terminalId
	 * @return
	 */
	@Override
	public List<ApplicationOwnerEntity> getMyApp(String userId, Long terminalId) {
		List<ApplicationOwnerEntity> myAppList = null;
		String myAppJsons = stringRedisTemplate.opsForValue().get(getCacheKey(userId,terminalId));
		if(UtilString.isBlank(myAppJsons)){
			Map<String,Object> searchParam = Maps.newHashMap();
			searchParam.put("userId",userId);
			searchParam.put("terminalId",terminalId);
			myAppList = queryAll(searchParam);
			stringRedisTemplate.opsForValue().set(getCacheKey(userId,terminalId), JsonMapper.toJSONString(myAppList), CacheConstants.EXPIRES_HOURS, TimeUnit.HOURS);
		}else {
			myAppList = JsonMapper.parseArray(myAppJsons,ApplicationOwnerEntity.class);
			log.debug("selectAppIdByUserIdInCache 命中缓存: {}" , "user:"+userId+"terminal:"+terminalId) ;
		}
		return myAppList;
	}

	/**
	 * 移除用户缓存
	 * @param userId
	 * @param terminalId
	 */
	@Override
	public void deleteMyAppCache(String userId, Long terminalId){
		stringRedisTemplate.delete(getCacheKey(userId,terminalId));
	}

	public String getCacheKey(String userId,Long terminalId){
		return CacheConstants.APP_CACHE_PREFIX+"user:"+userId+"terminal:"+terminalId;
	}

	@Override
	public List queryMyApp(Long terminalId, Long userId) {
		return this.entityDao.queryMyApp(terminalId,userId);
	}
}
