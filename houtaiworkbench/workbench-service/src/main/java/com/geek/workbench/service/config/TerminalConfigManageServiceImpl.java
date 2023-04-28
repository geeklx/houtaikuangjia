package com.geek.workbench.service.config;

import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import com.geek.workbench.dao.config.TerminalConfigManageDao;
import com.geek.workbench.dict.ConfigType;
import com.geek.workbench.dict.StatusType;
import com.geek.workbench.dto.other.ConfigCertificationUrlAndParam;
import com.geek.workbench.dto.other.UrlAndParam;
import com.geek.workbench.dto.third.party.TerminalSearchConfigDto;
import com.geek.workbench.entity.config.TerminalConfigManageEntity;
import com.geek.workbench.entity.terminal.TerminalThirdPartyConfigEntity;
import com.geek.workbench.service.terminal.TerminalThirdPartyConfigService;
import com.geek.workbench.util.CacheUtil;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 描述:  终端接口配置服务实现类
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Service
public class TerminalConfigManageServiceImpl extends AppJPABaseDataServiceImpl<TerminalConfigManageEntity, TerminalConfigManageDao>
	implements TerminalConfigManageService {

	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("terminalId","terminalId:EQ");
				put("configType","configType:EQ");
				put("configPlatform","configPlatform:EQ");
	       }
	};

	/**
	 * 描述:  终端第三方配置服务
	 * @createDate: 2022/3/1 15:05
	 * @author: gaojian
	 */
	@Autowired
	private TerminalThirdPartyConfigService terminalThirdPartyConfigService;
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}

	/**
	 * 描述:  保存终端默认的管理信息
	 *
	 * @param terminalId
	 * @createDate: 2021/11/2 17:50
	 * @author: gaojian
	 * @modify:
	 * @return: void
	 */
	@Override
	public void saveDefault(Long terminalId) {

		// 1. 初始化认证信息
		List<TerminalConfigManageEntity> list = new ArrayList<>();
		ConfigType[] configTypes = ConfigType.values();
		for ( int i = 0 ; i < configTypes.length ; i++ ){
			ConfigType item = configTypes[i];
			TerminalConfigManageEntity terminalConfigManageEntity = new TerminalConfigManageEntity();
			terminalConfigManageEntity.setConfigType(item);
			terminalConfigManageEntity.setConfigTypeName(item.getRemark());
			terminalConfigManageEntity.setRemark("初始化");
			terminalConfigManageEntity.setTerminalId(terminalId);
			list.add(terminalConfigManageEntity);
		}

		// 2. 批量保存
		saveBatch(list);

		// 3. 保存统一搜索初始化配置
		TerminalThirdPartyConfigEntity terminalThirdPartyConfigEntity = new TerminalThirdPartyConfigEntity();
		terminalThirdPartyConfigEntity.setTerminalId(terminalId);
		terminalThirdPartyConfigEntity.setConfigType(ConfigType.unisearch);
		terminalThirdPartyConfigEntity.setStatusType(StatusType.DISABLE);
		TerminalSearchConfigDto searchConfigDto = new TerminalSearchConfigDto();
		terminalThirdPartyConfigEntity.setConfigInfo(JSONObject.toJSONString(searchConfigDto));
		terminalThirdPartyConfigService.save(terminalThirdPartyConfigEntity);
	}

	/**
	 *用户接口配置信息缓存
	 *
	 * @author liuke
	 * @date 2021/10/15 14:56
	 * @return
	 */
	public LoadingCache<Long, ConfigCertificationUrlAndParam> terminalBasicCache = CacheUtil.getInstance().build(
			new CacheLoader<Long,ConfigCertificationUrlAndParam>() {
				@Override
				public ConfigCertificationUrlAndParam load(Long key) throws Exception {
					//todo 考虑缓存  if else取配置
					Map<String,Object> searchParam = Maps.newHashMap();
					searchParam.put("terminalId",key);
					List<TerminalConfigManageEntity> terminalConfigManageEntities = queryAll(searchParam);
					ConfigCertificationUrlAndParam configCertificationUrlAndParam = new ConfigCertificationUrlAndParam();
					terminalConfigManageEntities.stream().forEach(terminalConfigManageEntity -> {
						if(terminalConfigManageEntity.getConfigType().equals(ConfigType.authorized)){
							configCertificationUrlAndParam.setAuthorized(new UrlAndParam(terminalConfigManageEntity.getConfigPlatform()));
						}else if(terminalConfigManageEntity.getConfigType().equals(ConfigType.resource)){
							configCertificationUrlAndParam.setResource(new UrlAndParam(terminalConfigManageEntity.getConfigPlatform()));
						}else if(terminalConfigManageEntity.getConfigType().equals(ConfigType.oss)){
							configCertificationUrlAndParam.setOss(new UrlAndParam(terminalConfigManageEntity.getConfigPlatform()));
						}else if(terminalConfigManageEntity.getConfigType().equals(ConfigType.ums)){
							configCertificationUrlAndParam.setUms(new UrlAndParam(terminalConfigManageEntity.getConfigPlatform()));
						}else if(terminalConfigManageEntity.getConfigType().equals(ConfigType.unisearch)){
							configCertificationUrlAndParam.setUnisearch(new UrlAndParam(terminalConfigManageEntity.getConfigPlatform()));
						}else if(terminalConfigManageEntity.getConfigType().equals(ConfigType.immsg)){
							configCertificationUrlAndParam.setImmsg(new UrlAndParam(terminalConfigManageEntity.getConfigPlatform()));
						}
					});
					return configCertificationUrlAndParam;
				}


			}
	);

	/**
	 * 在缓存取出认证配置
	 * @param id
	 * @author liuke
	 * @date 2021/10/18 10:05d
	 * @return java.util.List<com.geek.workbench.entity.terminal.TerminalApplicationConfigEntity>
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

}
