package com.geek.workbench.service.config;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.geek.workbench.dao.config.ConfigCascadeDictDao;
import com.geek.workbench.entity.config.ConfigCascadeEntity;
import org.springframework.stereotype.Service;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;

/**
 * 描述:  级联配置服务实现类
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Service
public class ConfigCascadeDictServiceImpl extends AppJPABaseDataServiceImpl<ConfigCascadeEntity, ConfigCascadeDictDao>
	implements ConfigCascadeService {
	
	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("id","id:EQ");
				put("parentId","parentId:EQ");
				put("configStatus","configStatus:EQ");
				put("configType","configType:EQ");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}


	/**
	 * 描述:  查询信息
	 *
	 * @param params
	 * @createDate: 2021/11/1 10:37
	 * @author: gaojian
	 * @modify:
	 * @return: java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
	 */
	@Override
	public List<Map<String,Object>> queryInfo(Map<String, Object> params) {

		return queryInfo(params);
	}
}
