package com.geek.workbench.service.config;

import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import com.geek.workbench.dao.config.ConfigApiParamsDao;
import com.geek.workbench.entity.config.ConfigApiParamsEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 描述:  接口配置服务实现类
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Service
public class ConfigApiParamsServiceImpl extends AppJPABaseDataServiceImpl<ConfigApiParamsEntity, ConfigApiParamsDao>
	implements ConfigApiParamsService {
	
	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}
	

}
