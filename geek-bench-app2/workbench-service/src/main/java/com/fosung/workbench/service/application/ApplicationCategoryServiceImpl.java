package com.fosung.workbench.service.application;

import com.fosung.framework.common.support.service.AppBaseDataService;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import com.fosung.workbench.dao.application.ApplicationCategoryDao;
import com.fosung.workbench.dao.application.ApplicationConfigAndroidDao;
import com.fosung.workbench.entity.application.ApplicationCategoryEntity;
import com.fosung.workbench.entity.application.ApplicationConfigAndroidEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class ApplicationCategoryServiceImpl extends AppJPABaseDataServiceImpl<ApplicationCategoryEntity, ApplicationCategoryDao>
	implements ApplicationCategoryService{
	
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


	@Service
	public static class ApplicationConfigAndroidServiceImpl extends AppJPABaseDataServiceImpl<ApplicationConfigAndroidEntity, ApplicationConfigAndroidDao>
		implements AppBaseDataService<ApplicationConfigAndroidEntity, Long> {

		/**
		 * 查询条件表达式
		 */
		private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
				{
					put("appId","appId:EQ");
			   }
		};

		@Override
		public Map<String, String> getQueryExpressions() {
			return expressionMap ;
		}


	}
}
