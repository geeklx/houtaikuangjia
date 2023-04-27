package com.geek.system.support.system.service.sys;

import java.util.LinkedHashMap;
import java.util.Map;

import com.geek.system.support.system.dao.sys.SysApplicationCategoryDao;
import com.geek.system.support.system.entity.sys.SysApplicationCategoryEntity;
import org.springframework.stereotype.Service;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;

@Service
public class SysApplicationCategoryServiceImpl extends AppJPABaseDataServiceImpl<SysApplicationCategoryEntity, SysApplicationCategoryDao>
	implements SysApplicationCategoryService {
	
	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("categoryName","categoryName:EQ");
				put("categoryType","categoryType:EQ");
				put("id","id:EQ");
				put("ids","id:IN");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}
	

}
