package com.geek.workbench.service.terminal;

import java.util.LinkedHashMap;
import java.util.Map;

import com.geek.workbench.dao.terminal.TerminalNavigationStrategyDao;
import com.geek.workbench.entity.terminal.TerminalNavigationStrategy;
import org.springframework.stereotype.Service;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;

/**
 * 描述:  终端导航策略服务实现层
 * @createDate: 2021/11/05 15:06
 * @author: gaojian
 * @version V1.0
 */
@Service
public class TerminalNavigationStrategyServiceImpl extends AppJPABaseDataServiceImpl<TerminalNavigationStrategy, TerminalNavigationStrategyDao>
	implements TerminalNavigationStrategyService {
	
	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("navigationBtmId","navigationBtmId:EQ");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}
	

}
