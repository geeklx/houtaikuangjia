package com.fosung.workbench.service.common;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fosung.workbench.dao.common.ManageOptLogDao;
import com.fosung.workbench.entity.common.ManageOptLogEntity;
import org.springframework.stereotype.Service;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;

/**
 * @Description 操作日志记录服务实现类
 * @Author gaojian
 * @Date 2021/10/15 8:25
 * @Version V1.0
 */
@Service
public class ManageOptLogServiceImpl extends AppJPABaseDataServiceImpl<ManageOptLogEntity, ManageOptLogDao>
	implements ManageOptLogService {
	
	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("optModule","optModule:LIKE");
				put("optName","optName:LIKE");
				put("optBusinessId","optBusinessId:LIKE");
				put("startTime", "createDatetime:GTEDATE");
				put("endTime", "createDatetime:LTEDATE");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}
	

}
