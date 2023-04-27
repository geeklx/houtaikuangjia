package com.geek.system.support.system.service.sys;

import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import com.geek.system.support.system.dao.sys.SysRoleAppDao;
import com.geek.system.support.system.entity.sys.SysRoleAppEntity;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SysRoleAppServiceImpl extends AppJPABaseDataServiceImpl<SysRoleAppEntity, SysRoleAppDao>
	implements SysRoleAppService {


	@Autowired
	private SysRoleService sysRoleService;
	
	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("roleId","roleId:EQ");
				put("roleIds","roleId:IN");
				put("appId","appId:EQ");
				put("appIds","appIds:IN");
				put("projectId","projectId:EQ");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}

//	@Override
//	public void preSaveHandler(SysRoleResourceEntity sysRoleResourceEntity){
//		super.preSaveHandler(sysRoleResourceEntity);
//		HashMap<String,Object> searchParam= Maps.newHashMap();
//		searchParam.put("roleId",sysRoleResourceEntity.getRoleId());
//		searchParam.put("resourceId",sysRoleResourceEntity.getResourceId());
//		List<SysRoleResourceEntity> sysRoleResourceEntities = this.queryAll(searchParam);
//		if(!UtilCollection.sizeIsEmpty(sysRoleResourceEntities)){
//			Set<Long> ids = UtilCollection.extractToSet(sysRoleResourceEntities,"id",Long.class);
//			this.delete(ids);
//		}
//	}

	@Override
	public List<SysRoleAppEntity> getRoleAppByRole(Long roleId){
		HashMap<String,Object> searchParam = Maps.newHashMap();
		searchParam.put("roleId",roleId);
		return queryAll(searchParam);
	}



}
