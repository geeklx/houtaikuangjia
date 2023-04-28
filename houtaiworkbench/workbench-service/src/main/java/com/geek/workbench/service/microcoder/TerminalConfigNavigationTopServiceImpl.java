package com.geek.workbench.service.microcoder;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.exception.AppException;
import com.geek.workbench.common.GlobalVariableKey;
import com.geek.workbench.common.MessageContent;
import com.geek.workbench.common.TerminalMessageContent;
import com.geek.workbench.dao.microcoder.TerminalConfigNavigationTopDao;
import com.geek.workbench.dict.MenuType;
import com.geek.workbench.dict.StatusType;
import com.geek.workbench.dto.microcoder.TerminalConfigMenuTopDto;
import com.geek.workbench.entity.microcoder.TerminalConfigNavigationTop;
import com.geek.workbench.util.CacheUtil;
import com.google.api.client.util.Lists;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @Description 顶部导航信息
 * @Author gaojian
 * @Date 2021/11/04 10:25
 * @Version V1.0
 */
@Service
public class TerminalConfigNavigationTopServiceImpl extends AppJPABaseDataServiceImpl<TerminalConfigNavigationTop, TerminalConfigNavigationTopDao>
	implements TerminalConfigNavigationTopService {

	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("terminalId","terminalId:EQ");
				put("navigationBtmId","navigationBtmId:EQ");
				put("area","area:EQ");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}

	/**
	 * 描述:  查询顶部导航信息
	 *
	 * @param terminalConfigNavigationTop
	 * @createDate: 2021/11/9 14:53
	 * @author: gaojian
	 * @modify:
	 * @return: Page<TerminalConfigNavigationTop>
	 */
	@Override
	public List<Map<String, Object>> queryInfo(TerminalConfigNavigationTop terminalConfigNavigationTop) {

		Assert.notNull(terminalConfigNavigationTop.getTerminalId(), MessageContent.TERMINAL_ID_IS_NULL);
		Assert.notNull(terminalConfigNavigationTop.getArea(), TerminalMessageContent.AREA_IS_NULL);
		return entityDao.queryInfo(terminalConfigNavigationTop);
	}

	/**
	 * 描述:  查询顶部导航信息 -- 分页
	 *
	 * @param terminalConfigNavigationTop
	 * @param pageable
	 * @createDate: 2021/11/9 14:53
	 * @author: gaojian
	 * @modify:
	 * @return: Page<TerminalConfigNavigationTop>
	 */
	@Override
	public Page<Map<String, Object>> queryInfoPage(TerminalConfigNavigationTop terminalConfigNavigationTop, Pageable pageable) {

		Assert.notNull(terminalConfigNavigationTop.getTerminalId(), MessageContent.TERMINAL_ID_IS_NULL);
		Assert.notNull(terminalConfigNavigationTop.getArea(), TerminalMessageContent.AREA_IS_NULL);
		return entityDao.queryInfoPage(terminalConfigNavigationTop,pageable);
	}

	/**
	 * 描述:  保存顶部导航
	 *
	 * @param terminalConfigNavigationTop
	 * @createDate: 2021/11/8 10:41
	 * @author: gaojian
	 * @modify:
	 * @return: void
	 */
	@Override
	public void saveInfo(TerminalConfigNavigationTop terminalConfigNavigationTop) {

		// 1. 校验底部导航是否已关联
		Map<String,Object> searchParams = new HashMap<>(8);
		searchParams.put(GlobalVariableKey.TERMINAL_ID,terminalConfigNavigationTop.getTerminalId());
		searchParams.put(GlobalVariableKey.AREA,terminalConfigNavigationTop.getArea());
		searchParams.put(GlobalVariableKey.NAVIGATION_BTM_ID,terminalConfigNavigationTop.getNavigationBtmId());
		List<TerminalConfigNavigationTop> list = queryAll(searchParams);
		if( list.size() > 0 ){
			throw new AppException(TerminalMessageContent.NAVIGATION_IS_EXIST);
		}

		// 2. 保存顶部导航信息
		terminalConfigNavigationTop.setMenuType(MenuType.custom);
		terminalConfigNavigationTop.setStatus(StatusType.ENABLE);
		save(terminalConfigNavigationTop);
	}

	/**
	 * 描述:  修改顶部导航
	 *
	 * @param terminalConfigNavigationTop
	 * @createDate: 2021/11/8 10:41
	 * @author: gaojian
	 * @modify:
	 * @return: void
	 */
	@Override
	@Transactional( rollbackFor = RuntimeException.class)
	public void updateInfo(TerminalConfigNavigationTop terminalConfigNavigationTop) {

		// 1. 校验底部导航是否已关联
		Map<String,Object> searchParams = new HashMap<>(8);
		searchParams.put(GlobalVariableKey.TERMINAL_ID,terminalConfigNavigationTop.getTerminalId());
		searchParams.put(GlobalVariableKey.AREA,terminalConfigNavigationTop.getArea());
		searchParams.put(GlobalVariableKey.NAVIGATION_BTM_ID,terminalConfigNavigationTop.getNavigationBtmId());
		List<TerminalConfigNavigationTop> list = queryAll(searchParams);
		if( list.size() > 0 && !terminalConfigNavigationTop.getId().equals(list.get(0).getId())){
			throw new AppException(TerminalMessageContent.NAVIGATION_IS_EXIST);
		}

		// 2. 判断是否修改关联的地步导航栏
		TerminalConfigNavigationTop result = get(terminalConfigNavigationTop.getId());
		if(!result.getNavigationBtmId().equals(terminalConfigNavigationTop.getNavigationBtmId())
		|| !result.getArea().equals(terminalConfigNavigationTop.getArea())){
			entityDao.updateAllTopMenu(terminalConfigNavigationTop);
		}

		// 3. 执行修改方法
		Set<String> updateFields = UtilDTO.toDTOExcludeFields(terminalConfigNavigationTop,
				Arrays.asList("id",GlobalVariableKey.TERMINAL_ID,"status","menuType")).keySet();
		update(terminalConfigNavigationTop,updateFields);
	}

	/**
	 * 描述: 删除信息
	 *
	 * @param list
	 * @createDate: 2021/11/9 15:36
	 * @author: gaojian
	 * @modify:
	 * @return: void
	 */
	@Override
	public void deleteInfo(List<AppBaseIdParam> list) {

		// 1. 删除导航信息
		delete(list.stream().map(map -> map.getId()).collect(Collectors.toList()));

		// 2. 删除菜单信息
		list.forEach( appBaseIdParam -> {
			entityDao.deleteAllTopMenu(appBaseIdParam.getId());
		});
	}
	@Override
	public List queryAreaList(){
		return entityDao.queryAreaList();
	}

	/**
	 *顶部导航配置缓存
	 *
	 * @author lhm
	 * @date 2021/11/16 14:56
	 * @return
	 */
	public LoadingCache<TerminalConfigMenuTopDto,List<TerminalConfigNavigationTop>> navigationTopCache = CacheUtil.getInstance().build(
			new CacheLoader<TerminalConfigMenuTopDto,List<TerminalConfigNavigationTop>>() {
				@Override
				public List<TerminalConfigNavigationTop> load(TerminalConfigMenuTopDto dto) throws Exception {
					ConcurrentHashMap map = new ConcurrentHashMap();
					if(null != dto.getCategoryCode()){
						map.put("navigationBtmId", dto.getCategoryCode());
					}
					//查询数据库的地域
					List<Map<String,Object>> areaList =  queryAreaList();
					//如果传过的地域包含数据库中的地域  则查询条件为数据库中值  如 传参：济南市
					for(Map areaMap : areaList){
						if(dto.getCityName().startsWith(areaMap.get("area").toString())){
							dto.setCityName(areaMap.get("area").toString());
							map.put("area", areaMap.get("area").toString());
						}
					}
					return queryAll(map);
				}
			}
	);


	/**
	 *
	 * 根据终端获取应用
	 * @param dto
	 * @author lhm
	 * @date 2021/11/16 10:05
	 * @return java.util.List<com.geek.workbench.entity.terminal.TerminalApplicationConfigEntity>
	 */
	@Override
	public List<TerminalConfigNavigationTop> getNavigationTop(TerminalConfigMenuTopDto dto){
		List<TerminalConfigNavigationTop> entities = Lists.newArrayList();
		try {
			entities = navigationTopCache.get(dto);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return entities;
	}
}
