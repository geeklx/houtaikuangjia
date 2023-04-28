package com.geek.workbench.service.microcoder;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.geek.workbench.dao.microcoder.TerminalConfigMenuTopDao;
import com.geek.workbench.dict.StatusType;
import com.geek.workbench.dto.microcoder.TerminalConfigMenuTopDto;
import com.geek.workbench.entity.microcoder.TerminalConfigMenuTopEntity;
import com.geek.workbench.entity.microcoder.TerminalConfigNavigationBtmEntity;
import com.geek.workbench.entity.microcoder.TerminalConfigNavigationTop;
import com.geek.workbench.util.CacheUtil;
import com.google.api.client.util.Lists;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;

/**
 * @Description 顶部菜单服务实现类
 * @Author gaojian
 * @Date 2021/11/04 10:25
 * @Version V1.0
 */
@Service
public class TerminalConfigMenuTopServiceImpl extends AppJPABaseDataServiceImpl<TerminalConfigMenuTopEntity, TerminalConfigMenuTopDao>
	implements TerminalConfigMenuTopService {
	
	/**
	 * 描述: 顶部导航服务
	 * @createDate: 2021/11/9 16:45
	 * @author: gaojian
	 */
	@Autowired
	private TerminalConfigNavigationTopService terminalConfigNavigationTopService;

	/**
	 * 描述: 底部导航服务
	 * @createDate: 2021/12/07 16:45
	 * @author: gaojian
	 */
	@Autowired
	private TerminalConfigNavigationBtmService terminalConfigNavigationBtmService;

	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("terminalId","terminalId:EQ");
				put("parentId","parentId:EQ");
				put("navigationBtmId","navigationBtmId:EQ");
				put("navigationId","navigationId:EQ");
				put("area","area:EQ");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}


	/**
	 * 描述:  保存顶部菜单
	 *
	 * @param terminalConfigMenuTopEntity
	 * @createDate: 2021/11/9 16:44
	 * @author: gaojian
	 * @modify:
	 * @return: void
	 */
	@Override
	public void saveInfo(TerminalConfigMenuTopEntity terminalConfigMenuTopEntity) {

		// 1. 获取顶部导航信息并将基本信息复制给菜单
		TerminalConfigNavigationTop terminalConfigNavigationTop = terminalConfigNavigationTopService.get(terminalConfigMenuTopEntity.getNavigationId());
		terminalConfigMenuTopEntity.setArea(terminalConfigNavigationTop.getArea());
		terminalConfigMenuTopEntity.setNavigationBtmId(terminalConfigNavigationTop.getNavigationBtmId());
		terminalConfigMenuTopEntity.setTerminalId(terminalConfigNavigationTop.getTerminalId());
		terminalConfigMenuTopEntity.setStatus(StatusType.ENABLE);
		terminalConfigMenuTopEntity.setMenuType(terminalConfigNavigationTop.getMenuType());
		terminalConfigMenuTopEntity.setIsEnd(Boolean.TRUE);

		// 2. 修改父菜单为有子信息
		TerminalConfigMenuTopEntity parent = get(terminalConfigMenuTopEntity.getParentId());
		if(parent != null && parent.getId() != null){
			parent.setIsEnd(Boolean.FALSE);
			update(parent, Arrays.asList("isEnd"));
		}

		// 3. 保存菜单信息
		save(terminalConfigMenuTopEntity);
	}

	/**
	 * 描述:  删除菜单
	 *
	 * @param list
	 * @createDate: 2021/11/9 18:12
	 * @author: gaojian
	 * @modify:
	 * @return: void
	 */
	@Override
	public void deleteInfo(List<AppBaseIdParam> list) {

		// 1. 删除菜单
		delete(list.stream().map(map -> map.getId()).collect(Collectors.toList()));

		// 2. 级联删除子菜单
		list.forEach(appBaseIdParam -> {
			entityDao.deleteByMenuId(appBaseIdParam.getId());
		});
	}

	@Override
	public List<Map<String, Object>> getMeunList(TerminalConfigMenuTopDto menuTopDto) {

		// 1. 因APP侧底部导航无法使用Long类型的id所以此处需要通过int类型的id转换一下 20211207 加
		String intId = menuTopDto.getCategoryCode();
		Map<String,Object> searchParams = new HashMap<>(8);
		searchParams.put("intId",Integer.valueOf(intId));
		List<TerminalConfigNavigationBtmEntity>  btmEntities = terminalConfigNavigationBtmService.queryAll(searchParams);
		if(btmEntities != null && !btmEntities.isEmpty()){
			menuTopDto.setCategoryCode(btmEntities.get(0).getId().toString());
		}else{
			return null;
		}

		List<Map<String, Object>> resultList = new ArrayList<>();
		List<TerminalConfigMenuTopEntity> meunTopList = this.getMenuTop(menuTopDto);
		for(TerminalConfigMenuTopEntity entity : meunTopList){
			if(menuTopDto.getCityName().startsWith(entity.getArea())){
				Map navigationTopmap =getMap(entity);
				resultList.add(navigationTopmap);
			}
		}
		return resultList;
	}

	/**
	 * 组装数据
	 * @param entity
	 * @return
	 */
	public Map getMap(TerminalConfigMenuTopEntity entity){
		Map navigationTopmap = new HashMap();
		navigationTopmap.put("id",entity.getId());
		navigationTopmap.put("img",entity.getImg() == null?"":entity.getImg());
		navigationTopmap.put("name",entity.getName() == null ?"":entity.getName());
		navigationTopmap.put("url",entity.getUrl() == null?"":entity.getUrl());
		navigationTopmap.put("enable", true);
		navigationTopmap.put("parentId",entity.getParentId()== null ?"":entity.getParentId());
		navigationTopmap.put("cityName",entity.getArea() == null ?"":entity.getArea() );
		navigationTopmap.put("num",entity.getNum() == null ? "": entity.getNum());
		return navigationTopmap;
	}

	/**
	 *顶部导航配置缓存
	 *
	 * @author lhm
	 * @date 2021/11/16 14:56
	 * @return
	 */
	public LoadingCache<TerminalConfigMenuTopDto,List<TerminalConfigMenuTopEntity>> menuTopCache = CacheUtil.getInstance().build(
			new CacheLoader<TerminalConfigMenuTopDto,List<TerminalConfigMenuTopEntity>>() {
				@Override
				public List<TerminalConfigMenuTopEntity> load(TerminalConfigMenuTopDto dto) throws Exception {
					TerminalConfigNavigationTop navigationTop= new TerminalConfigNavigationTop();
					List<TerminalConfigNavigationTop> navigationTopList = terminalConfigNavigationTopService.getNavigationTop( dto);
					if(!navigationTopList.isEmpty()){
						navigationTop = navigationTopList.get(0);
					}

					ConcurrentHashMap map =new ConcurrentHashMap();
					map.put("terminalId",dto.getTerminalId());
					map.put("status", StatusType.ENABLE);
					if(null != dto.getCategoryCode()){
						map.put("navigationBtmId", dto.getCategoryCode());
					}
					if(null != navigationTop && null !=  navigationTop.getId()){
						map.put("navigationId", navigationTop.getId());
					}
					if(null == dto.getParentId() || StringUtils.isEmpty(dto.getParentId())){
						map.put("parentId",navigationTop.getId());
					}else{
						map.put("parentId", dto.getParentId());
					}
					if(null != dto.getCityName()){
						map.put("area", dto.getCityName());
					}
					return queryAll(map,new String[]{"num"});
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
	public List<TerminalConfigMenuTopEntity> getMenuTop(TerminalConfigMenuTopDto dto){
		List<TerminalConfigMenuTopEntity> entities = Lists.newArrayList();
		try {
			entities = menuTopCache.get(dto);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return entities;
	}
}
