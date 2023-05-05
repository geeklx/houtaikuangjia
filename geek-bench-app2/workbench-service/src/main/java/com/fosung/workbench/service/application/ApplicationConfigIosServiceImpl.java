package com.fosung.workbench.service.application;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.exception.AppException;
import com.fosung.framework.common.secure.auth.AppUserDetails;
import com.fosung.framework.common.support.service.AppBaseDataService;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import com.fosung.workbench.common.AppMessageContent;
import com.fosung.workbench.common.MessageContent;
import com.fosung.workbench.dao.application.ApplicationConfigIosDao;
import com.fosung.workbench.dict.AppType;
import com.fosung.workbench.dict.AuditStatusType;
import com.fosung.workbench.dict.TerminalType;
import com.fosung.workbench.entity.application.ApplicationBaseConfigEntity;
import com.fosung.workbench.entity.application.ApplicationBasicEntity;
import com.fosung.workbench.entity.application.ApplicationConfigIosEntity;
import com.fosung.workbench.entity.application.ApplicationVersionEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 描述:  应用IOS配置信息服务实现类
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Service
public class ApplicationConfigIosServiceImpl extends AppJPABaseDataServiceImpl<ApplicationConfigIosEntity, ApplicationConfigIosDao>
	implements  ApplicationConfigIosService {

	/**
	 * 描述:  注入版本服务
	 * @createDate: 2021/10/27 15:01
	 * @author: gaojian
	 */
	@Autowired
	private ApplicationVersionService applicationVersionService;

	@Autowired
	private ApplicationBasicService applicationBasicService;

	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("appId","appId:EQ");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}


	/**
	 * 描述:  升级APP
	 *
	 * @param applicationConfigIosEntity
	 * @createDate: 2021/10/18 19:24
	 * @author: gaojian
	 * @modify:
	 * @return: void
	 */
	@Override
	public void upgradeApp(ApplicationConfigIosEntity applicationConfigIosEntity) {

		// 1. 查询应用最大版本号
		ApplicationVersionEntity applicationVersion = new ApplicationVersionEntity();
		applicationVersion.setAppId(applicationConfigIosEntity.getAppId());
		applicationVersion.setAppType(TerminalType.ios);

		// 2. 保存版本信息
		applicationVersion.setVersionName(applicationConfigIosEntity.getVersionName());
		applicationVersion.setVersionNum(applicationConfigIosEntity.getVersionNum());

		// 校验包名是否一致
		ApplicationBasicEntity applicationBasicEntity = applicationBasicService.get(applicationConfigIosEntity.getAppId());
		if( applicationBasicEntity == null || !StringUtils.equals(applicationBasicEntity.getPackageName(),applicationConfigIosEntity.getPackageName())){
			throw new AppException(MessageContent.APP_PACKAGE_NAME_INCONFORMITY);
		}
		applicationVersion.setRemark(applicationConfigIosEntity.getRemark());
		applicationVersion.setStartName(applicationConfigIosEntity.getStartName());
		applicationVersion.setStartParams(applicationConfigIosEntity.getStartParams());
		applicationVersion.setVersionSize(applicationConfigIosEntity.getVersionSize());
		applicationVersion.setPackageName(applicationConfigIosEntity.getPackageName());
		applicationVersion.setFileName(applicationConfigIosEntity.getFileName());
		AppUserDetails appUserDetails = appUserDetailsService.getAppUserDetails();
		if( appUserDetails == null){
			throw new AppException(MessageContent.LOGIN_USER_IS_NULL);
		}
		applicationVersion.setCreateUserName(appUserDetails.getUserRealName());
		applicationVersionService.upgrade(applicationVersion);

		// 3. 保存应用配置信息
		applicationConfigIosEntity.setAppVersionId(applicationVersion.getId());
		save(applicationConfigIosEntity);

		// 4. 修改版本表中的配置主键
		applicationVersion.setVersionConfigId(applicationConfigIosEntity.getId());
		applicationVersionService.update(applicationVersion, Arrays.asList("versionConfigId"));
	}

	/**
	 * 描述:  修改信息
	 *
	 * @param applicationConfigIosEntity
	 * @createDate: 2021/10/22 9:00
	 * @author: gaojian
	 * @modify:
	 * @return: void
	 */
	@Override
	public void updateApp(ApplicationConfigIosEntity applicationConfigIosEntity) {

		// 1. 由请求参数中获取需要更新的字段
		Set<String> updateFields = UtilDTO.toDTOExcludeFields(applicationConfigIosEntity, Arrays.asList("id")).keySet();
		update( applicationConfigIosEntity , updateFields ) ;

		// 2. 更新版本表信息
		ApplicationVersionEntity applicationVersion = new ApplicationVersionEntity();
		applicationVersion.setId(applicationConfigIosEntity.getAppVersionId());
		applicationVersion.setVersionSize(applicationConfigIosEntity.getVersionSize());
		applicationVersion.setRemark(applicationConfigIosEntity.getRemark());
		applicationVersion.setStartName(applicationConfigIosEntity.getStartName());
		applicationVersion.setStartParams(applicationConfigIosEntity.getStartParams());
		applicationVersion.setFileName(applicationConfigIosEntity.getFileName());
		applicationVersion.setVersionName(applicationConfigIosEntity.getVersionName());
		applicationVersionService.update(applicationVersion,
				Arrays.asList("versionName","versionSize","remark","startName","startParams","fileName"));
	}

	/**
	 * 描述:  根据应用id查询第一个版本信息
	 *
	 * @param id
	 * @createDate: 2021/10/26 17:14
	 * @author: gaojian
	 * @modify:
	 * @return: com.fosung.workbench.entity.application.ApplicationConfigAndroidEntity
	 */
	@Override
	public ApplicationBaseConfigEntity queryFirstByAppIdOrderByCreateDatetime(Long id) {

		// 1. 非空判断并根据应用id查询第一次发行的版本
		Assert.notNull(id, AppMessageContent.APP_IS_NOT_EXIST);
		return entityDao.queryFirstByAppIdOrderByCreateDatetime(id);
	}
}
