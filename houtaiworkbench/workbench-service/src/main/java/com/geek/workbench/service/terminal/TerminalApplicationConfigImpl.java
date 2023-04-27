package com.geek.workbench.service.terminal;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.util.UtilBean;
import com.fosung.framework.dao.config.mybatis.page.MybatisPageRequest;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.system.pbs.api.SystemSysApi;
import com.geek.workbench.common.AppMessageContent;
import com.geek.workbench.config.TerminalContent;
import com.geek.workbench.dao.terminal.TerminalApplicationConfigDao;
import com.geek.workbench.dict.*;
import com.geek.workbench.dto.terminal.TerminalApplicationBindDto;
import com.geek.workbench.dto.terminal.TerminalApplicationConfigDto;
import com.geek.workbench.entity.application.ApplicationBasicEntity;
import com.geek.workbench.entity.application.ApplicationVersionEntity;
import com.geek.workbench.entity.terminal.TerminalApplicationBindEntity;
import com.geek.workbench.entity.terminal.TerminalApplicationConfigEntity;
import com.geek.workbench.entity.terminal.TerminalApplicationShelvesEntity;
import com.geek.workbench.entity.terminal.TerminalBasicEntity;
import com.geek.workbench.service.application.ApplicationBasicService;
import com.geek.workbench.service.application.ApplicationVersionService;
import com.geek.workbench.service.project.ProjectBasicService;
import com.geek.workbench.service.search.UniSearchService;
import com.geek.workbench.util.CacheUtil;
import com.geek.workbench.dict.*;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class TerminalApplicationConfigImpl extends AppJPABaseDataServiceImpl<TerminalApplicationConfigEntity, TerminalApplicationConfigDao>
		implements TerminalApplicationConfigService {

	@Autowired
	private ApplicationBasicService applicationService;

	@Autowired
	private ApplicationVersionService applicationVersionService;

	@Autowired
	private TerminalBasicService terminalBasicInfoService;

	@Autowired
	private TerminalApplicationBindService terminalApplicationBindService;

	@Autowired
	private TerminalApplicationShelvesService terminalShelvesService;

	@Autowired
	private SystemSysApi systemSysApi;

	@Autowired
	private ProjectBasicService projectBasicService;

	@Autowired
	private UniSearchService uniSearchService;

	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
		{
			put("terminalId","terminalId:EQ");
			put("appId","appId:EQ");
			put("appName","appName:EQ");
			put("status","status:EQ");
		}
	};

	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}

	/**
	 * 查询终端绑定应用列表
	 * @param searchParam
	 * @return
	 * @author fuhao
	 * @date 2021/10/13 13:57
	 */
	@Override
	public Page<TerminalApplicationConfigEntity> queryTerminalAppByPage(Map<String, Object> searchParam, TerminalApplicationConfigDto terminalAppConfigDto) {
		List<Map<String, Object>> projectList = projectBasicService.queryProjectOption();

		// 如果项目信息为空则返回空的终端信息
		if(projectList.size() == 0 ){
			return Page.empty();
		}

		ArrayList<Long> projects = new ArrayList<>();
		for (int i = 0; i < projectList.size(); i++) {
			Map<String, Object> project = projectList.get(i);
			projects.add((Long) project.get("dictValue"));
		}
		searchParam.put("projectList",projects);
		Pageable pageable = MybatisPageRequest.of(terminalAppConfigDto.getPageNum(),terminalAppConfigDto.getPageSize());
		return entityDao.queryTerminalAppByPage(searchParam,pageable);
	}

	/**
	 * 描述:  根据终端包名和终端类型查询终端绑定应用信息
	 *
	 * @param searchParam
	 * @createDate: 2021/12/1 14:47
	 * @author: gaojian
	 * @modify:
	 * @return: java.util.List<com.fosung.workbench.entity.terminal.TerminalApplicationConfigEntity>
	 */
	@Override
	public List<TerminalApplicationConfigEntity> queryTerminalApp(Map<String, Object> searchParam) {
		return entityDao.queryTerminalApp(searchParam);
	}

	/**
	 * 基本配置修改操作
	 * @param terminalApp
	 * @author fuhao
	 * @date 2021/10/13 13:57
	 */
	@Override
	public void update(TerminalApplicationConfigEntity terminalApp) {
		Set<String> updateFields = new HashSet<>();
		updateFields.add("appName");
		updateFields.add("appType");
		updateFields.add("appIcon");
		updateFields.add("remark");
		updateFields.add("startParam");
		updateFields.add("startName");
		updateFields.add("lastUpdateDatetime");
		updateFields.add("lastUpdateUserId");
		//按照字段更新对象
		this.update( terminalApp , updateFields ) ;
	}

	/**
	 * 查询选择应用展示最新版本app
	 * @param terminalAppConfigDto
	 * @return
	 * @author fuhao
	 * @date 2021/10/13 13:57
	 */
	@Override
	public List<TerminalApplicationConfigEntity> queryApp(TerminalApplicationConfigDto terminalAppConfigDto) {
		// 拼装查询参数
		TerminalApplicationConfigDto terminalAppDto = appendParams(terminalAppConfigDto);
		if(terminalAppDto != null){
			// 分页查询需要展示的app
			// Pageable pageable = MybatisPageRequest.of(terminalAppConfigDto.getPageNum(),terminalAppConfigDto.getPageSize());
			List<TerminalApplicationConfigEntity> appResult = applicationService.queryNewApp(terminalAppConfigDto);
			// 查询绑定版本信息
			List<TerminalApplicationBindEntity> bindAppVersion = checkBindAppVersion(terminalAppConfigDto.getTerminalId(),
					terminalAppConfigDto.getAppId(),terminalAppConfigDto.getAppVersionId(),terminalAppConfigDto.getProjectId());
			// List<TerminalApplicationConfigEntity> content = appResult.getContent();
			// 分析是否已经绑定过当前版本
			if(bindAppVersion != null){
				for (TerminalApplicationBindEntity appVersion:bindAppVersion) {
					for (TerminalApplicationConfigEntity terminalApp:appResult) {
						if(appVersion.getAppVersionId().equals(terminalApp.getId())){
							terminalApp.setChecked(true);
						}
					}
				}
			}
			return appResult;
		}
		return null;
	}

	/**
	 * 终端应用选择查询分类
	 * @param terminalApplicationConfigDto
	 * @return
	 * @author fuhao
	 * @date 2021/10/13 13:57
	 */
	@Override
	public List<Map> queryCategory(TerminalApplicationConfigDto terminalApplicationConfigDto) {
		// 拼装查询参数
		TerminalApplicationConfigDto terminalAppDto = appendParams(terminalApplicationConfigDto);
		if(terminalAppDto != null){
			List<ApplicationBasicEntity> appResult = applicationService.queryCategory(terminalAppDto);
			ResponseParam appCategory = systemSysApi.querySysTemDict("appCategory");
			ArrayList<Map> datalist = (ArrayList<Map>) appCategory.get("datalist");
			List<Map> categoryResult = new ArrayList<>();
			// 增加全部选项
			Map<String, Object> firstMap = new HashMap<>();
			firstMap.put(TerminalContent.CATEGORY_CODE,"all");
			firstMap.put(TerminalContent.CATEGORY_NAME,"全部");
			categoryResult.add(firstMap);
			for (int i = 0; i < datalist.size(); i++) {
				Map map = datalist.get(i);
				appResult.forEach(app -> {
					if(app.getCategoryCode().equals(map.get("dictValue"))){
						Map result = new HashMap<>();
						result.put(TerminalContent.CATEGORY_CODE,app.getCategoryCode());
						result.put(TerminalContent.CATEGORY_NAME,map.get("dictLabel"));
						categoryResult.add(result);
					}
				});
			}
			return categoryResult;
		}
		return null;
	}

	/**
	 * 终端应用选择确定
	 * @param terminalAppBindDtos
	 * @author fuhao
	 * @date 2021/10/13 13:57
	 */
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void saveTerminalAppBind(List<TerminalApplicationBindDto> terminalAppBindDtos) {
		terminalAppBindDtos.forEach(terminalAppBindDto -> {
			ApplicationBasicEntity appBasic = applicationService.get(terminalAppBindDto.getAppId());
			ApplicationVersionEntity applicationVersion = applicationVersionService.get(terminalAppBindDto.getAppVersionId());
			if(appBasic != null){
				TerminalApplicationConfigEntity terminalAppConfig = new TerminalApplicationConfigEntity();
				terminalAppConfig.setAppId(appBasic.getId());
				terminalAppConfig.setAppIcon(appBasic.getIconUrl());
				terminalAppConfig.setAppName(appBasic.getAppName());
				terminalAppConfig.setRemark(appBasic.getRemark());
				terminalAppConfig.setTerminalId(terminalAppBindDto.getTerminalId());
				terminalAppConfig.setAppType(applicationVersion.getAppType());
				terminalAppConfig.setStatus(StatusType.noRelease);
				terminalAppConfig.setProjectId(terminalAppBindDto.getProjectId());
				terminalAppConfig.setDataSource(DataSourceType.workbench);
				terminalAppConfig.setStartName(applicationVersion.getStartName());
				terminalAppConfig.setStartParam(applicationVersion.getStartParams());
				TerminalApplicationConfigEntity bindAppConfig = checkExistTerminalAppBind(terminalAppConfig);
				if(bindAppConfig != null){
					//由请求参数中获取需要更新的字段
					Set<String> updateFields = UtilDTO.toDTOExcludeFields(bindAppConfig, Arrays.asList("id")).keySet();
					//按照字段更新对象
					this.update( bindAppConfig , updateFields ) ;
				}else {
					this.save(terminalAppConfig);
				}
				List<TerminalApplicationBindEntity> bindAppVersion = checkBindAppVersion(terminalAppBindDto.getTerminalId(),
						terminalAppBindDto.getAppId(),terminalAppBindDto.getAppVersionId(),terminalAppBindDto.getProjectId());
				if(bindAppVersion == null || bindAppVersion.size() == 0){
					TerminalApplicationBindEntity terminalBindApp = new TerminalApplicationBindEntity();
					terminalBindApp.setAppConfigId(terminalAppConfig.getId());
					terminalBindApp.setTerminalId(terminalAppBindDto.getTerminalId());
					terminalBindApp.setAppId(terminalAppBindDto.getAppId());
					terminalBindApp.setAppVersionId(terminalAppBindDto.getAppVersionId());
					terminalBindApp.setStatus(StatusType.noRelease);
					terminalBindApp.setProjectId(terminalAppBindDto.getProjectId());
					terminalApplicationBindService.save(terminalBindApp);
				}
			}
		});
	}

	/**
	 * 终端应用复制
	 * @param terminalAppConfigDto
	 * @author fuhao
	 * @date 2021/10/13 13:57
	 */
	@Override
	@Transactional
	public void copy(TerminalApplicationConfigDto terminalAppConfigDto) {
		TerminalApplicationConfigEntity terminalApplicationConfig = this.get(terminalAppConfigDto.getId());
		// 复制基础信息表
		TerminalApplicationConfigEntity configCopy = new TerminalApplicationConfigEntity();
		if(terminalApplicationConfig != null){
			TerminalApplicationConfigEntity terminalApplicationConfigCopy = UtilBean.copyBean(terminalApplicationConfig, TerminalApplicationConfigEntity.class);
			terminalApplicationConfigCopy.setId(null);
			terminalApplicationConfigCopy.setStatus(StatusType.noRelease);
			terminalApplicationConfigCopy.setAppName(terminalApplicationConfigCopy.getAppName()+"{1}");
			configCopy = this.save(terminalApplicationConfigCopy);
		}
		// 复制应用版本信息
		Map<String, Object> searchBindParam = new HashMap<>();
		searchBindParam.put(TerminalContent.APP_CONFIG_ID,terminalAppConfigDto.getId());
		List<TerminalApplicationBindEntity> terminalApplicationBinds = terminalApplicationBindService.queryAll(searchBindParam);
		if(terminalApplicationBinds != null && terminalApplicationBinds.size() > 0){
			for (TerminalApplicationBindEntity terminalApplicationBind: terminalApplicationBinds) {
				TerminalApplicationBindEntity terminalApplicationBindCopy = UtilBean.copyBean(terminalApplicationBind, TerminalApplicationBindEntity.class);
				terminalApplicationBindCopy.setId(null);
				terminalApplicationBindCopy.setStatus(StatusType.noRelease);
				terminalApplicationBindCopy.setAppConfigId(configCopy.getId());
				terminalApplicationBindService.save(terminalApplicationBindCopy);
			}
		}
		// 复制应用授权信息
		Map<String, Object> searchShelvesParam = new HashMap<>();
		searchShelvesParam.put(TerminalContent.APP_CONFIG_ID,terminalAppConfigDto.getId());
		List<TerminalApplicationShelvesEntity> terminalApplicationShelves = terminalShelvesService.queryAll(searchShelvesParam);
		if(terminalApplicationShelves != null && terminalApplicationShelves.size() > 0){
			for (TerminalApplicationShelvesEntity terminalShelves:terminalApplicationShelves) {
				TerminalApplicationShelvesEntity shelvesCopy = UtilBean.copyBean(terminalShelves, TerminalApplicationShelvesEntity.class);
				shelvesCopy.setId(null);
				shelvesCopy.setAppConfigId(configCopy.getId());
				terminalShelvesService.save(shelvesCopy);
			}
		}

		// 同步信息到统一搜索 2021 12 24 加
		uniSearchService.searchSynchronizationData(configCopy, SearchOperateType.add);
	}

	@Override
	public Map<String, List<TerminalApplicationShelvesEntity>> queryShelves(TerminalApplicationConfigDto terminalAppConfigDto) {
		Map<String,Object> searchParam = new HashMap<>();
		searchParam.put(TerminalContent.APP_CONFIG_ID,terminalAppConfigDto.getId());
		List<TerminalApplicationShelvesEntity> terminalApplicationShelves = terminalShelvesService.queryAll(searchParam);
		Map<String, List<TerminalApplicationShelvesEntity>> listMap = terminalApplicationShelves.stream().collect(Collectors.groupingBy(TerminalApplicationShelvesEntity::getShelvesType));
		return listMap;
	}

	/**
	 * 描述:  根据版本主键修改终端绑定应用状态信息
	 *
	 * @param terminalAppConfigDto
	 * @createDate: 2021/10/22 14:40
	 * @author: gaojian
	 * @modify:
	 * @return: void
	 */
	@Override
	public void updateStatusByVersionId(TerminalApplicationConfigDto terminalAppConfigDto) {

		entityDao.updateStatusByVersionId(terminalAppConfigDto);
	}

	/**
	 * 功能描述: 拼装参数
	 * TODO
	 *
	 * @param terminalApplicationConfigDto
	 * @return
	 * @author fuhao
	 * @date 2021/10/14 9:18
	 */
	@Override
	public TerminalApplicationConfigDto appendParams(TerminalApplicationConfigDto terminalApplicationConfigDto){
		TerminalBasicEntity terminalBasicEntity = terminalBasicInfoService.get(terminalApplicationConfigDto.getTerminalId());
		if (terminalBasicEntity != null){
			TerminalType terminalType = terminalBasicEntity.getTerminalType();
			List<AppType> typeParams = new ArrayList<>();
			typeParams.add(AppType.h5);
			if(AppType.android.getRemark().equals(terminalType.getRemark())){
				typeParams.add(AppType.android);
			}else if(AppType.ios.getRemark().equals(terminalType.getRemark())){
				typeParams.add(AppType.ios);
			}
			terminalApplicationConfigDto.setTypeParams(typeParams);
			return terminalApplicationConfigDto;
		}
		return null;
	}

	/**
	 * 描述:  根据应用id删除基础配置信息
	 *
	 * @param appId
	 * @createDate: 2021/11/6 17:35
	 * @author: gaojian
	 * @modify:
	 * @return: void
	 */
	@Override
	public void deleteByAppId(Long appId) {

		Assert.notNull(appId, AppMessageContent.APP_ID_IS_NULL);

		Map<String,Object> searchParams = new HashMap<>(4);
		searchParams.put("appId",appId);
		List<TerminalApplicationConfigEntity> list = queryAll(searchParams);
		list.forEach(terminalApplicationConfigEntity -> {

			// 同步信息到统一搜索 2021 12 24 加
			uniSearchService.searchSynchronizationData(terminalApplicationConfigEntity,SearchOperateType.del);
		});

		entityDao.deleteByAppId(appId);
	}

	@Override
	public void deleteByConfigId(List<AppBaseIdParam> list) {
		//执行删除
		List<Long> ids = list.stream().map(map -> map.getId()).collect(Collectors.toList());
		this.delete(ids);
		ids.forEach(id->{
			// 删除版本信息
			HashMap<String, Object> configInfo = new HashMap<>(4);
			configInfo.put("appConfigId",id);

			// 同步信息到统一搜索 2021 12 24 加
			TerminalApplicationConfigEntity terminalApplicationConfigEntity = new TerminalApplicationConfigEntity();
			terminalApplicationConfigEntity.setId(id);
			uniSearchService.searchSynchronizationData(terminalApplicationConfigEntity,SearchOperateType.del);

			List<TerminalApplicationBindEntity> bindInfo = terminalApplicationBindService.queryAll(configInfo);
			terminalApplicationBindService.delete(bindInfo.stream().map(map -> map.getId()).collect(Collectors.toList()));
			// 删除授权信息
			bindInfo.forEach(bindId -> {
				List<TerminalApplicationShelvesEntity> shelvesInfo = terminalShelvesService.queryAll(configInfo);
				terminalShelvesService.delete(shelvesInfo.stream().map(map -> map.getId()).collect(Collectors.toList()));
			});
		});
	}

	/**
	 *
	 * @param terminalId 终端id
	 * @param appId 应用id
	 * @param appVersionId 应用版本id
	 * @param projectId 项目id
	 * @return
	 * @author fuhao
	 * @date 2021/10/14 9:18
	 */
	public List<TerminalApplicationBindEntity> checkBindAppVersion(Long terminalId,Long appId,Long appVersionId,Long projectId){
		Map<String,Object> searchParam = new HashMap();
		searchParam.put(TerminalContent.TERMINAL_ID,terminalId);
		if(appId != null){
			searchParam.put(TerminalContent.APP_ID,appId);
		}
		if(appVersionId != null){
			searchParam.put(TerminalContent.APP_VERSION_ID,appVersionId);
		}

		searchParam.put(TerminalContent.PROJECT_ID,projectId);
		List<TerminalApplicationBindEntity> bindAppVersion= terminalApplicationBindService.queryAll(searchParam);
		if(bindAppVersion != null && bindAppVersion.size() > 0){
			return bindAppVersion;
		}
		return null;
	}

	/**
	 * 检查终端是否已经绑定应用
	 * @param terminalAppConfig
	 * @return
	 * @author fuhao
	 * @date 2021/10/14 9:18
	 */
	public TerminalApplicationConfigEntity checkExistTerminalAppBind(TerminalApplicationConfigEntity terminalAppConfig){
		Map<String, Object> searchParam = new HashMap<>();
		searchParam.put("terminalId",terminalAppConfig.getTerminalId());
		searchParam.put("appId",terminalAppConfig.getAppId());
		List<TerminalApplicationConfigEntity> bindList = this.queryAll(searchParam);
		if (bindList != null && bindList.size() > 0){
			for (TerminalApplicationConfigEntity bind:bindList) {
				terminalAppConfig.setId(bind.getId());
				return terminalAppConfig;
			}
		}
		return null;
	}


	/**
	 *终端应用配置缓存
	 *
	 * @author liuke
	 * @date 2021/10/15 14:56
	 * @return
	 */
	public LoadingCache<Long,List<TerminalApplicationConfigEntity>> terminalApplicationConfigCache = CacheUtil.getInstance().build(
			new CacheLoader<Long,List<TerminalApplicationConfigEntity>>() {
				@Override
				public List<TerminalApplicationConfigEntity> load(Long key) throws Exception {
					Map<String,Object> searchParam = Maps.newHashMap();
					searchParam.put("status",StatusType.release);
					searchParam.put("terminalId",key);
					return queryAll(searchParam);
				}
			}
	);


	/**
	 *
	 * 根据终端获取应用
	 * @param terminalId
	 * @author liuke
	 * @date 2021/10/18 10:05
	 * @return java.util.List<com.fosung.workbench.entity.terminal.TerminalApplicationConfigEntity>
	 */
	@Override
	public List<TerminalApplicationConfigEntity> getCacheAppByTerminal(Long terminalId){
		List<TerminalApplicationConfigEntity> entities = Lists.newArrayList();
		try {
			entities = terminalApplicationConfigCache.get(terminalId);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return entities;
	}

	/**
	 *终端应用配置缓存
	 *
	 * @author liuke
	 * @date 2021/10/15 14:56
	 * @return
	 */
	public LoadingCache<Long,TerminalApplicationConfigEntity> TerminalApplicationConfigCache = CacheUtil.getInstance().build(
			new CacheLoader<Long,TerminalApplicationConfigEntity>() {
				@Override
				public TerminalApplicationConfigEntity load(Long key) throws Exception {
					return get(key);
				}
			}
	);


	/**
	 * 获取终端应用配置
	 * @param appId
	 * @return
	 */
	@Override
	public TerminalApplicationConfigEntity getConfigById(Long appId){
		TerminalApplicationConfigEntity entity = new TerminalApplicationConfigEntity();
		try {
			entity = TerminalApplicationConfigCache.get(appId);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return entity;
	}
}
