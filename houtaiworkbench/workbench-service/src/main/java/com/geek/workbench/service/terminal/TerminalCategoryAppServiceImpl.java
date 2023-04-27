package com.geek.workbench.service.terminal;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.fosung.framework.common.util.UtilString;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.workbench.config.TerminalContent;
import com.geek.workbench.dao.terminal.TerminalCategoryAppDao;
import com.geek.workbench.dto.terminal.TerminalCategoryAppSaveDto;
import com.geek.workbench.entity.terminal.TerminalCategoryAppEntity;
import com.geek.workbench.util.CacheUtil;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;

import javax.transaction.Transactional;

@Service
public class TerminalCategoryAppServiceImpl extends AppJPABaseDataServiceImpl<TerminalCategoryAppEntity, TerminalCategoryAppDao>
	implements TerminalCategoryAppService {

	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("appConfigId","appConfigId:EQ");
				put("terminalId","terminalId:EQ");
				put("categoryCode","categoryCode:EQ");
				put("categoryType","categoryType:EQ");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}


	/**
	 *终端应用分类配置缓存
	 *
	 * @author liuke
	 * @date 2021/10/15 14:56
	 * @return
	 */
	public LoadingCache<Map,Set<Long>> terminalCategoryCache = CacheUtil.getInstance().build(
			new CacheLoader<Map,Set<Long>>() {
				@Override
				public Set<Long> load(Map key) throws Exception {
					Map<String,Object> searchParam = Maps.newHashMap();
					searchParam.put("terminalId",key.get("terminalId"));
					searchParam.put("categoryCode",key.get("code"));
					return queryAll(searchParam).stream().map(ca -> ca.getAppConfigId()).collect(Collectors.toSet());
				}
			}
	);


	/**
	 *
	 * 根据终端获取应用
	 * @param terminalId
	 * @author liuke
	 * @date 2021/10/18 10:05
	 * @return java.util.List<com.fosung.workbench.entity.terminal.TerminalApplicationConfigEntity>
	 */
	@Override
	public Set<Long> getCacheAppByTerminal(Long terminalId,String code){
		Set<Long> ids = Sets.newHashSet();
		try {
			if(terminalId==null||UtilString.isBlank(code)){
				return ids;
			}
			Map<String,Object> map = Maps.newHashMap();
			map.put("terminalId",terminalId);
			map.put("code",code);
			ids = terminalCategoryCache.get(map);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return ids;
	}

	/**
	 * 描述: 保存工作台分类绑定应用信息
	 * @param terminalCategoryAppSaveDto
	 * @return com.fosung.framework.web.http.ResponseParam
	 * @author fuhao
	 * @date 2021/10/27 16:45
	 **/
	@Override
	@Transactional
	public ResponseParam saveInfo(TerminalCategoryAppSaveDto terminalCategoryAppSaveDto) {

		if(terminalCategoryAppSaveDto.getCategoryCode() == null){
			return ResponseParam.fail().message(TerminalContent.CHECK_CATEGORY_CODE);
		}
		// 保存前先逻辑删除
		Map<String, Object> delParam = new HashMap<>();
		delParam.put(TerminalContent.CATEGORY_CODE,terminalCategoryAppSaveDto.getCategoryCode());
		entityDao.deleteByExample(delParam);

		// 保存
		for (TerminalCategoryAppEntity categoryApp : terminalCategoryAppSaveDto.getList()) {
			categoryApp.setAppConfigId(categoryApp.getConfigId());
			if(terminalCategoryAppSaveDto.getDisabledNum() == null){
				categoryApp.setDisabledNum(false);
			}else {
				categoryApp.setDisabledNum(terminalCategoryAppSaveDto.getDisabledNum());
			}
			this.save(categoryApp);
		}
		return ResponseParam.saveSuccess();
	}
}
