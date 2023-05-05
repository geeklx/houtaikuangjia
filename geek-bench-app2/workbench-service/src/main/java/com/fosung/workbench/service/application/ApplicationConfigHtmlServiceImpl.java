package com.fosung.workbench.service.application;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.exception.AppException;
import com.fosung.framework.common.secure.auth.AppUserDetails;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import com.fosung.workbench.common.AppMessageContent;
import com.fosung.workbench.common.MessageContent;
import com.fosung.workbench.dao.application.ApplicationConfigHtmlDao;
import com.fosung.workbench.dict.TerminalType;
import com.fosung.workbench.entity.application.ApplicationBaseConfigEntity;
import com.fosung.workbench.entity.application.ApplicationConfigHtmlEntity;
import com.fosung.workbench.entity.application.ApplicationVersionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 描述:  应用H5配置信息服务实现类
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Service
public class ApplicationConfigHtmlServiceImpl extends AppJPABaseDataServiceImpl<ApplicationConfigHtmlEntity, ApplicationConfigHtmlDao>
	implements ApplicationConfigHtmlService {

	/**
	 * 描述:  注入版本服务
	 * @createDate: 2021/10/27 15:01
	 * @author: gaojian
	 */
	@Autowired
	private ApplicationVersionService applicationVersionService;

	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("appId","appId:EQ");
				put("appVersionId","appVersionId:EQ");
				put("frontUrl","frontUrl:EQ");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}


	/**
	 * 描述:  升级APP
	 *
	 * @param applicationConfigHtmlEntity
	 * @createDate: 2021/10/18 19:24
	 * @author: gaojian
	 * @modify:
	 * @return: void
	 */
	@Override
	public void upgradeApp(ApplicationConfigHtmlEntity applicationConfigHtmlEntity) {

		// 1. 查询应用最大版本号
		ApplicationVersionEntity applicationVersion = new ApplicationVersionEntity();
		applicationVersion.setAppId(applicationConfigHtmlEntity.getAppId());
		applicationVersion.setAppType(TerminalType.h5);
//		Integer maxVersionNum = applicationVersionService.queryMaxVersionNum(applicationVersion);

		// 2. 保存版本信息
		applicationVersion.setVersionName(applicationConfigHtmlEntity.getVersionName());
		applicationVersion.setVersionNum(applicationConfigHtmlEntity.getVersionNum());
//		if(applicationConfigHtmlEntity.getVersionNum() == null ){
//			applicationVersion.setVersionNum(maxVersionNum + 1);
//		}
		applicationVersion.setRemark(applicationConfigHtmlEntity.getRemark());
		applicationVersion.setStartName(applicationConfigHtmlEntity.getStartName());
		applicationVersion.setStartParams(applicationConfigHtmlEntity.getStartParams());
		applicationVersion.setPackageName(applicationConfigHtmlEntity.getPackageName());
		applicationVersion.setVersionSize(applicationConfigHtmlEntity.getVersionSize());
		applicationVersion.setFileName(applicationConfigHtmlEntity.getFileName());
		AppUserDetails appUserDetails = appUserDetailsService.getAppUserDetails();
		if( appUserDetails == null){
			throw new AppException(MessageContent.LOGIN_USER_IS_NULL);
		}
		applicationVersion.setCreateUserName(appUserDetails.getUserRealName());
		applicationVersionService.upgrade(applicationVersion);

		// 3. 保存应用配置信息
		applicationConfigHtmlEntity.setAppVersionId(applicationVersion.getId());
		save(applicationConfigHtmlEntity);

		// 4. 修改版本表中的配置主键
		applicationVersion.setVersionConfigId(applicationConfigHtmlEntity.getId());
		applicationVersionService.update(applicationVersion, Arrays.asList("versionConfigId"));
	}

	/**
	 * 描述:  修改信息
	 *
	 * @param applicationConfigHtmlEntity
	 * @createDate: 2021/10/22 9:00
	 * @author: gaojian
	 * @modify:
	 * @return: void
	 */
	@Override
	public void updateApp(ApplicationConfigHtmlEntity applicationConfigHtmlEntity) {

		// 1. 由请求参数中获取需要更新的字段
		Set<String> updateFields = UtilDTO.toDTOExcludeFields(applicationConfigHtmlEntity, Arrays.asList("id")).keySet();
		update( applicationConfigHtmlEntity , updateFields ) ;

		// 2. 更新版本表信息
		ApplicationVersionEntity applicationVersion = new ApplicationVersionEntity();
		applicationVersion.setId(applicationConfigHtmlEntity.getAppVersionId());
		applicationVersion.setVersionSize(applicationConfigHtmlEntity.getVersionSize());
		applicationVersion.setRemark(applicationConfigHtmlEntity.getRemark());
		applicationVersion.setStartName(applicationConfigHtmlEntity.getStartName());
		applicationVersion.setStartParams(applicationConfigHtmlEntity.getStartParams());
		applicationVersion.setFileName(applicationConfigHtmlEntity.getFileName());
		applicationVersion.setVersionName(applicationConfigHtmlEntity.getVersionName());
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

		Assert.notNull(id, AppMessageContent.APP_IS_NOT_EXIST);
		return entityDao.queryFirstByAppIdOrderByCreateDatetime(id);
	}

}
