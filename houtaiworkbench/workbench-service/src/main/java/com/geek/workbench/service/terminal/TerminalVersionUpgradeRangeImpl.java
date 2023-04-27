package com.geek.workbench.service.terminal;

import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import com.geek.workbench.dao.terminal.TerminalVersionUpgradeRangeDao;
import com.geek.workbench.entity.terminal.TerminalVersionUpgradeRangeEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述:  终端版本更新范围服务
 * @createDate: 2022/2/23 17:18
 * @author: gaojian
 */
@Service
public class TerminalVersionUpgradeRangeImpl extends AppJPABaseDataServiceImpl<TerminalVersionUpgradeRangeEntity, TerminalVersionUpgradeRangeDao>
	implements TerminalVersionUpgradeRangeService {
	
	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("terminalVersionId","terminalVersionId:EQ");
				put("updateIndex","updateIndex:EQ");
				put("del","del:BOOLEANQE");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}

	/**
	 * 描述:  删除版本历史范围变更记录
	 * @createDate: 2022/2/23 17:18
	 * @author: gaojian
	 * @modify:
	 * @param id
	 * @return: java.lang.Integer
	 */
	@Override
	public Integer deleteHistoryLog(Long id) {
		return entityDao.deleteHistoryLog(id);
	}

	/**
	 * 描述:  查询真实的全部
	 *
	 * @param searchParams
	 * @createDate: 2022/2/23 18:05
	 * @author: gaojian
	 * @modify:
	 * @return: java.util.List<com.fosung.workbench.entity.terminal.TerminalVersionUpgradeRangeEntity>
	 */
	@Override
	public List<TerminalVersionUpgradeRangeEntity> queryRealAll(Map<String, Object> searchParams) {
		return entityDao.queryRealAll(searchParams);
	}

}
