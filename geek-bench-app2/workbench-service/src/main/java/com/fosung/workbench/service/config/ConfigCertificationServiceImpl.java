package com.fosung.workbench.service.config;

import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import com.fosung.workbench.dao.config.ConfigCertificationDao;
import com.fosung.workbench.dto.other.ConfigCertificationUrlAndParam;
import com.fosung.workbench.dto.other.UrlAndParam;
import com.fosung.workbench.entity.config.ConfigCertification;
import com.fosung.workbench.util.CacheUtil;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 描述:  用户接口配置服务实现类
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Service
public class ConfigCertificationServiceImpl extends AppJPABaseDataServiceImpl<ConfigCertification, ConfigCertificationDao>
	implements ConfigCertificationService {


	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("id","id:EQ");
				put("authorizedName","authorizedName:LIKE");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}

	/**
	 *用户接口配置信息缓存
	 *
	 * @author liuke
	 * @date 2021/10/15 14:56
	 * @return
	 */
	public LoadingCache<Long,ConfigCertificationUrlAndParam> terminalBasicCache = CacheUtil.getInstance().build(
			new CacheLoader<Long,ConfigCertificationUrlAndParam>() {
				@Override
				public ConfigCertificationUrlAndParam load(Long key) throws Exception {
					ConfigCertification configCertification = get(key);
					ConfigCertificationUrlAndParam configCertificationUrlAndParam = new ConfigCertificationUrlAndParam();
					//认证服务
					setServer(configCertification,configCertificationUrlAndParam);
					//资源服务
					setResource(configCertification,configCertificationUrlAndParam);
					//统一存储
					setOss(configCertification,configCertificationUrlAndParam);
					return configCertificationUrlAndParam;
				}
				//认证服务
				public void setServer(ConfigCertification configCertification,ConfigCertificationUrlAndParam configCertificationUrlAndParam){
					configCertificationUrlAndParam.setAuthorized(new UrlAndParam("/gwapi/workbenchserver"));
			}
				//资源服务
				public void setResource(ConfigCertification configCertification,ConfigCertificationUrlAndParam configCertificationUrlAndParam){
					configCertificationUrlAndParam.setResource(new UrlAndParam("/gwapi/workbenchserver"));
				}
				//存储
				public void setOss(ConfigCertification configCertification,ConfigCertificationUrlAndParam configCertificationUrlAndParam){
					configCertificationUrlAndParam.setOss(new UrlAndParam("/api/oss"));
				}

			}
	);

	 /**
	  * 在缓存取出认证配置
	 * @param id
	 * @author liuke
	 * @date 2021/10/18 10:05
	  * @return java.util.List<com.fosung.workbench.entity.terminal.TerminalApplicationConfigEntity>
	 */
	@Override
	public ConfigCertificationUrlAndParam getCacheTerminal(Long id){
		ConfigCertificationUrlAndParam configCertification = new ConfigCertificationUrlAndParam();
		try {
			configCertification = terminalBasicCache.get(id);

		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return configCertification;
	}

	/**
	 * 描述:  查询认证授权的信息
	 *
	 * @param id
	 * @createDate: 2021/10/26 9:45
	 * @author: gaojian
	 * @modify:
	 * @return: java.util.Map<java.lang.String, java.lang.Object>
	 */
	@Override
	public Map<String, Object> queryInfo(Long id) {


		return null;
	}

}
