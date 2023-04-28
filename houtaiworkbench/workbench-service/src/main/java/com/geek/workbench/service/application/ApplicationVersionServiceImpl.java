package com.geek.workbench.service.application;

import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.exception.AppException;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import com.geek.workbench.common.AppMessageContent;
import com.geek.workbench.common.GlobalVariableKey;
import com.geek.workbench.common.MessageContent;
import com.geek.workbench.dao.application.ApplicationVersionDao;
import com.geek.workbench.dict.AuditStatusType;
import com.geek.workbench.dict.StatusType;
import com.geek.workbench.dict.TerminalType;
import com.geek.workbench.dto.application.ApplicationVersionDto;
import com.geek.workbench.dto.terminal.TerminalApplicationConfigDto;
import com.geek.workbench.entity.application.ApplicationBaseConfigEntity;
import com.geek.workbench.entity.application.ApplicationBasicEntity;
import com.geek.workbench.entity.application.ApplicationVersionEntity;
import com.geek.workbench.service.terminal.TerminalApplicationBindService;
import com.geek.workbench.service.terminal.TerminalApplicationConfigService;
import com.geek.workbench.util.CacheUtil;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * 描述:  应用IOS配置信息服务实现类
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Service
@Slf4j
public class ApplicationVersionServiceImpl extends AppJPABaseDataServiceImpl<ApplicationVersionEntity, ApplicationVersionDao>
	implements ApplicationVersionService {

	/**
	 * 描述:  注入应用基本信息服务
	 * @createDate: 2021/10/27 15:01
	 * @author: gaojian
	 */
	@Autowired
	private ApplicationBasicService applicationBasicService;

	/**
	 * 描述:  注入安卓应用配置信息服务
	 * @createDate: 2021/10/27 15:01
	 * @author: gaojian
	 */
	@Autowired
	private ApplicationConfigAndroidService applicationConfigAndroidService;

	/**
	 * 描述:  注入IOS应用配置信息服务
	 * @createDate: 2021/10/27 15:01
	 * @author: gaojian
	 */
	@Autowired
	private ApplicationConfigIosService applicationConfigIosService;

	/**
	 * 描述:  注入H5应用配置信息服务
	 * @createDate: 2021/10/27 15:01
	 * @author: gaojian
	 */
	@Autowired
	private ApplicationConfigHtmlService applicationConfigHtmlService;

	/**
	 * 描述:  注入终端绑定应用服务
	 * @createDate: 2021/10/27 15:01
	 * @author: gaojian
	 */
	@Autowired
	private TerminalApplicationBindService terminalApplicationBindService;

	/**
	 * 描述:  注入终端应用配置服务
	 * @createDate: 2021/10/27 15:01
	 * @author: gaojian
	 */
	@Autowired
	private TerminalApplicationConfigService terminalApplicationConfigService;

	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("appId","appId:EQ");
				put("appType","appType:EQ");
				put("versionName","versionName:LIKE");
				put("startTime","startTime:GTEDATE");
				put("endTime","startTime:LTEDATE");
				put("isNewVersion","isNewVersion:EQ");

			}
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}

	/**
	 * 版本升级
	 * @param applicationVersionEntity
	 */
	@Override
	public void upgrade(ApplicationVersionEntity applicationVersionEntity) {

		// 1. 修改不是最新版本
		entityDao.updateAllIsNotNewVersion(applicationVersionEntity);

		// 2. 判断versionName是否有重复的
		Map<String,Object> searchParams = new HashMap<>(8);
		searchParams.put(GlobalVariableKey.APP_ID,applicationVersionEntity.getAppId());
		searchParams.put(GlobalVariableKey.APP_TYPE,applicationVersionEntity.getAppType());
		searchParams.put("versionNum",applicationVersionEntity.getVersionNum());
		List<ApplicationVersionEntity> list = queryAll(searchParams);
		if( list != null && list.size() > 0 ){
			list.forEach( item -> {
				if(item.getVersionNum() != null && item.getVersionNum().equals(applicationVersionEntity.getVersionNum())){
					throw new AppException(MessageContent.VERSION_NAME_IS_EXIST);
				}
			});
		}

		// 3. 保存信息
		String userName = "";
		try {
			userName = appUserDetailsService.getAppUserDetails().getUsername();
		}catch (Exception e){
			log.error(e.getMessage());
		}
		applicationVersionEntity.setCreateUserName(userName);
		if( applicationVersionEntity.getVersionNum() == null ||applicationVersionEntity.getVersionNum() == 1){
			applicationVersionEntity.setRemark("初始版本");
		}
		if(StringUtils.isNotBlank(applicationVersionEntity.getRemark())){
			applicationVersionEntity.setRemark(applicationVersionEntity.getRemark());
		}
		applicationVersionEntity.setIsNewVersion(Boolean.TRUE);
		applicationVersionEntity.setCompatibleOldVersion(Boolean.TRUE);
		applicationVersionEntity.setDownloadNum(0);
		applicationVersionEntity.setAuditStatus(AuditStatusType.pass);
		applicationVersionEntity.setAuditRemark(AuditStatusType.pass.getRemark());
		save(applicationVersionEntity);
	}

	/**
	 * 描述:  查询应用最新版本号
	 *
	 * @param applicationVersionEntity
	 * @createDate: 2021/10/18 21:28
	 * @author: gaojian
	 * @modify:
	 * @return:
	 */
	@Override
	public Integer queryMaxVersionNum(ApplicationVersionEntity applicationVersionEntity) {

		// 1. 必要条件校验
		Assert.notNull(applicationVersionEntity.getAppType(), AppMessageContent.APP_TYPE_IS_NULL);
		Assert.notNull(applicationVersionEntity.getAppId(),AppMessageContent.APP_ID_IS_NULL);

		// 2. 判断应用id是否存在
		if(!applicationBasicService.isExist(applicationVersionEntity.getAppId())){
			throw new AppException(MessageContent.APP_IS_NOT_EXIST);
		}

		// 3. 获取应用最大版本号 初始添加应用版本号为 0 + 1
		Integer maxVersionNum = entityDao.getMaxVersionNum(applicationVersionEntity);
		if(maxVersionNum == null){
			maxVersionNum = 0;
		}
		return maxVersionNum;
	}

	/**
	 * 描述:  根据应用id删除版本信息
	 *
	 * @param appId
	 * @createDate: 2021/10/21 13:26
	 * @author: gaojian
	 * @modify:
	 * @return: void
	 */
	@Override
	public void deleteVersionByAppId(Long appId) {

		// 1. 非空判断
		Assert.notNull(appId,AppMessageContent.APP_ID_IS_NULL);

		// 2. 删除应用版本信息
		entityDao.deleteAllByAppId(appId);
	}

	/**
	 * 描述:  查询版本信息
	 * @createDate: 2021/10/21 17:05
	 * @author: gaojian
	 * @modify:
	 * @param applicationVersionDto
	 * @param pageable
	 * @return: org.springframework.data.domain.Page<java.util.Map<java.lang.String,java.lang.Object>>
	 */
	@Override
	public Page<Map<String, Object>> queryVersion(ApplicationVersionDto applicationVersionDto, Pageable pageable) {

        // 1. 非空判断
        Assert.notNull(applicationVersionDto,AppMessageContent.PARAMS_IS_NULL);

        // 2. 查询应用版本信息
		if(applicationVersionDto.getAppType() != null){
			applicationVersionDto.setAppTypeStr(applicationVersionDto.getAppType().name());
		}
        return entityDao.queryVersion(applicationVersionDto,pageable);
	}

	/**
	 * 描述:
	 *
	 * @param versionId
	 * @createDate: 2021/10/21 18:36
	 * @author: gaojian
	 * @modify:
	 * @return: com.geek.workbench.entity.application.ApplicationBaseConfigEntity
	 */
	@Override
	public ApplicationVersionEntity getVersionInfo(Long versionId) {

		// 1. 非空判断
		Assert.notNull(versionId,AppMessageContent.PARAMS_IS_NULL);

		// 2. 查询应用版本信息和应用基本信息
		ApplicationVersionEntity applicationVersion = get(versionId) ;
		if(applicationVersion == null){
			throw new AppException(AppMessageContent.APP_VERSION_IS_NULL);
		}
		ApplicationBasicEntity applicationBasicEntity = applicationBasicService.get(applicationVersion.getAppId());
		applicationVersion.setAppInfo(applicationBasicEntity);

		// 3. 查询配置信息
		String appType = applicationVersion.getAppType().name();
		ApplicationBaseConfigEntity applicationBaseConfigEntity = null;
		if(StringUtils.equals(appType, TerminalType.android.name())){
			applicationBaseConfigEntity =applicationConfigAndroidService.get(applicationVersion.getVersionConfigId());
		}else if(StringUtils.equals(appType, TerminalType.h5.name())){
			applicationBaseConfigEntity =applicationConfigHtmlService.get(applicationVersion.getVersionConfigId());
		}else if(StringUtils.equals(appType, TerminalType.ios.name())){
			applicationBaseConfigEntity =applicationConfigIosService.get(applicationVersion.getVersionConfigId());
		}
		applicationBaseConfigEntity.setPackageName(applicationBasicEntity.getPackageName());
		applicationBaseConfigEntity.setStartName(applicationVersion.getStartName());
		applicationBaseConfigEntity.setStartParams(applicationVersion.getStartParams());
		applicationBaseConfigEntity.setFileName(applicationVersion.getFileName());
		applicationVersion.setAppConfigs(Arrays.asList(applicationBaseConfigEntity));
		return applicationVersion;
	}

	/**
	 * 描述: 删除版本信息
	 *
	 * @param list
	 * @createDate: 2021/10/22 9:07
	 * @author: gaojian
	 * @modify:
	 * @return: void
	 */
	@Override
	public void deleteVersion(List<AppBaseIdParam> list) {

		// 1. 执行删除应用基本信息的方法
		this.delete(list.stream().map(map -> map.getId()).collect(Collectors.toList()));

		// 2. 删除关联信息
		for(AppBaseIdParam param : list){

			// 2.1. 删除应用配置信息
			deleteVersionConfig(param.getId());

			// 2.2. 删除终端绑定的版本
			deleteTerminalBindVersion(param.getId());

			// 2.3. 修改终端应用配置信息为
			deleteTerminalAppConfigVersion(param.getId());

			// 2.4 修改应用版本信息
			updateVersionInfo(param.getId());
		}
	}

	/**
	 * 描述:  最新版应用信息
	 *
	 * @param appId
	 * @param terminalType
	 * @createDate: 2021/10/26 11:49
	 * @author: gaojian
	 * @modify:
	 * @return: com.geek.workbench.entity.application.ApplicationVersionEntity
	 */
	@Override
	public ApplicationVersionEntity queryAppPackageName(Long appId, TerminalType terminalType) {

		return entityDao.getFirstByAppIdAndAppTypeAndIsNewVersion(appId,terminalType,true);
	}

	/**
	 * 描述:  删除配置信息
	 * @createDate: 2021/10/22 13:48
	 * @author: gaojian
	 * @modify:
	 * @param versionId
	 * @return: void
	 */
	public void deleteVersionConfig(Long versionId){

		// 1. 非空判断
		Assert.notNull(versionId,AppMessageContent.PARAMS_IS_NULL);

		// 2. 修改版本绑定的配置信息为无效
		entityDao.deleteAllConfigById(versionId);

	}

	/**
	 * 描述:  根据应用id删除终端绑定信息
	 * @createDate: 2021/10/21 14:31
	 * @author: gaojian
	 * @modify:
	 * @param versionId
	 * @return: void
	 */
	public void deleteTerminalBindVersion(Long versionId){

		// 1. 非空判断
		Assert.notNull(versionId,AppMessageContent.PARAMS_IS_NULL);

		// 2. 根据版本id删除终端绑定的应用版本
		terminalApplicationBindService.deleteBindByVersionId(versionId);
	}

	/**
	 * 描述:  根据应用id删除终端绑定信息
	 * @createDate: 2021/10/21 14:31
	 * @author: gaojian
	 * @modify:
	 * @param versionId
	 * @return: void
	 */
	public void deleteTerminalAppConfigVersion(Long versionId){

		// 1. 根据应用版本id修改应用配置状态为已下线
		TerminalApplicationConfigDto terminalApplicationConfigDto = new TerminalApplicationConfigDto();
		terminalApplicationConfigDto.setAppVersionId(versionId);
		terminalApplicationConfigDto.setStatus(StatusType.offline.getValue());
		terminalApplicationConfigService.updateStatusByVersionId(terminalApplicationConfigDto);
	}

	/**
	 * 描述:  修改版本信息
	 * @createDate: 2022/1/25 16:19
	 * @author: gaojian
	 * @modify:
	 * @param 
	 * @return: void
	 */
	public void updateVersionInfo(Long versionId){

		// 1. 获取删除应用版本信息
		ApplicationVersionEntity applicationVersionEntity = get(versionId);

		// 2. 如果删除的是最新版本则更新下一个版本
		if(applicationVersionEntity.getIsNewVersion()){

			Map<String,Object> searchParams = new HashMap<>(8);
			searchParams.put("appId",applicationVersionEntity.getAppId());
			searchParams.put("appType",applicationVersionEntity.getAppType());
			String[] sorts = {"versionNum:desc"};
			List<ApplicationVersionEntity> list = queryAll(searchParams,sorts);
			if(list != null && list.size() > 0){
				ApplicationVersionEntity result = list.get(0);
				result.setIsNewVersion(true);
				update(result,Arrays.asList("isNewVersion"));
			}
		}




	}

	/**
	 *终端应用配置缓存
	 *
	 * @author liuke
	 * @date 2021/10/15 14:56
	 * @return
	 */
	public LoadingCache<ConcurrentHashMap,List<ApplicationVersionEntity>> applicationVersionCache = CacheUtil.getInstance().build(
			new CacheLoader<ConcurrentHashMap,List<ApplicationVersionEntity>>() {
				@Override
				public List<ApplicationVersionEntity> load(ConcurrentHashMap map) throws Exception {
					ConcurrentHashMap<String,Object> searchParam = new ConcurrentHashMap();
					searchParam.put("appId",Long.valueOf(map.get("appId").toString()));
					searchParam.put("appType",map.get("appType").toString());
					searchParam.put("isNewVersion",true);
					return queryAll(searchParam);
				}
			}
	);

	/**
	 * 获取版本信息
	 * @param map
	 * @return
	 */
	@Override
	public List<ApplicationVersionEntity> getCacheByAppId(ConcurrentHashMap map){
		List<ApplicationVersionEntity> entities = Lists.newArrayList();
		try {
			entities = applicationVersionCache.get(map);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return entities;
	}
}
