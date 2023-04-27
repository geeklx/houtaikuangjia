package com.geek.system.support.system.service.sys;

import java.util.*;

import com.fosung.framework.common.util.UtilCollection;
import com.geek.system.support.system.entity.sys.SysRoleResourceEntity;
import com.geek.system.support.system.dao.sys.SysRoleResourceDao;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;

@Service
public class SysRoleResourceServiceImpl extends AppJPABaseDataServiceImpl<SysRoleResourceEntity, SysRoleResourceDao>
	implements SysRoleResourceService {


	@Autowired
	private SysRoleService sysRoleService;
	
	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("roleId","roleId:EQ");
				put("roleIds","roleId:IN");
				put("resourceId","resourceId:EQ");
				put("resourceIds","resourceId:IN");
				put("projectId","projectId:EQ");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}

	/**
	 * 保存之前进行业务逻辑处理
	 * @param sysRoleResourceEntity
	 */
	@Override
	public void preSaveHandler(SysRoleResourceEntity sysRoleResourceEntity){
		super.preSaveHandler(sysRoleResourceEntity);
		HashMap<String,Object> searchParam= Maps.newHashMap();
		searchParam.put("roleId",sysRoleResourceEntity.getRoleId());
		searchParam.put("resourceId",sysRoleResourceEntity.getResourceId());
		List<SysRoleResourceEntity> sysRoleResourceEntities = this.queryAll(searchParam);
		if(!UtilCollection.sizeIsEmpty(sysRoleResourceEntities)){
			Set<Long> ids = UtilCollection.extractToSet(sysRoleResourceEntities,"id",Long.class);
			this.delete(ids);
		}
	}

	/**
	 * 根据角色查询资源
	 * @param roleId
	 * @return
	 */
	public List<SysRoleResourceEntity> getRoleResourceByRole(Long roleId){
		HashMap<String,Object> searchParam = Maps.newHashMap();
		searchParam.put("roleId",roleId);
		return queryAll(searchParam);
	}



}
