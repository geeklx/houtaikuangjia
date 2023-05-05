package com.fosung.workbench.service.application;

import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.exception.AppException;
import com.fosung.framework.common.secure.auth.AppUserDetailsService;
import com.fosung.framework.common.util.UtilString;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.workbench.AppBean.ApkBasic;
import com.fosung.workbench.AppBean.ApkBasicDto;
import com.fosung.workbench.common.AppMessageContent;
import com.fosung.workbench.common.GlobalVariableKey;
import com.fosung.workbench.dao.application.ApplicationBasicDao;
import com.fosung.workbench.dict.AuditStatusType;
import com.fosung.workbench.dict.DataSourceType;
import com.fosung.workbench.dto.terminal.TerminalApplicationConfigDto;
import com.fosung.workbench.entity.application.*;
import com.fosung.workbench.entity.terminal.TerminalApplicationConfigEntity;
import com.fosung.workbench.entity.terminal.TerminalBasicEntity;
import com.fosung.workbench.entity.terminal.TerminalVersionEntity;
import com.fosung.workbench.service.terminal.TerminalApplicationBindService;
import com.fosung.workbench.service.terminal.TerminalApplicationConfigService;
import com.fosung.workbench.service.terminal.TerminalBasicService;
import com.fosung.workbench.service.terminal.TerminalVersionService;
import com.fosung.workbench.util.ApkParserUtil;
import com.fosung.workbench.util.AppHeaderResolution;
import com.fosung.workbench.util.CacheUtil;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * 描述:  应用基本信息服务实现类
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Slf4j
@Service
public class ApplicationBasicServiceImpl extends AppJPABaseDataServiceImpl<ApplicationBasicEntity, ApplicationBasicDao>
	implements ApplicationBasicService {

	/**
	 * 描述:  注入安卓服务
	 * @createDate: 2021/10/27 14:57
	 * @author: gaojian
	 */
	@Autowired
	private ApplicationConfigAndroidService applicationConfigAndroidService;

	/**
	 * 描述:  注入IOS服务
	 * @createDate: 2021/10/27 14:57
	 * @author: gaojian
	 */
	@Autowired
	private ApplicationConfigIosService applicationConfigIosService;

	/**
	 * 描述:  注入H5服务
	 * @createDate: 2021/10/27 14:57
	 * @author: gaojian
	 */
	@Autowired
	private ApplicationConfigHtmlService applicationConfigHtmlService;

	/**
	 * 描述:  注入版本服务
	 * @createDate: 2021/10/27 14:57
	 * @author: gaojian
	 */
    @Autowired
    private ApplicationVersionService applicationVersionService;

	/**
	 * 描述:  注入终端绑定应用服务
	 * @createDate: 2021/10/27 14:57
	 * @author: gaojian
	 */
	@Autowired
	private TerminalApplicationBindService terminalApplicationBindService;

	/**
	 * 描述:  注入终端绑定应用
	 * @createDate: 2021/10/27 14:57
	 * @author: gaojian
	 */
	@Autowired
	private TerminalApplicationConfigService terminalApplicationConfigService;

	/**
	 * 描述:  注入登录用户详情服务类
	 * @createDate: 2021/10/13 18:03
	 * @author: gaojian
	 */
	@Autowired
	private AppUserDetailsService appUserDetailsService ;
	@Autowired
	private TerminalBasicService terminalBasicService;
    @Autowired
    private TerminalVersionService terminalVersionService;


	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
                put("appName","appName:LIKE");
                put("appCode","appCode:LIKE");
                put("appType","appType:EQ");
                put("categoryCode","categoryCode:EQ");
				put("startTime", "createDatetime:GTEDATE");
				put("endTime", "createDatetime:LTEDATE");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}

	/**
	 * 描述:  保存应用
	 * @createDate: 2021/10/21 12:31
	 * @author: gaojian
	 * @modify:
	 * @param applicationBasicEntity
	 * @return: void
	 */
	@Override
	public void saveApplicationBasic(ApplicationBasicEntity applicationBasicEntity) {

		// 1. 校验应用信息编码是否存在 和 本类型中包名是否已存在
		ApplicationBasicEntity result = entityDao.queryFirstByAppCodeAndDel(applicationBasicEntity.getAppCode()
				,Boolean.FALSE);
		if(result != null ){
			throw new AppException(AppMessageContent.APP_CODE_IS_EXIST);
		}

		// 应产品要求为适应灯塔业务去掉包名唯一校验 2021-12-13加
//		ApplicationBasicEntity result1 = entityDao.queryFirstByPackageNameAndAppTypeAndDel(applicationBasicEntity.getPackageName()
//				,applicationBasicEntity.getAppType(),Boolean.FALSE);
//		if(result1 != null ){
//			throw new AppException(AppMessageContent.PACKAGE_NAME_IS_EXIST);
//		}

		// 2. 设置默认信息
		if(applicationBasicEntity.getDataSource() == null ){
			applicationBasicEntity.setDataSource(DataSourceType.workbench);
		}
		String userName = "";
		try {
			userName = appUserDetailsService.getAppUserDetails().getUsername();
		}catch (Exception e){
			log.error(e.getMessage());
		}
		applicationBasicEntity.setExamineFlag(AuditStatusType.pass.name());
		applicationBasicEntity.setCreaterName(userName);

		// 3. 保存应用信息
		this.save(applicationBasicEntity);
	}

	/**
	 * 终端应用查询最新发布版本的app
	 * @param terminalApplicationConfigDto
	 * @return
	 * @author fuhao
	 * @date 2021/10/15 10:16
	 */
	@Override
	public List<TerminalApplicationConfigEntity> queryNewApp(TerminalApplicationConfigDto terminalApplicationConfigDto) {
		return entityDao.queryNewApp(terminalApplicationConfigDto);
	}

	/**
	 * 终端应用查询所在分类
	 * @param terminalApplicationConfigDto
	 * @return
	 * @author fuhao
	 * @date 2021/10/15 10:16
	 */
	@Override
	public List<ApplicationBasicEntity> queryCategory(TerminalApplicationConfigDto terminalApplicationConfigDto) {
		return entityDao.queryCategory(terminalApplicationConfigDto);
	}


    /**
     * 描述:  删除应用信息
     * @createDate: 2021/10/18 17:41
     * @author: gaojian
     * @modify:
     * @param list
     * @return: void
     */
	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void deleteApp(List<AppBaseIdParam> list) {

		// 1. 执行删除应用基本信息的方法
		delete(list.stream().map(map -> map.getId()).collect(Collectors.toList()));

		// 2. 删除关联信息
		for(AppBaseIdParam param : list){

			// 2.1. 删除应用配置信息
			deleteAppConfig(param.getId());

			// 2.2. 删除应用版本信息
			deleteAppVersion(param.getId());

			// 2.3 删除终端应用配置信息
			terminalApplicationConfigService.deleteByAppId(param.getId());

			// 2.3 删除应用终端关联关系
			deleteTerminalAppBind(param.getId());
		}

	}

	/**
	 * 描述:  获取应用配置信息
	 * @createDate: 2021/10/18 16:32
	 * @author: gaojian
	 * @modify:
	 * @param appId
	 * @return: java.util.List<com.fosung.workbench.entity.application.ApplicationBaseConfigEntity>
	 */
	@Override
	public List<ApplicationBaseConfigEntity> getApplicationConfig(Long appId) {

    	// 1. 查询安卓配置信息
		List<ApplicationBaseConfigEntity> list = new ArrayList<>();
		Map<String, Object> searchParam =  new HashMap<>(8);
		searchParam.put(GlobalVariableKey.APP_ID,appId);
		List<ApplicationConfigAndroidEntity>  androidEntityList =  applicationConfigAndroidService.queryAll(searchParam);
		if( androidEntityList != null && androidEntityList.size() > 0 ){
			androidEntityList.forEach(applicationConfigAndroidEntity ->{
				list.add(applicationConfigAndroidEntity);
			});
		}

		// 2. 查询IOS配置信息
		List<ApplicationConfigIosEntity>  iosEntityList =  applicationConfigIosService.queryAll(searchParam);
		if( iosEntityList != null && iosEntityList.size() > 0 ){
			iosEntityList.forEach(applicationConfigIosEntity ->{
				list.add(applicationConfigIosEntity);
			});
		}

		// 3. 查询H5信息
		List<ApplicationConfigHtmlEntity>  htmlEntityList =  applicationConfigHtmlService.queryAll(searchParam);
		if( htmlEntityList != null && htmlEntityList.size() > 0 ){
			htmlEntityList.forEach(applicationConfigHtmlEntity ->{
				list.add(applicationConfigHtmlEntity);
			});
		}
		return list;
	}

	/**
	 * 描述:  获取初始应用配置信息
	 *
	 * @param appId
	 * @createDate: 2021/10/18 16:33
	 * @author: gaojian
	 * @modify:
	 * @return: java.util.List<com.fosung.workbench.entity.application.ApplicationBaseConfigEntity>
	 */
	@Override
	public List<ApplicationBaseConfigEntity> getInitApplicationConfig(Long appId) {

		// 1. 非空判断
		Assert.notNull(appId, AppMessageContent.APP_IS_NOT_EXIST);

		// 2. 查询安卓配置信息
		List<ApplicationBaseConfigEntity> list = new ArrayList<>();
		ApplicationBaseConfigEntity android = applicationConfigAndroidService.queryFirstByAppIdOrderByCreateDatetime(appId);
		if(android != null ){
			list.add(android);
		}

		// 3. 查询IOS配置信息
		ApplicationBaseConfigEntity ios = applicationConfigIosService.queryFirstByAppIdOrderByCreateDatetime(appId);
		if(ios != null ){
			list.add(ios);
		}

		// 4. 查询H5配置信息
		ApplicationBaseConfigEntity h5 = applicationConfigHtmlService.queryFirstByAppIdOrderByCreateDatetime(appId);
		if(h5 != null ){
			list.add(h5);
		}
		return list;
	}

	/**
	 * 描述:  删除应用配置信息
	 * @createDate: 2021/10/18 20:59
	 * @author: gaojian
	 * @modify:
	 * @param appId
	 * @return: void
	 */
	public void  deleteAppConfig(Long appId){

		// 1. 执行删除应用配置的方法
		entityDao.deleteAppConfigByAppId(appId);

	}

	/**
	 * 描述:  删除应用版本信息
	 * @createDate: 2021/10/21 13:37
	 * @author: gaojian
	 * @modify:
	 * @param appId
	 * @return: void
	 */
	public void deleteAppVersion(Long appId){

		// 1. 执行根据应用主键删除应用版本信息的方法
		applicationVersionService.deleteVersionByAppId(appId);
	}

	/**
	 * 描述:  根据应用id删除终端绑定信息
	 * @createDate: 2021/10/21 14:31
	 * @author: gaojian
	 * @modify:
	 * @param appId
	 * @return: void
	 */
	public void deleteTerminalAppBind(Long appId){

		// 1. 执行根据应用id删除终端绑定的应用
		terminalApplicationBindService.deleteBindByAppId(appId);
	}

	/**
	 *终端应用配置缓存
	 *
	 * @author liuke
	 * @date 2021/10/15 14:56
	 * @return
	 */
	public LoadingCache<Long,ApplicationBasicEntity> applicationCache = CacheUtil.getInstance().build(
			new CacheLoader<Long,ApplicationBasicEntity>() {
				@Override
				public ApplicationBasicEntity load(Long key) throws Exception {
					return get(key);
				}
			}
	);


	/**
	 *
	 * 根据终端获取应用
	 * @param appId
	 * @author liuke
	 * @date 2021/10/18 10:05
	 * @return java.util.List<com.fosung.workbench.entity.terminal.TerminalApplicationConfigEntity>
	 */
	@Override
	public ApplicationBasicEntity getAppById(Long appId){
		ApplicationBasicEntity entity = new ApplicationBasicEntity();
		try {
			entity = applicationCache.get(appId);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return entity;
	}
    @Override
    public Map<String,Object> upgrade(ApkBasicDto info, HttpServletRequest servletRequest){
        String msg="";
        Map<String,Object> map = new HashMap<>();
        if(null == info.getAppPackageName()){
            msg = "包名不能为空";
            map.put("msg",msg);
            return map;
        }
        if(null == info.getServerVersionName() || null == info.getServerVersionCode()){
            msg = "版本信息不能为空";
            map.put("msg",msg);
            return map;
        }
        AppHeaderResolution.HeaderMessage headerMessage = AppHeaderResolution.resolutionHeader(servletRequest);
        //根据包名和终端类型获取包名
        TerminalBasicEntity terminalBasic = terminalBasicService.getCacheTerminalByNameAndType(headerMessage.getPackageName(),headerMessage.getTerminalType().name());
        if(null == terminalBasic){
            msg ="未获取到终端信息";
            map.put("msg",msg);
            return map;
        }
        TerminalVersionEntity terminalVersion = terminalVersionService.getCacheTerminal(String.valueOf(terminalBasic.getId()));
        if(null == terminalVersion){
            msg ="未获取到终端版本信息";
            map.put("msg",msg);
            return map;
        }
        ApkBasic infoNew = new ApkBasic();
        Map apkInfo = new HashMap();
        if(null != info.getId()){//更新应用信息
            Map<String, Object> searchParam = new HashMap<String, Object>();
            TerminalApplicationConfigEntity terminalApplicationConfig = terminalApplicationConfigService.get(info.getId());
            if(null == terminalApplicationConfig){
                msg ="未获取到应用配置信息";
                map.put("msg",msg);
                return map;
            }
            searchParam = new HashMap<String, Object>();
            searchParam.put("appId",terminalApplicationConfig.getAppId());
            searchParam.put("isNewVersion",true);
            ApplicationVersionEntity versionEntity = new ApplicationVersionEntity();
            List<ApplicationVersionEntity> versionEntityList = applicationVersionService.queryAll(searchParam) ;
            if(versionEntityList.isEmpty()){
                msg ="未获取到应用版本信息";
                map.put("msg",msg);
                return map;
            }else{
                versionEntity = versionEntityList.get(0);
            }
            searchParam = new HashMap<String, Object>();
            searchParam.put("appId",terminalApplicationConfig.getAppId());
            searchParam.put("appVersionId",versionEntity.getId());
            ApplicationConfigAndroidEntity androidEntity = new ApplicationConfigAndroidEntity();
            List<ApplicationConfigAndroidEntity>  androidEntityList =applicationConfigAndroidService.queryAll(searchParam);
            if(!androidEntityList.isEmpty()){
                androidEntity = androidEntityList.get(0);
            }else{
                msg ="未获取到应用配置信息";
                map.put("msg",msg);
                return map;
            }
			//apkInfo =  ApkParserUtil.apkParser(terminalVersion.getInstallationPackage());
            //infoNew = applicationConfigAndroidService.getInfo(terminalVersion,androidEntity, terminalBasic);
        }else{//更新终端信息
			//apkInfo =  ApkParserUtil.apkParser(terminalVersion.getInstallationPackage());
             infoNew = terminalVersionService.getInfo(terminalVersion,terminalBasic);

        }
        if((info.getServerVersionCode().equals(terminalVersion.getTerminalVersion()) && info.getServerVersionName().equals(terminalVersion.getVersionName())
                && info.getAppPackageName().equals(terminalBasic.getPackageName()) && info.getMd5().equals(terminalVersion.getInstallationPackageMd5())) || Integer.valueOf(info.getServerVersionCode())>Integer.valueOf(terminalVersion.getTerminalVersion())){
			msg ="已经是最新版本";
			map.put("msg",msg);
			return map;
        }
        map.put("msg",msg);
        map.put("info",infoNew);
        return map;
    }
	@Override
	public Map<String,Object> upgradeApp(ApkBasicDto dto, HttpServletRequest servletRequest){
		String msg="";
		Map<String,Object> map = new HashMap<>();
	   if(null == dto.getAppPackageName()){
		   msg = "包名不能为空";
	   }
        if(null == dto.getServerVersionName() || null == dto.getServerVersionCode()){
			msg="版本信息不能为空";
	    }
		Map<String, Object> searchParam = new HashMap<String, Object>();
		AppHeaderResolution.HeaderMessage headerMessage = AppHeaderResolution.resolutionHeader(servletRequest);
		//根据包名和终端类型获取包名
		TerminalBasicEntity terminalBasic = terminalBasicService.getCacheTerminalByNameAndType(headerMessage.getPackageName(),headerMessage.getTerminalType().name());
		if(null == terminalBasic){
			msg= "未获取到终端信息";
		}
		TerminalApplicationConfigEntity terminalApplicationConfig = terminalApplicationConfigService.get(dto.getId());
		if(null == terminalApplicationConfig){
			msg="未获取到应用配置信息";
		}
		searchParam = new HashMap<String, Object>();
		searchParam.put("appId",terminalApplicationConfig.getAppId());
		searchParam.put("isNewVersion",true);
		ApplicationVersionEntity versionEntity = new ApplicationVersionEntity();
		List<ApplicationVersionEntity> versionEntityList = applicationVersionService.queryAll(searchParam) ;
		if(versionEntityList.isEmpty()){
			msg= "未获取到应用版本信息";
		}else{
			versionEntity = versionEntityList.get(0);
		}
	    searchParam = new HashMap<String, Object>();
        searchParam.put("bundleId",dto.getAppPackageName());
		searchParam.put("appVersionId",versionEntity.getId());
	    ApplicationConfigAndroidEntity androidEntity = new ApplicationConfigAndroidEntity();
	    List<ApplicationConfigAndroidEntity>  androidEntityList =applicationConfigAndroidService.queryAll(searchParam);
        if(!androidEntityList.isEmpty()){
		 androidEntity = androidEntityList.get(0);
	   }else{
			msg = "未获取到应用信息";
	  }
        if(null== androidEntity.getAppPackagePath() || UtilString.isEmpty(androidEntity.getAppPackagePath())){
			msg = "未获取到应用版本更新地址";
		}
	 /* Map p =  ApkParserUtil.apkParser(androidEntity.getAppPackagePath());
	   ApkBasic infoNew = applicationConfigAndroidService.getInfo(androidEntity, p);
        //infoNew.setIsForce(null!=terminalVersion.getForceUpgrade() ? terminalVersion.getForceUpgrade():false);
		infoNew.setIsForce(false);
        if((dto.getServerVersionCode().equals(infoNew.getServerVersionCode()) && dto.getServerVersionName().equals(infoNew.getServerVersionName())
			&& dto.getAppPackageName().equals(infoNew.getAppPackageName()) && dto.getMd5().equals(infoNew.getMd5())) || Integer.valueOf(dto.getServerVersionCode())>Integer.valueOf(p.get("versionCode").toString())){
			msg = "已经是最新版本";
	}*/
		map.put("msg",msg);
       // map.put("info",infoNew);
		return map;
	}

}
