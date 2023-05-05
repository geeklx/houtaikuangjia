package com.fosung.workbench.service.terminal;

import java.util.*;
import java.util.concurrent.ExecutionException;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.util.UtilTree;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.workbench.config.TerminalContent;
import com.fosung.workbench.dao.terminal.TerminalConfigCategoryDao;
import com.fosung.workbench.dict.StatusType;
import com.fosung.workbench.dict.TerminalAppCategoryType;
import com.fosung.workbench.dto.config.AppSearchParamDto;
import com.fosung.workbench.dto.terminal.TerminalCategoryAppDto;
import com.fosung.workbench.entity.microcoder.TerminalConfigNavigationBtmEntity;
import com.fosung.workbench.entity.terminal.TerminalApplicationConfigEntity;
import com.fosung.workbench.entity.terminal.TerminalConfigCategoryEntity;
import com.fosung.workbench.service.microcoder.TerminalConfigNavigationBtmService;
import com.fosung.workbench.util.CacheUtil;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TerminalConfigCategoryServiceImpl extends AppJPABaseDataServiceImpl<TerminalConfigCategoryEntity, TerminalConfigCategoryDao>
	implements TerminalConfigCategoryService {

	@Autowired
	private TerminalApplicationConfigService applicationConfigService;
	@Autowired
	private TerminalConfigNavigationBtmService terminalConfigNavigationBtmService;
	
	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("terminalId","terminalId:EQ");
				put("status","status:EQ");
				put("type","type:EQ");
				put("area","area:LIKE");
				put("code","code:EQ");
				put("navigationBtmId","navigationBtmId:EQ");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}

	/**
	 * 功能描述: 最新排序
	 *
	 * @param terminalConfigCategory
	 * @return java.lang.Integer
	 * @author fuhao
	 * @date 2021/10/21 17:56
	 */
	@Override
	public Integer getMaxNum(TerminalConfigCategoryEntity terminalConfigCategory) {
		return entityDao.getMaxNum(terminalConfigCategory);
	}

	/**
	 * 功能描述: 获取分类绑定的应用与未绑定的应用
	 *
	 * @param terminalConfigCategory
	 * @return ResponseParam
	 * @author fuhao
	 * @date 2021/10/21 17:56
	 */
	@Override
	public ResponseParam queryCategoryApp(TerminalCategoryAppDto terminalConfigCategory) {
		if(terminalConfigCategory.getTerminalId()==null){
			return ResponseParam.fail().message(TerminalContent.CHECK_TERMINAL_ID);
		}
		// 未选中的集合
		List<TerminalApplicationConfigEntity> unCheckListResult = new ArrayList<>();
		// 查询绑定的全部绑定应用
		HashMap<String, Object> allParam = new HashMap<>();
		allParam.put("terminalId",terminalConfigCategory.getTerminalId());
		allParam.put("status", StatusType.release.getValue());
		List<TerminalApplicationConfigEntity> allApp = applicationConfigService.queryAll(allParam,new String[]{"num"});
		// 分类类型已经绑定的信息
		allParam.put("categoryType",terminalConfigCategory.getCategoryType());
		//allParam.put("categoryCode",terminalConfigCategory.getCategoryCode());
		List<TerminalApplicationConfigEntity> categoryTypeApp = entityDao.queryCategoryBindApp(allParam);
		// 选中的集合 -> 防止空指针
		List<TerminalApplicationConfigEntity> checkTypeList = categoryTypeApp == null && categoryTypeApp.size() == 0?
				new ArrayList<>():categoryTypeApp;
//		// 过滤未选中的应用 -> 未选中 = 终端绑定 - 分类绑定
		for(TerminalApplicationConfigEntity all: allApp){
			boolean flag = true;
			for(TerminalApplicationConfigEntity checked: checkTypeList){
				if(!terminalConfigCategory.getCategoryCode().equals(checked.getCategoryCode())){
					if(checked.getAppVersionId().equals(all.getAppVersionId())){
						flag = false;
						break;
					}
				}else {
					if(checked.getAppVersionId().equals(all.getAppVersionId())){
						break;
					}
				}
//				if(all.getTerminalId().equals(checked.getTerminalId())){
//					if(terminalConfigCategory.getCategoryType().equals(checked.getCategoryType())){
//						if(!terminalConfigCategory.getCategoryCode().equals(checked.getCategoryCode())){
//							if(checked.getAppVersionId().equals(all.getAppVersionId())){
//								flag = false;
//								break;
//							}
//						}
//					}
//				}

			}
			if(flag){
				unCheckListResult.add(all);
			}
		}
		// 分类选中的app
		allParam.put("categoryCode",terminalConfigCategory.getCategoryCode());
		List<TerminalApplicationConfigEntity> checkListResult = entityDao.queryCategoryBindApp(allParam);
		// 返回结果
		Map<String,Object> result = new HashMap();
		result.put("uncheck",unCheckListResult);
		result.put("checked",checkListResult);
		if(checkListResult.size() > 0){
			result.put("disabledNum",checkListResult.get(0).getDisabledNum());
		}else {
			result.put("disabledNum",false);
		}
		return ResponseParam.success().data(result);
	}

	/**
	 *终端应用分类配置缓存
	 *
	 * @author liuke
	 * @date 2021/10/15 14:56
	 * @return
	 */
	public LoadingCache<AppSearchParamDto,List<TerminalConfigCategoryEntity>> terminalCategoryCache = CacheUtil.getInstance().build(
			new CacheLoader<AppSearchParamDto,List<TerminalConfigCategoryEntity>>() {
				@Override
				public List<TerminalConfigCategoryEntity> load(AppSearchParamDto shelvesSearchParamDto) throws Exception {
					//全部应用
					List listAll = new ArrayList();
					Map<String,Object> searchParam = Maps.newHashMap();
					searchParam.put("terminalId",shelvesSearchParamDto.getTerminalId());
					searchParam.put("status",true);
					searchParam.put("type",TerminalAppCategoryType.all);

					listAll = queryAll(searchParam,new String[]{"num"});

					//常规应用
					List listRoutine = new ArrayList();
					searchParam.put("terminalId",shelvesSearchParamDto.getTerminalId());
					searchParam.put("status",true);
					searchParam.put("type",TerminalAppCategoryType.routine);
					if(StringUtils.isNotBlank(shelvesSearchParamDto.getNavigationBtmId())){
						searchParam.put("navigationBtmId",shelvesSearchParamDto.getNavigationBtmId());
					}
					listRoutine = queryAll(searchParam,new String[]{"num"});
                    if(!listRoutine.isEmpty()){
						listAll.addAll(listRoutine);
					}
					//我的应用
					List listMe = new ArrayList();
					searchParam = Maps.newHashMap();
					searchParam.put("terminalId",shelvesSearchParamDto.getTerminalId());
					searchParam.put("status",true);
					searchParam.put("type",TerminalAppCategoryType.me);
					listMe= queryAll(searchParam,new String[]{"num"});
					if(!listMe.isEmpty()){
						listAll.addAll(listMe);
					}
                    //本地
					List listRegion = new ArrayList();
					searchParam = Maps.newHashMap();
					searchParam.put("terminalId",shelvesSearchParamDto.getTerminalId());
					searchParam.put("status",true);
					searchParam.put("type",TerminalAppCategoryType.regional);
					if(StringUtils.isNotBlank(shelvesSearchParamDto.getNavigationBtmId())){
						searchParam.put("navigationBtmId",shelvesSearchParamDto.getNavigationBtmId());
					}
					if(null != shelvesSearchParamDto.getCityName() && !"".equals(shelvesSearchParamDto.getCityName())){
						if(shelvesSearchParamDto.getCityName().contains("市")){
							searchParam.put("area",shelvesSearchParamDto.getCityName().split("市")[0]);
						}else{
							searchParam.put("area",shelvesSearchParamDto.getCityName());
						}

						listRegion = queryAll(searchParam,new String[]{"num"});
						if(!listRegion.isEmpty()){
							listAll.addAll(listRegion);
						}
					}else{
						listRegion = queryAll(searchParam,new String[]{"num"});
						if(!listRegion.isEmpty()){
							listAll.addAll(listRegion);
						}
					}
					return listAll;
				}
			}
	);


	/**
	 *
	 * 根据终端获取应用
	 * @param shelvesSearchParamDto
	 * @author liuke
	 * @date 2021/10/18 10:05
	 * @return java.util.List<com.fosung.workbench.entity.terminal.TerminalApplicationConfigEntity>
	 */
	@Override
	public List<TerminalConfigCategoryEntity> getCacheAppByTerminal(AppSearchParamDto shelvesSearchParamDto){
		List<TerminalConfigCategoryEntity> entities = Lists.newArrayList();
		try {
			entities = terminalCategoryCache.get(shelvesSearchParamDto);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return entities;
	}

	/**
	 * 描述: 查询分类树结构
	 * @param terminalConfigCategory
	 * @return com.fosung.framework.web.http.ResponseParam
	 * @author fuhao
	 * @date 2021/10/26 11:43
	 **/
	@Override
	public ResponseParam queryCategory(TerminalCategoryAppDto terminalConfigCategory) {
		if(terminalConfigCategory.getTerminalId()==null){
			return ResponseParam.fail().message(TerminalContent.CHECK_TERMINAL_ID);
		}
		Map<String, Object> searchParam = new HashMap<>(8);
		searchParam.put("terminalId",terminalConfigCategory.getTerminalId());
		if(StringUtils.isNotBlank(terminalConfigCategory.getCategoryType())){
			searchParam.put("type",TerminalAppCategoryType.valueOf(terminalConfigCategory.getCategoryType()));
		}
		if(StringUtils.isNotBlank(terminalConfigCategory.getArea())){
			searchParam.put("area",terminalConfigCategory.getArea());
		}
		List<TerminalConfigCategoryEntity> terminalConfigCategoryEntities = this.queryAll(searchParam,new String[]{"num_asc"});
		terminalConfigCategoryEntities.forEach(categoryApp -> {
			if(categoryApp.getType().equals(TerminalAppCategoryType.routine)){
				categoryApp.setDisabledFlag(false);
			}else {
				categoryApp.setDisabledFlag(true);
			}
			if(null != categoryApp.getNavigationBtmId() ){
				Map<String,Object> searchParams = new HashMap<>(8);
				searchParams.put("intId", categoryApp.getNavigationBtmId());
				List<TerminalConfigNavigationBtmEntity>  btmEntities = terminalConfigNavigationBtmService.queryAll(searchParams);
				if(!btmEntities.isEmpty()){
					TerminalConfigNavigationBtmEntity navigationBtmEntity = btmEntities.get(0);
					categoryApp.setNavigationBtmName(navigationBtmEntity.getNavigationName());
				}
			}
		});
		List<Map<String, Object>> treeData = UtilTree.getTreeData(UtilDTO.toDTO(terminalConfigCategoryEntities, null, null, null), "id", "parentId", "children", false);
		if( treeData == null ){
			return ResponseParam.success().datalist(new ArrayList<>());
		}else{
			return ResponseParam.success().datalist(treeData);
		}

	}

	/**
	 * 描述: 保存工作台分类信息
	 * @param terminalConfigCategory
	 * @return void
	 * @author fuhao
	 * @date 2021/10/28 10:37
	 **/
	@Override
	@Transactional
	public void saveInfo(TerminalConfigCategoryEntity terminalConfigCategory) {

		terminalConfigCategory.setCode(UUID.randomUUID().toString());
		if(terminalConfigCategory.getNum() == null){
			// 获取最新排序
			Integer maxNum = this.getMaxNum(terminalConfigCategory);
			if(maxNum == null){
				maxNum = 0;
			}
			terminalConfigCategory.setNum(maxNum+1);
		}
		terminalConfigCategory.setStatus(true);
		this.save(terminalConfigCategory);
	}
}
