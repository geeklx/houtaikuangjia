package com.geek.workbench.service.terminal;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.json.JsonMapper;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.common.util.UtilString;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.workbench.common.GlobalVariableKey;
import com.geek.workbench.dao.terminal.TerminalApplicationShelvesDao;
import com.geek.workbench.dict.SearchOperateType;
import com.geek.workbench.dict.ShelvesType;
import com.geek.workbench.dto.terminal.TerminalApplicationShelvesDto;
import com.geek.workbench.entity.terminal.TerminalApplicationConfigEntity;
import com.geek.workbench.entity.terminal.TerminalApplicationShelvesEntity;
import com.geek.workbench.service.feign.RoleService;
import com.geek.workbench.service.search.UniSearchService;
import com.geek.workbench.util.CacheConstants;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TerminalApplicationShelvesServiceImpl extends AppJPABaseDataServiceImpl<TerminalApplicationShelvesEntity, TerminalApplicationShelvesDao>
	implements TerminalApplicationShelvesService {


	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private UniSearchService uniSearchService;

	@Autowired
	private TerminalApplicationConfigService configService;

	@Autowired
	private RoleService roleService;

	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("terminalId","terminalId:EQ");
				put("appId","appId:EQ");
				put("appConfigId","appConfigId:EQ");
				put("shelvesRanges","shelvesRange:IN");
				put("shelvesRange","shelvesRange:EQ");
				put("shelvesType","shelvesType:EQ");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}

	/**
	 * 功能描述: 保存授权信息
	 *
	 * @param terminalApplicationShelvesDto
	 * @return
	 * @author fuhao
	 * @date 2021/10/15 16:41
	 */
	@Override
	@Transactional
	public void save( TerminalApplicationShelvesDto terminalApplicationShelvesDto) {
		Map<String, Object> searchParam =  UtilDTO.toDTO(terminalApplicationShelvesDto, null);
		// 若存在授权先删除
		List<TerminalApplicationShelvesEntity> terminalApplicationShelvesEntities = this.queryAll(searchParam);
		terminalApplicationShelvesEntities.forEach(terminalApp->{
			this.delete(terminalApp.getId());
		});
		// 是否存在用户授权
		List<String> userShelvesRange = terminalApplicationShelvesDto.getUserShelvesRange();
		if(userShelvesRange != null && userShelvesRange.size() > 0){
			String userShelvesRangesParents = terminalApplicationShelvesDto.getUserShelvesRangesParents();
			appendSaveShelvesParams(userShelvesRange,terminalApplicationShelvesDto, ShelvesType.user.getValue(),userShelvesRangesParents);
		}
		// 是否存在身份授权
		List<String> identityShelvesRange = terminalApplicationShelvesDto.getIdentityShelvesRange();
		if(identityShelvesRange != null && identityShelvesRange.size() > 0){
			appendSaveShelvesParams(identityShelvesRange,terminalApplicationShelvesDto,ShelvesType.identity.getValue(),null);
		}
		// 是否存在组织授权
		List<String> orgShelvesRange = terminalApplicationShelvesDto.getOrgShelvesRange();
		if(orgShelvesRange != null && orgShelvesRange.size() > 0){
			String orgShelvesRangeParents = terminalApplicationShelvesDto.getOrgShelvesRangeParents();
			appendSaveShelvesParams(orgShelvesRange,terminalApplicationShelvesDto,ShelvesType.org.getValue(),orgShelvesRangeParents);
		}
//		// 是否存在地区授权
//		List<String> areaShelvesRange = terminalApplicationShelvesDto.getAreaShelvesRange();
//		if(areaShelvesRange != null && areaShelvesRange.size() > 0){
//			appendSaveShelvesParams(areaShelvesRange,terminalApplicationShelvesDto,ShelvesType.area.getValue());
//		}

		// 修改授权信息同步到统一搜索 2021 12 27
		TerminalApplicationConfigEntity terminalAppConfig = configService.get(terminalApplicationShelvesDto.getAppConfigId());
		uniSearchService.searchSynchronizationData(terminalAppConfig, SearchOperateType.upd);
	}

	/**
	 * 拼装终端应用授权参数
	 * @param userShelvesRange
	 * @param dto
	 * @param shelvesType
	 * @author fuhao
	 * @date 2021/10/15 16:41
	 */
	public void appendSaveShelvesParams(List<String> userShelvesRange,
										TerminalApplicationShelvesDto dto,String shelvesType,String shelvesRangeParents){
		for (String shelvesRange:userShelvesRange) {
			TerminalApplicationShelvesEntity terminalApplicationShelves = new TerminalApplicationShelvesEntity();
			terminalApplicationShelves.setTerminalId(dto.getTerminalId());
			//terminalApplicationShelves.setAppId(dto.getAppId());
			terminalApplicationShelves.setAppConfigId(dto.getAppConfigId());
			terminalApplicationShelves.setShelvesType(shelvesType);
			terminalApplicationShelves.setShelvesRange(shelvesRange);
			terminalApplicationShelves.setShelvesRangeParents(shelvesRangeParents);
			this.save(terminalApplicationShelves);

			// 更新 redis 缓存
			updateShelves(dto.getTerminalId(),shelvesType,shelvesRange);

		}
	}

	/**
	 *获取用户授权范围
	 *
	 * @param
	 * @author liuke
	 * @date 2021/10/25 16:10
	 * @return java.util.Set<java.lang.Long>
	 */
	@Override
	public Set<Long> getUserRangeIds(Long terminalId,String userId){
		Set<Long> ids = Sets.newHashSet();
		String appIds = stringRedisTemplate.opsForValue().get(CacheConstants.APP_SHELVES_USER_PREFIX+"terminalId"+terminalId+ShelvesType.user.getValue()+":"+userId);
		if(UtilString.isBlank(appIds)){
			Map<String,Object> searchParam = Maps.newHashMap();
			searchParam.put("shelvesRange", userId);
			searchParam.put("terminalId",terminalId);
			List<TerminalApplicationShelvesEntity> shelvesEntities = this.queryAll(searchParam);
			if(UtilCollection.sizeIsEmpty(shelvesEntities)){
				return ids;
			}
			ids = shelvesEntities.stream().map(map -> map.getAppConfigId()).collect(Collectors.toSet()) ;
			stringRedisTemplate.opsForValue().set(CacheConstants.APP_SHELVES_USER_PREFIX+"terminalId"+terminalId+ShelvesType.user.getValue()+":"+userId, JsonMapper.toJSONString(ids), CacheConstants.EXPIRES_HOURS, TimeUnit.HOURS);
		}else {
			List<Long> userIds = JsonMapper.parseArray(appIds,Long.class);
			ids.addAll(userIds);
			log.debug("selectAppIdByUserIdInCache 命中缓存: {}" , "user:"+userId+"terminal:"+terminalId) ;
		}
		return ids;
	}

	/**
	 *获取用户授权范围
	 *
	 * @param
	 * @author liuke
	 * @date 2021/10/25 16:10
	 * @return java.util.Set<java.lang.Long>
	 */
	@Override
	public Set<Long> getIdenRangeIds(Long terminalId,Set<String> identity){
		Set<Long> ids = Sets.newHashSet();

		for (String id : identity) {
			String appIds = stringRedisTemplate.opsForValue().get(CacheConstants.APP_SHELVES_IDENDITY_PREFIX+"terminalId"+terminalId+ShelvesType.identity.getValue()+":"+id);
			if(UtilString.isBlank(appIds)){
				Map<String,Object> searchParam = Maps.newHashMap();
				searchParam.put("shelvesRange",id);
				searchParam.put("terminalId",terminalId);
				List<TerminalApplicationShelvesEntity> shelvesEntities = this.queryAll(searchParam);
				ids.addAll( shelvesEntities.stream().map(map -> map.getAppConfigId()).collect(Collectors.toSet()) );
				stringRedisTemplate.opsForValue().set(CacheConstants.APP_SHELVES_IDENDITY_PREFIX+"terminalId"+terminalId+terminalId+ShelvesType.identity.getValue()+":"+id, JsonMapper.toJSONString(ids), CacheConstants.EXPIRES_HOURS, TimeUnit.HOURS);
			}else {
				List<Long> userIds = JsonMapper.parseArray(appIds,Long.class);
				ids.addAll(userIds);
				log.debug("selectAppIdByUserIdInCache 命中缓存: {}" , "iden:"+id+"terminal:"+terminalId) ;
			}
		}


		return ids;
	}

	/**
	 *获取组织授权范围
	 *
	 * @param
	 * @author liuke
	 * @date 2021/10/25 16:10
	 * @return java.util.Set<java.lang.Long>
	 */
	@Override
	public Set<Long> getOrgRangeIds(Long terminalId,Set<String> org){
		Set<Long> ids = Sets.newHashSet();

		for (String id : org) {
			String appIds = stringRedisTemplate.opsForValue().get(CacheConstants.APP_SHELVES_ORG_PREFIX+"terminalId"+terminalId+ShelvesType.org.getValue()+":"+id);
			if(UtilString.isBlank(appIds)){
				Map<String,Object> searchParam = Maps.newHashMap();
				searchParam.put("shelvesRange",id);
				searchParam.put("terminalId",terminalId);
				List<TerminalApplicationShelvesEntity> shelvesEntities = this.queryAll(searchParam);
				ids.addAll( shelvesEntities.stream().map(map -> map.getAppConfigId()).collect(Collectors.toSet()) );
				stringRedisTemplate.opsForValue().set(CacheConstants.APP_SHELVES_ORG_PREFIX+"terminalId"+terminalId+ShelvesType.org.getValue()+":"+id, JsonMapper.toJSONString(ids), CacheConstants.EXPIRES_HOURS, TimeUnit.HOURS);
			}else {
				List<Long> appIdsJson = JsonMapper.parseArray(appIds,Long.class);
				ids.addAll(appIdsJson);
				log.debug("selectAppIdByUserIdInCache 命中缓存: {}" , "iden:"+id+"terminal:"+terminalId) ;
			}
		}


		return ids;
	}

	/**
	 * 描述:  根据角色范围查询应用授权信息-调用用户权限中心
	 * @createDate: 2022/8/1 15:49
	 * @author: gaojian
	 * @modify:
	 * @param terminalCode
	 * @param roleId
	 * @return: java.util.Set<java.lang.Long>
	 */
	@Override
	public Set<Long> getRoleRangeIds(String terminalCode, String roleId) {

		Set<Long> ids = new HashSet<>();
		try{
			String appIds = stringRedisTemplate.opsForValue().get(CacheConstants.APP_SHELVES_ORG_PREFIX+"terminalId"+terminalCode+ShelvesType.role.getValue()+":"+roleId);
			if(UtilString.isBlank(appIds)){
				ResponseParam responseParam = roleService.queryApp(terminalCode,roleId);
				if("true".equals(responseParam.get(ResponseParam.SUCCESS_PARAM_VALUE).toString())
						&& responseParam.get(ResponseParam.DATA_LIST_PARAM) != null){
					List<String> list = (List<String>) responseParam.get(ResponseParam.DATA_LIST_PARAM);
					list.forEach(s -> {
						ids.add(Long.valueOf(s));
					});
					stringRedisTemplate.opsForValue().set(CacheConstants.APP_SHELVES_ORG_PREFIX+"terminalId"+terminalCode+ShelvesType.role.getValue()+":"+roleId, JsonMapper.toJSONString(ids), CacheConstants.EXPIRES_MINUTE, TimeUnit.MINUTES);
				}else{
					log.error("调用用户权限中心返回信息错误，返回值为："+responseParam.toString());
				}
			}else{
				List<Long> appIdsJson = JsonMapper.parseArray(appIds,Long.class);
				ids.addAll(appIdsJson);
				log.debug("selectAppIdByRoleIdInCache 命中缓存: {}" , "iden:"+roleId+"terminal:"+terminalCode) ;
			}

		}catch (Exception e){
			log.error(e.getMessage());
		}
		return ids;
	}

	/**
	 *获取组织授权范围
	 *
	 * @param
	 * @author liuke
	 * @date 2021/10/25 16:10
	 * @return java.util.Set<java.lang.Long>
	 */
	@Override
	public Set<Long> getAreaRangeIds(Long terminalId,String areaCode){
		Set<Long> ids = Sets.newHashSet();
		String appIds = stringRedisTemplate.opsForValue().get(CacheConstants.APP_SHELVES_USER_PREFIX+"terminalId"+terminalId+ShelvesType.area.getValue()+":"+areaCode);
		if(UtilString.isBlank(appIds)){
			Map<String,Object> searchParam = Maps.newHashMap();
			searchParam.put("shelvesType",ShelvesType.area.getValue());
			searchParam.put("terminalId",terminalId);
			List<TerminalApplicationShelvesEntity> shelvesEntities = this.queryAll(searchParam);
			if(UtilCollection.sizeIsEmpty(shelvesEntities)){
				return ids;
			}
			ids = shelvesEntities.stream().filter(s -> areaCode.contains(s.getShelvesRange())).map(map -> map.getAppConfigId()).collect(Collectors.toSet()) ;
			stringRedisTemplate.opsForValue().set(CacheConstants.APP_SHELVES_USER_PREFIX+"terminalId"+terminalId+ShelvesType.area.getValue()+":"+areaCode, JsonMapper.toJSONString(ids), CacheConstants.EXPIRES_HOURS, TimeUnit.HOURS);
		}else {
			List<Long> userIds = JsonMapper.parseArray(appIds,Long.class);
			ids.addAll(userIds);
			log.debug("selectAppIdByUserIdInCache 命中缓存: {}" , "areaCode:"+areaCode+"terminal:"+terminalId) ;
		}
		return ids;
	}

	/**
	 * 描述:  为防止redis缓存雪崩，获取随机 1 - 8 的过期时间
	 * @createDate: 2021/11/3 16:51
	 * @author: gaojian
	 * @modify:
	 * @param
	 * @return: int
	 */
	public int getShelvesTime(){

		int shelvesTime =  (int) (Math.random() * CacheConstants.EXPIRES_HOURS) + 1;
		return shelvesTime;
	}

	/**
	 * 描述:  更新授权信息
	 * @createDate: 2021/11/3 17:05
	 * @author: gaojian
	 * @modify:
	 * @param terminalId
	 * @param shelvesType
	 * @param shelvesRange
	 * @return: void
	 */
	public void updateShelves(Long terminalId,String shelvesType,String shelvesRange){

		// 1. 查询授权信息
		try{
			Set<Long> ids = Sets.newHashSet();
			Map<String,Object> searchParam = Maps.newHashMap();
			searchParam.put("shelvesRange",shelvesRange);
			searchParam.put("terminalId",terminalId);
			List<TerminalApplicationShelvesEntity> shelvesEntities = this.queryAll(searchParam);
			ids.addAll( shelvesEntities.stream().map(map -> map.getAppConfigId()).collect(Collectors.toSet()));

			// 2. 更新 Redis key 的 规则为 shelves: + terminalId + 终端ID + 授权类型 + 授权范围
			int shelvesTime = getShelvesTime();
			stringRedisTemplate.opsForValue().set(CacheConstants.APP_SHELVES_PREFIX+ GlobalVariableKey.TERMINAL_ID +terminalId+shelvesType+":"+shelvesRange, JsonMapper.toJSONString(ids),shelvesTime, TimeUnit.HOURS);

		}catch (Exception e){
			log.error("更新授权范围信息失败,失败信息："+e.getMessage());
		}

	}

}
