package com.fosung.workbench.service.config;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fosung.workbench.dao.config.ConfigCategoryDao;
import com.fosung.workbench.entity.config.ConfigCategory;
import org.springframework.stereotype.Service;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;

/**
 * 描述:  目录树配置服务实现类
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Service
public class ConfigCategoryServiceImpl extends AppJPABaseDataServiceImpl<ConfigCategory, ConfigCategoryDao>
	implements ConfigCategoryService {
	
	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("id","id:EQ");
				put("categoryName","categoryName:LIKE");
				put("categoryType","categoryType:EQ");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}
	

}
