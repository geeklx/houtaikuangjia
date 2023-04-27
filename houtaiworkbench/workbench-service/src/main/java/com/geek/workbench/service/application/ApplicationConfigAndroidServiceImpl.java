package com.geek.workbench.service.application;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.exception.AppException;
import com.fosung.framework.common.secure.auth.AppUserDetails;
import com.fosung.framework.common.secure.auth.AppUserDetailsService;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import com.geek.workbench.AppBean.ApkBasic;
import com.geek.workbench.common.AppMessageContent;
import com.geek.workbench.common.MessageContent;
import com.geek.workbench.dao.application.ApplicationConfigAndroidDao;
import com.geek.workbench.dict.TerminalType;
import com.geek.workbench.entity.application.ApplicationBaseConfigEntity;
import com.geek.workbench.entity.application.ApplicationConfigAndroidEntity;
import com.geek.workbench.entity.application.ApplicationVersionEntity;
import com.geek.workbench.entity.terminal.TerminalVersionEntity;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 描述:  应用Android配置信息服务实现类
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Service
public class ApplicationConfigAndroidServiceImpl extends AppJPABaseDataServiceImpl<ApplicationConfigAndroidEntity, ApplicationConfigAndroidDao>
	implements ApplicationConfigAndroidService {

	@Autowired
	private ApplicationVersionService applicationVersionService;

	/**
	 * 描述:  注入登录用户详情服务类
	 * @createDate: 2021/10/13 18:03
	 * @author: gaojian
	 */
	@Autowired
	private AppUserDetailsService appUserDetailsService ;
	
	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("appId","appId:EQ");
				put("bundleId","bundleId:EQ");
				put("appVersionId","appVersionId:EQ");
				put("del","del:EQ");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}


	/**
	 * 描述:  升级APP
	 *
	 * @param applicationConfigAndroidEntity
	 * @createDate: 2021/10/18 19:24
	 * @author: gaojian
	 * @modify:
	 * @return: void
	 */
	@Override
	public void upgradeApp(ApplicationConfigAndroidEntity applicationConfigAndroidEntity) {

		// 1. 基本信息
		ApplicationVersionEntity applicationVersion = new ApplicationVersionEntity();
		applicationVersion.setAppId(applicationConfigAndroidEntity.getAppId());
		applicationVersion.setAppType(TerminalType.android);

		// 2. 保存版本信息
		applicationVersion.setRemark(applicationConfigAndroidEntity.getRemark());
		applicationVersion.setVersionName(applicationConfigAndroidEntity.getVersionName());
		applicationVersion.setVersionNum(applicationConfigAndroidEntity.getVersionNum());
		applicationVersion.setVersionSize(applicationConfigAndroidEntity.getVersionSize());
		applicationVersion.setPackageName(applicationConfigAndroidEntity.getPackageName());
		applicationVersion.setStartName(applicationConfigAndroidEntity.getStartName());
		applicationVersion.setStartParams(applicationConfigAndroidEntity.getStartParams());
		applicationVersion.setFileName(applicationConfigAndroidEntity.getFileName());
		AppUserDetails appUserDetails = appUserDetailsService.getAppUserDetails();
		if( appUserDetails == null){
			throw new AppException(MessageContent.LOGIN_USER_IS_NULL);
		}
		applicationVersion.setCreateUserName(appUserDetails.getUserRealName());
		applicationVersionService.upgrade(applicationVersion);

		// 3. 保存应用配置信息
		applicationConfigAndroidEntity.setAppVersionId(applicationVersion.getId());
		save(applicationConfigAndroidEntity);

		// 4. 修改版本表中的配置主键
		applicationVersion.setVersionConfigId(applicationConfigAndroidEntity.getId());
		applicationVersionService.update(applicationVersion, Arrays.asList("versionConfigId"));
	}

	/**
	 * 描述:  修改信息
	 *
	 * @param applicationConfigAndroidEntity
	 * @createDate: 2021/10/22 9:00
	 * @author: gaojian
	 * @modify:
	 * @return: void
	 */
	@Override
	public void updateApp(ApplicationConfigAndroidEntity applicationConfigAndroidEntity) {

		// 1. 由请求参数中获取需要更新的字段
		Set<String> updateFields = UtilDTO.toDTOExcludeFields(applicationConfigAndroidEntity, Arrays.asList("id")).keySet();
		update( applicationConfigAndroidEntity , updateFields ) ;

		// 2. 更新版本表信息
		ApplicationVersionEntity applicationVersion = new ApplicationVersionEntity();
		applicationVersion.setId(applicationConfigAndroidEntity.getAppVersionId());
		applicationVersion.setVersionSize(applicationConfigAndroidEntity.getVersionSize());
		applicationVersion.setRemark(applicationConfigAndroidEntity.getRemark());
		applicationVersion.setVersionName(applicationConfigAndroidEntity.getVersionName());
		applicationVersion.setVersionNum(applicationConfigAndroidEntity.getVersionNum());
		applicationVersion.setStartName(applicationConfigAndroidEntity.getStartName());
		applicationVersion.setStartParams(applicationConfigAndroidEntity.getStartParams());
		applicationVersion.setFileName(applicationConfigAndroidEntity.getFileName());
		applicationVersion.setVersionName(applicationConfigAndroidEntity.getVersionName());
		applicationVersionService.update(applicationVersion,
				Arrays.asList("versionName","versionNum","versionSize"
						,"remark","startName","startParams","fileName"));
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

	@Override
	public ApkBasic getInfo(TerminalVersionEntity terminalVersion, ApplicationConfigAndroidEntity androidEntity, Map p) {
		ApkBasic infoNew = new ApkBasic();
		infoNew.setServerVersionCode(p.get("versionCode") == null ? "" : p.get("versionCode").toString());
		infoNew.setServerVersionName(p.get("versionName") == null? "" : p.get("versionName").toString());
		infoNew.setAppPackageName(p.get("appPackageName") == null?"":p.get("appPackageName").toString());
		infoNew.setApplicationLable(p.get("appName")== null ? "":p.get("appName").toString());
		infoNew.setApkPath(terminalVersion.getInstallationPackage() == null ? "" : terminalVersion.getInstallationPackage());
		infoNew.setMd5(p.get("appPackageName") == null ? "" : DigestUtils.md5Hex(p.get("appPackageName") .toString()));
		infoNew.setUpdateInfoTitle("应用版本更新");
		infoNew.setUpdateInfo(androidEntity.getRemark()==null?"修复了一些已知问题":androidEntity.getRemark());
		infoNew.setIsForce(false);
		infoNew.setUpgradeBackImg("");
		return infoNew;
	}
}
