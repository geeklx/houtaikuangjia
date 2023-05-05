package com.fosung.system.support.system.service.sys;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fosung.system.support.system.dao.sys.SysProjectAppDao;
import com.fosung.system.support.system.entity.sys.SysProjectApp;
import org.springframework.stereotype.Service;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;

@Service
public class SysProjectAppServiceImpl extends AppJPABaseDataServiceImpl<SysProjectApp, SysProjectAppDao>
	implements SysProjectAppService {
	
	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("projectIds","projectId:IN");
				put("appIds","appId:IN");
				put("del","del:EQ");
				put("projectId","projectId:EQ");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}

	/**
	 * 描述: 通过应用id查询租户信息
	 * @param searchParam
	 * @return java.util.List<com.fosung.system.support.system.entity.sys.SysProjectApp>
	 * @author fuhao
	 * @date 2021/12/31 10:12
	 **/
	@Override
	public List<SysProjectApp> queryInfo(HashMap<String, Object> searchParam) {
		return entityDao.queryInfo(searchParam);
	}
}
