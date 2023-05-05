package com.fosung.workbench.service.microcoder;

import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.util.UtilString;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import com.fosung.workbench.AppBean.AppNavicationBtm;
import com.fosung.workbench.config.TerminalContent;
import com.fosung.workbench.dao.microcoder.TerminalConfigNavigationBtmDao;
import com.fosung.workbench.dict.ShowStyleType;
import com.fosung.workbench.dict.StatusType;
import com.fosung.workbench.dto.config.AppSearchParamDto;
import com.fosung.workbench.dto.microcoder.TerminalConfigNavigationBtmDto;
import com.fosung.workbench.entity.microcoder.TerminalConfigMenuTopEntity;
import com.fosung.workbench.entity.microcoder.TerminalConfigNavigationBtmEntity;
import com.fosung.workbench.entity.microcoder.TerminalConfigNavigationTop;
import com.fosung.workbench.service.terminal.TerminalNavigationStrategyService;
import com.fosung.workbench.util.CacheUtil;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class TerminalConfigNavigationBtmImpl extends AppJPABaseDataServiceImpl<TerminalConfigNavigationBtmEntity, TerminalConfigNavigationBtmDao>
	implements TerminalConfigNavigationBtmService {

	@Autowired
	private TerminalNavigationStrategyService terminalNavigationStrategyService;
	@Autowired
	private TerminalConfigNavigationTopService navTopService;
	@Autowired
	private TerminalConfigMenuTopService menuTopService;

	
	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("terminalId","terminalId:EQ");
				put("status","status:EQ");
				put("defaultShow","defaultShow:EQ");
				put("area","area:EQ");
				put("navigationUrl","navigationUrl:EQ");
				put("associatedObject","associatedObject:EQ");
				put("intId","intId:EQ");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}

	/**
	 * 描述: 根据id修改导航信息
	 * @param navigationConfigurationInfo
	 * @return int
	 * @author fuhao
	 * @date 2021/11/4 14:20
	 **/
	@Override
	public int updateById(TerminalConfigNavigationBtmEntity navigationConfigurationInfo) {
		return entityDao.updateById(navigationConfigurationInfo);
	}

	/**
	 *导航缓存
	 *
	 * @author liuke
	 * @date 2021/10/15 14:56
	 * @return
	 */
	public LoadingCache<AppSearchParamDto,List<AppNavicationBtm>> TerminalConfigNavigationBtmCache = CacheUtil.getInstance().build(
			new CacheLoader<AppSearchParamDto,List<AppNavicationBtm>>() {
				@Override
				public List<AppNavicationBtm> load(AppSearchParamDto appSearchParamDto) throws Exception {
					Map<String,Object> searchParam = Maps.newHashMap();
					Map<String,Object> map = Maps.newHashMap();
					searchParam.put("terminalId",appSearchParamDto.getTerminalId());
					searchParam.put("status", StatusType.ENABLE);
					List<TerminalConfigNavigationBtmEntity> result = queryAll(searchParam,new String[]{"num"});
					String cityName = appSearchParamDto.getCityName();
					List<AppNavicationBtm> list = Lists.newArrayList();
					for (TerminalConfigNavigationBtmEntity terminalConfigNavigationBtmEntity : result) {
						if(cityName.startsWith(terminalConfigNavigationBtmEntity.getArea())){
							if(terminalConfigNavigationBtmEntity.getShowStyle().equals(ShowStyleType.pic)){
								list.add(new AppNavicationBtm(terminalConfigNavigationBtmEntity.getIntId().toString(),"",terminalConfigNavigationBtmEntity.getNavigationName(),terminalConfigNavigationBtmEntity.getNavigationUrl(),terminalConfigNavigationBtmEntity.getDefaultShow(),terminalConfigNavigationBtmEntity.getNavigationLogoNoChecked(),terminalConfigNavigationBtmEntity.getNavigationLogoChecked() ));
							}else if(terminalConfigNavigationBtmEntity.getShowStyle().equals(ShowStyleType.word)){
								list.add(new AppNavicationBtm(terminalConfigNavigationBtmEntity.getIntId().toString(),"",terminalConfigNavigationBtmEntity.getNavigationName(),terminalConfigNavigationBtmEntity.getNavigationUrl(),terminalConfigNavigationBtmEntity.getDefaultShow(),"",""));
							}else {
								list.add(new AppNavicationBtm(terminalConfigNavigationBtmEntity.getIntId().toString(),"",terminalConfigNavigationBtmEntity.getNavigationName(),terminalConfigNavigationBtmEntity.getNavigationUrl(),terminalConfigNavigationBtmEntity.getDefaultShow(),terminalConfigNavigationBtmEntity.getNavigationLogoNoChecked(),terminalConfigNavigationBtmEntity.getNavigationLogoChecked()));
							}
						}
					}
					return list;
				}
			}
	);

	/**
	 * 根据终端id获取缓存中的配置
	 * @param appSearchParamDto
	 * @return
	 */
	@Override
	public List<AppNavicationBtm> getTerminalConfigNavigationBtms(AppSearchParamDto appSearchParamDto){
		try {
			if(UtilString.isNotBlank(appSearchParamDto.getCityName())){
				return TerminalConfigNavigationBtmCache.get(appSearchParamDto);
			}else {
				return Lists.newArrayList();
			}

		} catch (ExecutionException e) {
			e.printStackTrace();
			return Lists.newArrayList();
		}
	}

	/**
	 * 描述: 查询终端下最大的排序
	 * @param terminalConfigNavigationBtmEntity
	 * @return java.lang.Integer
	 * @author fuhao
	 * @date 2021/11/4 14:22
	 **/
	@Override
	public Integer queryMaxNum(TerminalConfigNavigationBtmEntity terminalConfigNavigationBtmEntity) {
		return entityDao.queryMaxNum(terminalConfigNavigationBtmEntity);
	}

	/**
	 * 描述: 获取底部导航下拉框
	 * @param dto
	 * @return java.util.List<com.fosung.workbench.entity.terminal.TerminalConfigNavigationBtmEntity>
	 * @author fuhao
	 * @date 2021/11/9 14:15
	 **/
	@Override
	public List<TerminalConfigNavigationBtmEntity> btmNavOption(TerminalConfigNavigationBtmDto dto) {
		Map<String, Object> searchParam = new HashMap<>();
		searchParam.put(TerminalContent.TERMINAL_ID,dto.getTerminalId());
		searchParam.put("area",dto.getArea());
		// 查询全部的底部导航
		List<TerminalConfigNavigationBtmEntity> allBtmNavList = this.queryAll(searchParam);
//		// 查询选中的底部导航
//		List<TerminalConfigNavigationTop> checkBtmNavList = navTopService.queryAll(searchParam);
//		// 过滤出未选中的底部导航
//		List<TerminalConfigNavigationBtmEntity> noCheckBtmNavList = new ArrayList<>();
//		for(TerminalConfigNavigationBtmEntity all: allBtmNavList){
//			boolean flag = true;
//			for(TerminalConfigNavigationTop checked: checkBtmNavList){
//				if(all.getId().equals(checked.getNavigationBtmId())){
//					flag = false;
//					break;
//				}
//			}
//			if(flag){
//				noCheckBtmNavList.add(all);
//			}
//		}
		return allBtmNavList;
	}

	@Override
	public void deleteByBtmNav(List<AppBaseIdParam> list) {
		System.out.println(list.stream().map(map -> map.getId()).collect(Collectors.toList()));
		List<Long> ids = list.stream().map(map -> map.getId()).collect(Collectors.toList());
		// 根据底部导航id删除底部导航信息
		this.delete(ids);
		// 根据底部导航id查询顶部导航id删除顶部导航信息
		ids.forEach(id -> {
			HashMap<String, Object> navTop = new HashMap<>();
			navTop.put("navigationBtmId",id);
			List<TerminalConfigNavigationTop> navtops = navTopService.queryAll(navTop);
			List<Long> navTopIds = navtops.stream().map(map -> map.getId()).collect(Collectors.toList());
			navTopService.delete(navTopIds);
			// 根据顶部导航id查询顶部导航菜单id删除顶部导航菜单信息
			navTopIds.forEach(navTopId -> {
				HashMap<String, Object> menuTop = new HashMap<>();
				menuTop.put("navigationId",navTopId);
				List<TerminalConfigMenuTopEntity> menuTops = menuTopService.queryAll(menuTop);
				List<Long> menuTopIds = menuTops.stream().map(map -> map.getId()).collect(Collectors.toList());
				menuTopService.delete(menuTopIds);
			});
		});
	}
}
