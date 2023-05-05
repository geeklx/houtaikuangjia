package com.fosung.system.support.system.service.sys;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fosung.framework.common.util.UtilCollection;
import com.fosung.system.support.system.dao.sys.SysApplicationDao;
import com.fosung.system.support.system.entity.sys.SysApplicationEntity;
import com.fosung.system.support.system.entity.sys.SysProjectApp;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;

@Service
public class SysApplicationServiceImpl extends AppJPABaseDataServiceImpl<SysApplicationEntity, SysApplicationDao>
	implements SysApplicationService {

	private final static String APP_CODE = "fs-user-sys-";

	@Autowired
	private SysProjectAppService sysProjectAppService;

	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("appCode","appCode:LIKE");
				put("appName","appName:LIKE");
				put("categoryId","categoryId:EQ");
				put("startTime", "createDatetime:GTEDATE");
				put("endTime", "createDatetime:LTEDATE");
				put("id","id:EQ");
				put("ids","id:IN");
				put("clientId","clientId:EQ");
				put("status","status:EQ");
				put("del","del:EQ");
				put("orgType","orgType:LIKE");
				put("appType","appType:EQ");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}


	/**
	 *保存前判断是否有应用编码，如果没有自动生成
	 *
	 * @param entity
	 * @author liuke
	 * @date 2021/11/30 15:07
	 * @return void
	 */
	@Override
	public void preSaveHandler(SysApplicationEntity entity) {
		if(entity.getAppCode() == null){
			entity.setAppCode(APP_CODE+ System.currentTimeMillis());
		}
		super.preSaveHandler(entity);
	}

	/**
	 *查询项目绑定全部应用
	 *
	 * @param projectId
	 * @author liuke
	 * @date 2021/12/3 14:48
	 * @return java.util.List<com.fosung.system.support.system.entity.sys.SysApplicationEntity>
	 */
	@Override
	public List<SysApplicationEntity> selectByProjectId(Long projectId){
		return entityDao.selectByProjectId(projectId);
	}

	@Override
	public void deleteInfo(List<Long> collect) {
		// 删除应用系统
		this.delete(collect);
		// 查询租户与应用之间关系
		Map<String, Object> projectAppExist = Maps.newHashMap();
		projectAppExist.put("appIds",collect);
		List<SysProjectApp> sysProjectApps = sysProjectAppService.queryAll(projectAppExist);
		// 如果存在则删除关联关系
		if(UtilCollection.isNotEmpty(sysProjectApps)){
			sysProjectAppService.delete(sysProjectApps.stream().map(map -> map.getId()).collect(Collectors.toList()));
		}
	}
}
