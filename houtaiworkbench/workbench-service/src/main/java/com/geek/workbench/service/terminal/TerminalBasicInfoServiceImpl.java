package com.geek.workbench.service.terminal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.common.config.AppProperties;
import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.exception.AppException;
import com.fosung.framework.common.secure.auth.AppUserDetails;
import com.fosung.framework.dao.config.mybatis.page.MybatisPage;
import com.fosung.framework.dao.config.mybatis.page.MybatisPageRequest;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.workbench.AppBean.UmsBean;
import com.geek.workbench.common.GlobalVariableKey;
import com.geek.workbench.common.MessageContent;
import com.geek.workbench.config.TerminalContent;
import com.geek.workbench.constant.UtilConstants;
import com.geek.workbench.dao.project.ProjectBasicDao;
import com.geek.workbench.dao.terminal.TerminalBasicDao;
import com.geek.workbench.dict.ConfigType;
import com.geek.workbench.dict.SearchOperateType;
import com.geek.workbench.dict.TerminalAppCategoryType;
import com.geek.workbench.dto.terminal.TerminalBasicDto;
import com.geek.workbench.entity.config.TerminalConfigManageEntity;
import com.geek.workbench.entity.terminal.TerminalApplicationConfigEntity;
import com.geek.workbench.entity.terminal.TerminalBasicEntity;
import com.geek.workbench.entity.terminal.TerminalConfigCategoryEntity;
import com.geek.workbench.service.config.TerminalConfigManageService;
import com.geek.workbench.service.feign.GwapiUmsBindService;
import com.geek.workbench.service.project.ProjectBasicService;
import com.geek.workbench.service.search.UniSearchService;
import com.geek.workbench.util.AppHeaderResolution;
import com.geek.workbench.util.CacheUtil;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class TerminalBasicInfoServiceImpl extends AppJPABaseDataServiceImpl<TerminalBasicEntity, TerminalBasicDao>
	implements TerminalBasicService {

	@Autowired
	private TerminalConfigCommonService terminalConfigCommonService;
	@Autowired
	private TerminalConfigCategoryService terminalConfigCategoryService;

	@Autowired(required = false)
	private ProjectBasicDao projectBasicDao;

	@Autowired
	private ProjectBasicService projectBasicService;
	@Autowired
	protected AppProperties appProperties;

	@Autowired
	private TerminalApplicationConfigService terminalApplicationConfigService;

	@Autowired
	private UniSearchService uniSearchService;

	@Value(value = "${app.customParams.projectRoleCode:projectAdmin}")
	private String projectRoleCode;

	@Value(value = "${app.customParams.superRoleCode:superAdmin}")
	private String superRoleCode;
	@Autowired
	private GwapiUmsBindService gwapiUmsBindService;

	/**
	 * 描述:  注入终端运行配置服务
	 * @createDate: 2021/11/2 17:58
	 * @author: gaojian
	 */
	@Autowired
	private TerminalConfigManageService terminalConfigManageService;

	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("projectId","projectId:EQ");
				put("terminalName","terminalName:LIKE");
				put("terminalType","terminalType:EQ");
				put("terminalCode","terminalCode:EQ");
				put("deployNetwork","deployNetwork:EQ");
				put("del","del:EQ");
				put("startTime", "createDatetime:GTEDATE");
				put("endTime", "createDatetime:LTEDATE");
				put("packageName","packageName:EQ");

	       }
	};

	/**
	 *终端基本信息缓存
	 *
	 * @author liuke
	 * @date 2021/10/15 14:56
	 * @return
	 */
	public LoadingCache<Map,List<TerminalBasicEntity>> terminalBasicCache = CacheUtil.getInstance().build(
			new CacheLoader<Map,List<TerminalBasicEntity>>() {
				@Override
				public List<TerminalBasicEntity> load(Map map) throws Exception {
					Map<String,Object> searchParam = Maps.newHashMap();
					searchParam.put(TerminalContent.PACKAGE_NAME,map.get("packageName").toString());
					searchParam.put("terminalType",map.get("terminalType").toString());
					List<TerminalBasicEntity> result = queryAll(searchParam);
					return result;
				}
			}
	);
	/**
	 *终端基本信息缓存
	 *
	 * @author liuke
	 * @date 2021/10/15 14:56
	 * @return
	 */
	public LoadingCache<Map,TerminalBasicEntity> terminalBasicCacheByType = CacheUtil.getInstance().build(
			new CacheLoader<Map,TerminalBasicEntity>() {
				@Override
				public TerminalBasicEntity load(Map map) throws Exception {
					Map<String,Object> searchParam = Maps.newHashMap();
					searchParam.put(TerminalContent.PACKAGE_NAME,map.get("packageName").toString());
					searchParam.put(TerminalContent.TERMINAL_TYPE,map.get("terminalType").toString() );
					List<TerminalBasicEntity> result = queryAll(searchParam);
					TerminalBasicEntity basicEntity = new TerminalBasicEntity();
					if (!result.isEmpty()){
						basicEntity = result.get(0);
						return basicEntity;
					}else{
						return basicEntity;
					}
				}
			}
	);
	@Override
	public TerminalBasicEntity getCacheTerminalByNameAndType(String packageName, String terminalType) {
		TerminalBasicEntity basicEntity = new TerminalBasicEntity();
		Map map = new HashMap();
		map.put("packageName",packageName);
		map.put("terminalType",terminalType);
		try {
			basicEntity = terminalBasicCacheByType.get(map);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return basicEntity;
	}
	/**
	 *
	 * 在缓存根据包取出终端
	 * @param packageName
	 * @author liuke
	 * @date 2021/10/18 10:05
	 * @return java.util.List<com.geek.workbench.entity.terminal.TerminalApplicationConfigEntity>
	 */
	@Override
	public List<TerminalBasicEntity> getCacheTerminal(String packageName,String terminalType){
		List<TerminalBasicEntity> terminalBasicEntity = Lists.newArrayList();
		try {
			Map map = new HashMap();
			map.put("packageName",packageName);
			map.put("terminalType",terminalType);
			terminalBasicEntity = terminalBasicCache.get(map);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return terminalBasicEntity;
	}

	@Override
	@Transactional
	public ResponseParam saveInfo(TerminalBasicEntity terminalBasicInfo) {
		//拼装查询参数
		HashMap<String, Object> searchParam = Maps.newHashMap();
		String terminalCode = terminalBasicInfo.getTerminalCode();
		searchParam.put(TerminalContent.TERMINAL_CODE,terminalCode);
		searchParam.put(TerminalContent.DEL,false);
		//判断终端编号是否存在
		boolean exist = this.isExist(searchParam);
		if(exist){
			return ResponseParam.fail().message(TerminalContent.IS_EXIST_TERMINAL);
		}
		// 判断类型跟包名是否存在
		Map<String,Object> searchParamPackage = new HashMap<>();
		searchParamPackage.put(TerminalContent.TERMINAL_TYPE,terminalBasicInfo.getTerminalType());
		searchParamPackage.put(TerminalContent.PACKAGE_NAME,terminalBasicInfo.getPackageName());
		boolean existPackage = this.isExist(searchParamPackage);
		if(existPackage){
			return ResponseParam.fail().message(TerminalContent.CHECK_PACKAGE_NAME);
		}
		TerminalBasicEntity save = this.save(terminalBasicInfo);
		// 保存基础配置
		Map<String, Object> saveCommonConfigParam = new HashMap<>();
		Map<String, String> fingerprintMap = new HashMap<>();
		fingerprintMap.put(TerminalContent.FINGERPRINT,TerminalContent.FALSE);
		Map<String, String> contactsMap = new HashMap<>();
		contactsMap.put(TerminalContent.CONTACTS_NAME,TerminalContent.TRUE);
		contactsMap.put(TerminalContent.CONTACTS_PHONE4,TerminalContent.TRUE);
		saveCommonConfigParam.put(TerminalContent.FINGERPRINT,fingerprintMap);
		saveCommonConfigParam.put(TerminalContent.WATERMARK,contactsMap);
		terminalConfigCommonService.saveCommonConfiguration(saveCommonConfigParam);
		// 保存工作台分类默认配置
		ArrayList<TerminalAppCategoryType> category = new ArrayList<>();
		category.add(TerminalAppCategoryType.me);
		category.add(TerminalAppCategoryType.all);
//		category.add(TerminalAppCategoryType.regional);
		for (int i = 0; i < category.size(); i++) {
			TerminalConfigCategoryEntity terminalConfigCategory = new TerminalConfigCategoryEntity();
			terminalConfigCategory.setType(category.get(i));
			terminalConfigCategory.setName(category.get(i).remark);
			terminalConfigCategory.setTerminalId(save.getId());
			terminalConfigCategory.setNum(i+1);
			terminalConfigCategoryService.saveInfo(terminalConfigCategory);
		}

		// 3. 增加保存默认运行配置方法
		terminalConfigManageService.saveDefault(save.getId());
		return ResponseParam.saveSuccess();
	}

	@Override
	@Transactional
	public void deleteAllTerminal(List<AppBaseIdParam> list) {
		// 根据id删除终端信息
		this.delete(list.stream().map(map -> map.getId()).collect(Collectors.toList()));
		// 拼装删除参数
		for (int i = 0; i < list.size(); i++) {
			Map<String,Object> delParam = new HashMap<>();
			delParam.put("terminalId",list.get(i).getId());
			// 管理要删除的表名
			List<String> tablenames = new ArrayList<>();
			// 绑定应用信息
			tablenames.add("terminal_application_config");
			// 删除同步到统一搜索的应用信息 2021-12-27 加
			deleteUnionSearchApp(list.get(i).getId());
			// 绑定应用版本信息
			tablenames.add("terminal_application_bind");
			// 终端应用授权
			tablenames.add("terminal_application_shelves");
			// 终端版本
			tablenames.add("terminal_version");
			// 终端基础配置
			tablenames.add("terminal_config_common");
			// 终端底部导航配置
			tablenames.add("terminal_config_navigation_btm");
			// 终端顶部导航配置
			tablenames.add("terminal_config_navigation_top");
			// 终端顶部导航菜单配置
			tablenames.add("terminal_config_menu_top");
			// 终端协议配置
			tablenames.add("terminal_config_agreement");
			// 终端广告/引导页配置
			tablenames.add("terminal_image_config");
			for (String tablename:tablenames) {
				delParam.put("tablename",tablename);
				entityDao.deleteAllByTerminal(delParam);
			}
		}
	}


	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}

	/**
	 * 描述: 终端基本信息分页查询
	 * @return org.springframework.data.domain.Page<com.geek.workbench.entity.terminal.TerminalBasicEntity>
	 * @author fuhao
	 * @date 2021/10/26 15:37
	 **/
	@Override
	public Page<TerminalBasicEntity> queryTerminalBasicInfoByPage(TerminalBasicDto terminalBasicDto) {
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
		terminalBasicDto.setProjectList(projects);
		//分页参数
		Pageable pageable = MybatisPageRequest.of(terminalBasicDto.getPageNum(),terminalBasicDto.getPageSize());
		MybatisPage<TerminalBasicEntity> page = entityDao.queryTerminalBasicInfoByPage(terminalBasicDto,pageable);
		return page;
	}

	/**
	 * 描述: 终端下拉框
	 * @param searchParam
	 * @return java.util.List<com.geek.workbench.entity.terminal.TerminalBasicEntity>
	 * @author fuhao
	 * @date 2021/10/26 15:36
	 **/
	@Override
	public List<TerminalBasicEntity> queryTerminalOptions(Map<String, Object> searchParam) {
		List<Map<String, Object>> projectList = projectBasicService.queryProjectOption();

		// 如果项目信息为空则返回空的终端信息
		if(projectList.size() == 0 ){
			return new ArrayList<>();
		}

		ArrayList<Long> projects = new ArrayList<>();
		for (int i = 0; i < projectList.size(); i++) {
			Map<String, Object> project = projectList.get(i);
			projects.add((Long) project.get("dictValue"));
		}
		searchParam.put("projectList",projects);
		return entityDao.queryTerminalOptions(searchParam);
	}

	/**
	 * 描述:  查询终端树型结构数据
	 * @createDate: 2021/11/6 11:58
	 * @author: gaojian
	 * @modify:
	 * @param
	 * @return: java.util.List<com.geek.workbench.entity.terminal.TerminalBasicEntity>
	 */
	@Override
	public List<TerminalBasicEntity> queryTerminalTree(){

		// 1. 获取当前登录用户信息
		AppUserDetails appUserDetails = appUserDetailsService.getAppUserDetails();
		if( appUserDetails == null){
			throw new AppException(MessageContent.LOGIN_USER_IS_NULL);
		}

		// 2. 判断登录用户是否为超级管理员如果为超级管理员则查询全部项目
		Set<String> roleCodes = appUserDetails.getUserRoleCodes();
		List<TerminalBasicEntity> result = new ArrayList<>();
		for(String roleCode : roleCodes){

			// 2.1 超级管理员
			if(StringUtils.equals(roleCode, superRoleCode)){
				List<TerminalBasicEntity> projects = entityDao.queryProjectTree();
				if(projects.size() > 0){
					for( TerminalBasicEntity project : projects) {
						List<TerminalBasicEntity> terminalTree = entityDao.queryTerminalTree(project);
						if (terminalTree == null || terminalTree.size() < 1) {

						} else {
							project.setTerminalTrees(terminalTree);
							result.add(project);
						}
					}
				}
				return result;
			}

			// 2.2 项目管理员
			if(StringUtils.equals(roleCode, projectRoleCode)){
				List<Map<String,Object>> projects = projectBasicDao.queryProjectOption(appUserDetails.getUserId());
				if(projects.size() > 0){
					for ( Map project : projects){
						TerminalBasicEntity terminalBasicEntity = new TerminalBasicEntity();
						terminalBasicEntity.setProjectId(Long.valueOf(project.get(GlobalVariableKey.DICT_VALUE).toString()));
						terminalBasicEntity.setProjectName(project.get(GlobalVariableKey.DICT_LABEL).toString());
						List<TerminalBasicEntity> terminalTree = entityDao.queryTerminalTree(terminalBasicEntity);
						if(terminalTree == null || terminalTree.size() < 1){

						}else{
							terminalBasicEntity.setTerminalTrees(terminalTree);
							result.add(terminalBasicEntity);
						}
					}
					return result;
				}
			}
		}
		return result;
	}
	@Override
	public ConcurrentHashMap terminalBind(UmsBean umsBean, HttpServletRequest servletRequest) {
		JSONObject umsResult = new JSONObject();
		ConcurrentHashMap map = new ConcurrentHashMap();
		AppHeaderResolution.HeaderMessage headerMessage = AppHeaderResolution.resolutionHeader(servletRequest);
		//根据包名和终端类型获取包名
		TerminalBasicEntity terminalBasic = this.getCacheTerminalByNameAndType(headerMessage.getPackageName(), headerMessage.getTerminalType().name());
		if (null == terminalBasic) {
			map.put("msg", "未获取到终端信息");
			return map;
		}
		try {
			//调用统一认证服务获取用户信息
			ConcurrentHashMap userMap = this.getUserInfo(terminalBasic, headerMessage.getToken());
			if (null != userMap.get("msg")) {
				map.put("msg", userMap.get("msg").toString());
				return map;
			}
			JSONObject userObject = (JSONObject) userMap.get("userObject");
			if (Boolean.valueOf(userObject.get("success").toString()) == true) {
				//根据用户信息传参调用统一消息, 获取返回结果
				ConcurrentHashMap umsMap = this.callUms(headerMessage, terminalBasic, umsBean, userObject);
				if (null != umsMap.get("msg")) {
					map.put("msg", umsMap.get("msg").toString());
					return map;
				}
				 //umsResult = (JSONObject) umsMap.get("umsResult");
				HashMap umsResultMap = (HashMap)umsMap.get("umsResult");
				if (Boolean.valueOf(umsMap.get("success").toString()) == true) {
					map.put("objectResult", umsResultMap);
				} else {
					map.put("msg", "绑定统一消息失败");
					return map;
				}
			} else {
				map.put("msg", "获取用户信息失败");
				return map;
			}
		} catch (Exception e) {
			map.put("msg", "接口调用异常！");
		}
		return map;
	}

	/**
	 * 獲取未讀消息數量
	 * @param servletRequest
	 * @return
	 */
	@Override
	public ConcurrentHashMap getUnreadCount(HttpServletRequest servletRequest) {
		JSONObject unreadResult = new JSONObject();
		ConcurrentHashMap map = new ConcurrentHashMap();
		AppHeaderResolution.HeaderMessage headerMessage = AppHeaderResolution.resolutionHeader(servletRequest);
		//根据包名和终端类型获取包名
		TerminalBasicEntity terminalBasic = this.getCacheTerminalByNameAndType(headerMessage.getPackageName(), headerMessage.getTerminalType().name());
		if (null == terminalBasic) {
			map.put("msg", "未获取到终端信息");
			return map;
		}
		try {
			ConcurrentHashMap userMap = this.getUserInfo(terminalBasic, headerMessage.getToken());
			if (null != userMap.get("msg")) {
				map.put("msg", userMap.get("msg").toString());
				return map;
			}
			JSONObject userObject = (JSONObject) userMap.get("userObject");
			if (Boolean.valueOf(userObject.get("success").toString()) == true) {
				//根据用户信息传参调用统一消息, 获取返回结果
				ConcurrentHashMap umsMap = this.callUnreadCount(terminalBasic, userObject);
				if (null != umsMap.get("msg")) {
					map.put("msg", umsMap.get("msg").toString());
					return map;
				}
				unreadResult = (JSONObject) umsMap.get("unreadResult");
				if (Boolean.valueOf(unreadResult.get("success").toString()) == true) {
					map.put("unreadResult", unreadResult);
				} else {
					map.put("msg", "调用统一消息获取信息失败");
					return map;
				}
			} else {
				map.put("msg", "获取用户信息失败");
				return map;
			}
		}catch (Exception e) {
			map.put("msg", "接口调用异常！");
			return map;
		}
		return map;
	}

	/**
 　　* @description: 调用统一认证获取人员信息
 　　* @param userUrl  token
 　　* @return JSONObject
 　　* @author lihuiming
 　　* @date 2021/11/24 19:13
 　　*/
	@Override
	public ConcurrentHashMap getUserInfo(TerminalBasicEntity terminalBasic,String token) {

		//获取终端配置类型 authorized("认证服务"),resource("资源服务"),ums("消息服务"),oss("统一存储");
		ConcurrentHashMap userMap = new ConcurrentHashMap();
		TerminalConfigManageEntity terminalConfigManageEntity = getTerminalConfigManageEntity(terminalBasic, ConfigType.authorized.name());
		if(null == terminalConfigManageEntity){
			userMap.put("msg","未获取到终端配置信息");
			return userMap;
		}
		//获取前缀路径
		Map<String,Object> globalParams = this.appProperties.getGlobalParams();
		String commonUrl = globalParams.get("umsUrl").toString();
		String userUrl = "";

		Map<String, String> requestParams =new HashMap<>();
		//获取用户信息地址 传递header及参数
		//todo 如果是灯塔 走灯塔登录
		if(terminalBasic.getPackageName().equals("com.fosung.lighthouse.dt2")){
			userUrl = commonUrl + terminalConfigManageEntity.getConfigPlatform() + UtilConstants.dtUserSuffix;
		}else{
			userUrl = commonUrl + terminalConfigManageEntity.getConfigPlatform() + UtilConstants.userSuffix;
		}
		HttpPost httpPost = new HttpPost(userUrl);
		httpPost.addHeader("ContentType", "application/json;charset=UTF-8");
		httpPost.addHeader("token",token);
		StringEntity entity = new StringEntity(JSON.toJSONString(requestParams),"utf-8");//解决中文乱码问题
		httpPost.setEntity(entity);
		HttpClient client = HttpClients.createDefault();
        try {
			//获取返回数据
			HttpResponse httpResponse = client.execute(httpPost);
			JSONObject userResult = new JSONObject();
			if (httpResponse != null) {
				HttpEntity resEntity = httpResponse.getEntity();
				if (resEntity != null) {
					//格式化用户信息数据
					String result = EntityUtils.toString(resEntity, "utf-8");
					userResult = JSONObject.parseObject(result);
				}
			}
			userMap.put("userObject", userResult);
		}catch (Exception e){
			userMap.put("msg", "接口调用异常！");
			return userMap;
		}
		return userMap;
	}
	  /**
	 　　* @description: 调用统一消息绑定终端
	 　　* @param headerMessage terminalBasic umsBean object
	 　　* @return JSONObject
	 　　* @author lihuiming
	 　　* @date 2021/11/24 19:41
	 　　*/
	public ConcurrentHashMap callUms(AppHeaderResolution.HeaderMessage headerMessage , TerminalBasicEntity terminalBasic , UmsBean umsBean, JSONObject object) throws  Exception{
		ConcurrentHashMap umsMap = new ConcurrentHashMap();
		TerminalConfigManageEntity terminalConfigManageEntity = getTerminalConfigManageEntity(terminalBasic, ConfigType.ums.name());
		if(null == terminalConfigManageEntity){
			umsMap.put("msg","未获取到终端配置信息");
			return umsMap;
		}
		String dataStr = object.get("data").toString();
		JSONObject data = JSONObject.parseObject(dataStr);
		JSONObject obj = new JSONObject();
		obj.put("userName",data.get("username")== null ? "": data.get("username").toString());
		obj.put("userId",data.get("userId")== null ? "": data.get("userId").toString());
		obj.put("terminalCode",terminalBasic.getTerminalCode()== null ? "": terminalBasic.getTerminalCode());
		obj.put("terminalUniqueId",umsBean.getTerminalUniqueId());
		obj.put("terminalBrand", headerMessage.getTerminalType().name());
		obj.put("terminalModel",headerMessage.getModel()==null?"android": headerMessage.getModel());
		obj.put("orgId",data.get("orgId")== null ? " ": data.get("orgId").toString());
		obj.put("orgName",data.get("orgName")== null ? " ": data.get("orgName").toString());
		obj.put("channelCode","APP_MOB");//mob推送
		obj.put("delTerminal","true"); //是否删除原绑定设备 ；true：删除，false：不删除（默认）

		ResponseParam result =  gwapiUmsBindService.umsbind(obj);
		umsMap.put("success",result.get("success"));
		umsMap.put("umsResult",result);
		return umsMap;
	}


	/**
	 　　* @description: 调用统一消息獲取未讀數量
	 　　* @param headerMessage terminalBasic umsBean object
	 　　* @return JSONObject
	 　　* @author lihuiming
	 　　* @date 2021/11/24 19:41
	 　　*/
	public ConcurrentHashMap callUnreadCount( TerminalBasicEntity terminalBasic , JSONObject object) throws  Exception{
		ConcurrentHashMap unreadMap = new ConcurrentHashMap();
		TerminalConfigManageEntity terminalConfigManageEntity = getTerminalConfigManageEntity(terminalBasic, ConfigType.ums.name());
		if(null == terminalConfigManageEntity){
			unreadMap.put("msg","未获取到终端配置信息");
			return unreadMap;
		}
		Map<String,Object> globalParams = this.appProperties.getGlobalParams();
		String commonUrl = globalParams.get("umsUrl").toString();
		String bindUrl = commonUrl+terminalConfigManageEntity.getConfigPlatform()+ UtilConstants.unreadSuffix;
		String dataStr = object.get("data").toString();
		JSONObject data = JSONObject.parseObject(dataStr);

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("userId", data.get("userId")== null ? "": data.get("userId").toString()));
		nvps.add(new BasicNameValuePair("moduleCode",  "INTERNET"));
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(bindUrl);
		httpPost.addHeader("ContentType", "application/json;charset=UTF-8");
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		HttpResponse httpResponse = httpclient.execute(httpPost);
		JSONObject unreadResult = new JSONObject();
		if (httpResponse != null) {
			HttpEntity resEntity = httpResponse.getEntity();
			if (resEntity != null) {
				String result = EntityUtils.toString(resEntity, "utf-8");
				unreadResult = JSONObject.parseObject(result);
			}
		}
		unreadMap.put("unreadResult",unreadResult);
		return unreadMap;
	}
	public TerminalConfigManageEntity getTerminalConfigManageEntity(TerminalBasicEntity terminalBasic,String configType){
		ConcurrentHashMap<String,Object> searchParam = new ConcurrentHashMap();
		searchParam.put("terminalId",terminalBasic.getId());
		searchParam.put("configType", configType);
		List<TerminalConfigManageEntity> terminalConfigManageEntities = terminalConfigManageService.queryAll(searchParam);
		TerminalConfigManageEntity terminalConfigManageEntity = new TerminalConfigManageEntity();
		if(!terminalConfigManageEntities.isEmpty()){
			terminalConfigManageEntity = terminalConfigManageEntities.get(0);
		}
		return terminalConfigManageEntity;
	}

	/**
	 * 描述:  删除统一搜索内的应用信息
	 * @createDate: 2021/12/27 14:22
	 * @author: gaojian
	 * @modify:
	 * @param terminalId
	 * @return: void
	 */
	public void deleteUnionSearchApp(Long terminalId){

		Map<String,Object> searchParams = new HashMap<>(4);
		searchParams.put("terminalId",terminalId);
		List<TerminalApplicationConfigEntity> list = terminalApplicationConfigService.queryAll(searchParams);
		list.forEach(terminalApplicationConfigEntity -> {
			uniSearchService.searchSynchronizationData(terminalApplicationConfigEntity, SearchOperateType.del);
		});
	}
}
