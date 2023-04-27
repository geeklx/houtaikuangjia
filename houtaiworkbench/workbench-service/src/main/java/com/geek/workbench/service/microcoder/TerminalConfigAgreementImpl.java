package com.geek.workbench.service.microcoder;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.geek.workbench.dao.microcoder.TerminalConfigAgreementDao;
import com.geek.workbench.entity.microcoder.TerminalConfigAgreementEntity;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;

@Service
public class TerminalConfigAgreementImpl extends AppJPABaseDataServiceImpl<TerminalConfigAgreementEntity, TerminalConfigAgreementDao>
	implements TerminalConfigAgreementService {
	
	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("terminalId","terminalId:EQ");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}


	/**
	 * 根据终端id获取缓存中的配置
	 * @param terminalId
	 * @return
	 */
	@Override
	public List<TerminalConfigAgreementEntity> getTerminalConfigAgreements(Long terminalId){
		Map<String,Object> searchParam = Maps.newHashMap();
		searchParam.put("terminalId",terminalId);
		return queryAll(searchParam);
	}


}
