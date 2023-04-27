package com.geek.workbench.service.config;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.exception.AppException;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import com.geek.workbench.common.MessageContent;
import com.geek.workbench.dao.config.ConfigManageDao;
import com.geek.workbench.dto.other.ConfigCertificationUrlAndParam;
import com.geek.workbench.dto.other.UrlAndParam;
import com.geek.workbench.entity.config.ConfigManageEntity;
import com.geek.workbench.entity.config.TerminalConfigManageEntity;
import com.geek.workbench.util.CacheUtil;
import com.geek.workbench.dict.ConfigType;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * 描述:  配置管理服务实现类
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Service
public class ConfigManageServiceImpl extends AppJPABaseDataServiceImpl<ConfigManageEntity, ConfigManageDao>
	implements ConfigManageService {


	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("id","id:EQ");
				put("configName","configName:LIKE");
				put("configType","configType:EQ");
				put("configPlatform","configPlatform:EQ");
				put("startTime", "createDatetime:GTEDATE");
				put("endTime", "createDatetime:LTEDATE");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}


	/**
	 * 描述:  保存信息
	 *
	 * @param configManageEntity
	 * @createDate: 2021/10/28 18:16
	 * @author: gaojian
	 * @modify:
	 * @return: void
	 */
	@Override
	public void saveInfo(ConfigManageEntity configManageEntity) {

		// 1. 查询是否已有此配置
		Map<String,Object> searchParams = new HashMap<>(8);
		searchParams.put("configType",configManageEntity.getConfigType());
		searchParams.put("configPlatform",configManageEntity.getConfigPlatform());
		List<ConfigManageEntity> list = queryAll(searchParams);
		if( list != null && list.size() > 0 ){
			throw new AppException(MessageContent.CONFIG_INFO_IS_EXIST);
		}

		// 2. 保存配置信息
		save(configManageEntity);
	}

	/**
	 * 描述:  修改信息
	 *
	 * @param configManageEntity
	 * @createDate: 2021/11/4 20:21
	 * @author: gaojian
	 * @modify:
	 * @return: void
	 */
	@Override
	public void updateInfo(ConfigManageEntity configManageEntity) {

		// 1. 由请求参数中获取需要更新的字段
		Set<String> updateFields = UtilDTO.toDTOExcludeFields(configManageEntity, Arrays.asList("id","configType")).keySet();
		ConfigManageEntity result = get(configManageEntity.getId());
		if (!StringUtils.equals(result.getConfigPlatform(),configManageEntity.getConfigPlatform())){

			// 2. 查询是否已有此配置
			Map<String,Object> searchParams = new HashMap<>(8);
			searchParams.put("configType",configManageEntity.getConfigType());
			searchParams.put("configPlatform",configManageEntity.getConfigPlatform());
			List<ConfigManageEntity> list = queryAll(searchParams);
			if( list != null && list.size() > 0 ){
				throw new AppException(MessageContent.CONFIG_INFO_IS_EXIST);
			}
		}

		update(configManageEntity,updateFields);

		// 3. 修改绑定的此配置终端的配置信息
		if (!StringUtils.equals(result.getConfigPlatform(),configManageEntity.getConfigPlatform())
		|| !StringUtils.equals(result.getConfigPlatformName(),configManageEntity.getConfigPlatformName())){
			TerminalConfigManageEntity terminalConfigManageEntity = new TerminalConfigManageEntity();
			terminalConfigManageEntity.setConfigPlatform(configManageEntity.getConfigPlatform());
			terminalConfigManageEntity.setConfigPlatformName(configManageEntity.getConfigName());
			terminalConfigManageEntity.setConfigType(configManageEntity.getConfigType());
			terminalConfigManageEntity.setOldConfigPlatform(result.getConfigPlatform());
			entityDao.updateTerminalRunConfig(terminalConfigManageEntity);
		}

	}

	/**
	 * 描述:  删除信息
	 *
	 * @param list
	 * @createDate: 2021/11/15 11:45
	 * @author: gaojian
	 * @modify:
	 * @return: void
	 */
	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void deleteInfo(List<AppBaseIdParam> list) {

		// 1. 循环
		list.forEach(appBaseIdParam -> {

			// 2. 修改绑定的运行配置为空
			ConfigManageEntity result = get(appBaseIdParam.getId());
			TerminalConfigManageEntity terminalConfigManageEntity = new TerminalConfigManageEntity();
			terminalConfigManageEntity.setConfigPlatformName(null);
			terminalConfigManageEntity.setConfigPlatform(null);
			terminalConfigManageEntity.setConfigType(result.getConfigType());
			terminalConfigManageEntity.setOldConfigPlatform(result.getConfigPlatform());
			entityDao.deleteTerminalRunConfig(terminalConfigManageEntity);

			// 3. 删除配合信息
			delete(appBaseIdParam.getId());
		});

	}

	/**
	 *查询终端下
	 *
	 * @param terminalId
	 * @author liuke
	 * @date 2021/10/29 15:55
	 * @return java.util.List<com.fosung.workbench.entity.config.ConfigManageEntity>
	 */
	@Override
	public List<ConfigManageEntity> queryByTerminalId(Long terminalId){
		return entityDao.queryByTerminalId(terminalId);
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
					List<ConfigManageEntity> list = queryByTerminalId(key);
					ConfigCertificationUrlAndParam configCertificationUrlAndParam = new ConfigCertificationUrlAndParam();
					for (ConfigManageEntity configManageEntity : list) {
						switch (configManageEntity.getConfigType()){
							case oss:
								configCertificationUrlAndParam.setOss(new UrlAndParam(configManageEntity.getConfigPlatform()));
								break;
							case resource:
								configCertificationUrlAndParam.setResource(new UrlAndParam(configManageEntity.getConfigPlatform()));
								break;
							case authorized:
								configCertificationUrlAndParam.setAuthorized(new UrlAndParam(configManageEntity.getConfigPlatform()));
								break;
							default:
								break;
						}
					}
					return configCertificationUrlAndParam;
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
}
