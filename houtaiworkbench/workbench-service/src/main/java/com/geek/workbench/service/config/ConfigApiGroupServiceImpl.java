package com.geek.workbench.service.config;

import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import com.geek.workbench.dao.config.ConfigApiGroupDao;
import com.geek.workbench.entity.config.ConfigApiGroupEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 描述:  接口组配置服务实现类
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Service
public class ConfigApiGroupServiceImpl extends AppJPABaseDataServiceImpl<ConfigApiGroupEntity, ConfigApiGroupDao>
	implements ConfigApiGroupService {

	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("id","id:EQ");
				put("apiName","apiName:LIKE");
				put("apiType","apiType:EQ");
				put("apiGroupId","apiGroupId:EQ");
				put("apiCategory","apiCategory:EQ");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}

}
