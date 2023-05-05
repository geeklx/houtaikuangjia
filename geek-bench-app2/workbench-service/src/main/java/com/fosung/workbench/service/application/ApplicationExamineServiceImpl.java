package com.fosung.workbench.service.application;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fosung.framework.common.support.service.AppBaseDataService;
import com.fosung.workbench.dao.application.ApplicationExamineDao;
import com.fosung.workbench.entity.application.ApplicationExamineEntity;
import org.springframework.stereotype.Service;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;

@Service
public class ApplicationExamineServiceImpl extends AppJPABaseDataServiceImpl<ApplicationExamineEntity, ApplicationExamineDao>
	implements ApplicationExamineService {
	
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
