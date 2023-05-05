package com.fosung.system.support.system.service.sys;


import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import com.fosung.system.support.system.dao.sys.SysRoleScopDao;
import com.fosung.system.support.system.entity.sys.*;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SysRoleScopServiceImpl extends AppJPABaseDataServiceImpl<SysRoleScopEntity, SysRoleScopDao>
	implements SysRoleScopService {

	
	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("projectId","projectId:EQ");
				put("roleId","roleId:EQ");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}



}
