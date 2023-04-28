package com.geek.system.support.system.service.sys;

import java.util.*;
import java.util.stream.Collectors;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.util.UtilCollection;
import com.geek.system.support.system.entity.sys.*;
import com.geek.system.support.system.dao.sys.SysResourceDao;
import com.geek.system.support.system.dto.sys.SysResourceDto;
import com.geek.system.support.system.entity.sys.*;
import com.geek.system.support.system.vo.SysResourceMenuVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class SysResourceServiceImpl extends AppJPABaseDataServiceImpl<SysResourceEntity, SysResourceDao>
	implements SysResourceService {


	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Autowired
	private SysApplicationService sysApplicationService;
	@Autowired
	private SysUserResourceService sysUserResourceService;


	@Autowired
	private SysRoleResourceService sysRoleResourceService;
	
	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("ids","id:IN");
				put("menuCodes","menuCode:IN");
				put("status","status:EQ");
				put("appId","appId:EQ");
				put("parentId","parentId:EQ");
				put("parentIds","parentId:IN");
				put("menuType","menuType:EQ");
				put("menuName","menuName:LIKE");
				put("id","id:EQ");
				put("menuCode","menuCode:RLIKE");
				put("roleIdIsNull","roleId:ISNULL");

			}
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}

	/**
	  * 根据多角色id获取资源
	  *
	  * @param roleIds
	  * @return java.util.List<SysResourceEntity>
	  * @author liuke
	  * @date 2021/4/7 9:54
	  */
	@Override
	public List<SysResourceEntity> queryResourceByRoleId(Set<Long> roleIds ,SysResourceDto sysResourceDto){
		HashMap<String,Object> searchResourceParam = Maps.newHashMap();
		searchResourceParam.put("roleIds",roleIds);
		List<SysRoleResourceEntity> roleResourceEntities=sysRoleResourceService.queryAll(searchResourceParam);

		if(!UtilCollection.sizeIsEmpty(roleResourceEntities)){
			Set<Long> ids = UtilCollection.extractToSet(roleResourceEntities,"resourceId",Long.class);
			if(!UtilCollection.sizeIsEmpty(ids)){
				Map<String,Object> searchParam = UtilDTO.toDTO(sysResourceDto,null);
				searchParam.put("ids",ids);
				return this.queryAll(searchParam);
			}
		}
		return null;
	}

	/**
	  * 根据角色Id获取资源
	  *
	  * @param roleId
	  * @return java.util.List<SysResourceEntity>
	  * @author liuke
	  * @date 2021/4/7 10:23
	  */
	@Override
	public List<SysResourceEntity> queryResourceByRoleId(Long roleId,SysResourceDto sysResourceDto){
		return this.queryResourceByRoleId(Sets.newHashSet(roleId),sysResourceDto);
	}

	/**
	  * 根据用户id查询资源
	  *
	  * @param userId
	  * @return java.util.List<SysResourceEntity>
	  * @author liuke
	  * @date 2021/4/7 10:16
	  */
	@Override
	public List<SysResourceEntity> queryResourceByUserId(Long userId, SysResourceDto sysResourceDto){
		Assert.notNull(userId,"用户ID不能为空");
		HashMap<String,Object> searchRoleParam = Maps.newHashMap();
		searchRoleParam.put("userId",userId);
		List<SysUserRoleEntity> userRoleEntities=sysUserRoleService.queryAll(searchRoleParam);
		if(!UtilCollection.sizeIsEmpty(userRoleEntities)){
			Set<Long> roleIds=UtilCollection.extractToSet(userRoleEntities,"roleId",Long.class);
			if(!UtilCollection.sizeIsEmpty(roleIds)){
				return this.queryResourceByRoleId(roleIds, sysResourceDto);
			}
		}
		return null;
	}

	/**
	 *根据用户id，应用编码获取资源树
	 *
	 * @param params
	 * @author liuke
	 * @date 2021/4/30 9:19
	 * @return java.util.List<SysResourceMenuVo>
	 */
	@Override
	public List<SysResourceMenuVo> selectByUserIdAndAppCode(Map<String,Object> params) {
		Set<SysResourceMenuVo> sysResourceMenuVos = Sets.newHashSet();
		//查询直接绑定到用户的资源
		List<SysResourceMenuVo> sysResourceMenu = entityDao.selectUserResource(params);
		List<Long> menuIds = sysResourceMenu.stream().map(map -> map.getMenuId()).collect(Collectors.toList());
		sysResourceMenuVos = Sets.newHashSet(sysResourceMenu);
		if(params.get("roleId") != null){
			List<SysResourceMenuVo> resourceMenuVos = entityDao.selectByUserIdAndAppCode(params);
			List<SysResourceMenuVo> collect = resourceMenuVos.stream().filter(user -> !menuIds.contains(user.getMenuId())).collect(Collectors.toList());
			sysResourceMenuVos.addAll(collect);
		}
		return Lists.newArrayList(sysResourceMenuVos).stream().sorted(Comparator.comparing(i -> i.getOrderNum(),Comparator.nullsLast(Integer::compareTo))).collect(Collectors.toList());
	}

	/**
	 *
	 * 根据角色id应用编码查询资源树
	 * @param params
	 * @author liuke
	 * @date 2022/6/28 14:02
	 * @return java.util.List<com.geek.system.support.system.vo.SysResourceMenuVo>
	 */
	@Override
	public List<SysResourceMenuVo> selectByAppCodeAndRole(Map<String,Object> params) {
		List<SysResourceMenuVo> sysResourceMenuVos = Lists.newArrayList();
		//查询直接绑定到用户的资源
		if(params.get("roleId") != null){
			 sysResourceMenuVos = entityDao.selectByUserIdAndAppCode(params);
		}
		return Lists.newArrayList(sysResourceMenuVos).stream().sorted(Comparator.comparing(i -> i.getOrderNum(),Comparator.nullsLast(Integer::compareTo))).collect(Collectors.toList());
	}
	/**
	 *
	 * 根据角色id应用编码查询app类型下的应用
	 * @param params
	 * @author liuke
	 * @date 2022/6/28 14:02
	 * @return java.util.List<com.geek.system.support.system.vo.SysResourceMenuVo>
	 */
	@Override
	public List<Long> selectAppByAppCodeAndRole(Map<String,Object> params) {
		List<Long> sysApps = Lists.newArrayList();
		//查询直接绑定到用户的资源
		if(params.get("roleId") != null){
			sysApps = entityDao.selectAppByAppCodeAndRole(params);
		}
		return sysApps;
	}

	/**
	 *分页查询
	 *
	 * @param searchParam
	 * @param pageable
	 * @author liuke
	 * @date 2021/11/2 16:50
	 * @return org.springframework.data.domain.Page<com.geek.system.support.system.entity.sys.SysRoleEntity>
	 */
	@Override
	public Page<SysResourceEntity> queryPage(Map<String, Object> searchParam, Pageable pageable) {
		return entityDao.queryPage(searchParam,pageable);
	}

	/**
	 * 描述: 资源级联删除
	 * @param
	 * @return void
	 * @author fuhao
	 * @date 2021/11/29 10:43
	 **/
	@Override
	@Transactional
	public void deleteInfo(List<AppBaseIdParam> list) {
		// 删除父资源
		this.delete(list.stream().map(map -> map.getId()).collect(Collectors.toList()));
		// 查询子资源
		HashMap<String, Object> searchParam = Maps.newHashMap();
		searchParam.put("parentIds",list.stream().map(map -> map.getId()).collect(Collectors.toList()));
		List<SysResourceEntity> resultResource = this.queryAll(searchParam);
		// 删除子资源
		if(resultResource != null && resultResource.size() > 0){
			for (SysResourceEntity children:resultResource) {
				// 删除一级子资源
				this.delete(children.getId());
				// 查询下级资源
				HashMap<String, Object> childrenParam = Maps.newHashMap();
				childrenParam.put("parentId",children.getId());
				List<SysResourceEntity> childrenResource = this.queryAll(childrenParam);
				// 如果存在则删除下级资源
				if(childrenResource != null && childrenResource.size() > 0){
					this.delete(childrenResource.stream().map(map -> map.getId()).collect(Collectors.toList()));
				}
			}
		}
	}

	/**
	 *查询应用下全部资源
	 *
	 * @param appId
	 * @author liuke
	 * @date 2021/12/3 14:29
	 * @return java.util.List<com.geek.system.support.system.entity.sys.SysResourceEntity>
	 */
	@Override
	public List<SysResourceEntity> getResoueceByAppId(Long appId){
		Map<String,Object> searchParam = Maps.newHashMap();
		searchParam.put("appId",appId);
		return queryAll(searchParam,new String[]{"num:asc","createDatetime:asc"});
	}

	/**
	 *获取全部应用资源
	 *
	 * @param projectId
	 * @author liuke
	 * @date 2021/12/3 15:08
	 * @return java.util.List<com.geek.system.support.system.entity.sys.ReturnTreeData>
	 */
	@Override
	public List<ReturnTreeData> getAppResourceTree(Long projectId){
		List<SysApplicationEntity>  sysApplicationEntities = sysApplicationService.selectByProjectId(projectId);
		List<ReturnTreeData> returnTreeDatas = Lists.newArrayList();
		for (SysApplicationEntity sysApplicationEntity : sysApplicationEntities) {
			ReturnTreeData returnTreeData = new ReturnTreeData();
			returnTreeData.setName(sysApplicationEntity.getAppName());
			returnTreeData.setId(sysApplicationEntity.getId());
			returnTreeData.setType("App");
			returnTreeData.setAppType(sysApplicationEntity.getAppType());
			returnTreeDatas.add(returnTreeData);
			List<SysResourceEntity> resourceEntities = getResoueceByAppId(sysApplicationEntity.getId());
			for (SysResourceEntity resourceEntity : resourceEntities) {
				ReturnTreeData returnTreeData1 = new ReturnTreeData();
				returnTreeData1.setName(resourceEntity.getMenuName());
				returnTreeData1.setId(resourceEntity.getId());
				returnTreeData1.setType("Resource");
				if(resourceEntity.getParentId()==null||resourceEntity.getParentId()==0L){
					returnTreeData1.setParentId(sysApplicationEntity.getId());
				}else {
					returnTreeData1.setParentId(resourceEntity.getParentId());
				}
				returnTreeDatas.add(returnTreeData1);
			}
		}
		return  returnTreeDatas;
	}

	/**
	 *人员授权资源列表查询
	 *
	 * @param projectId
	 * @param userId
	 * @author liuke
	 * @date 2021/12/3 15:22
	 * @return java.util.List<com.geek.system.support.system.entity.sys.ReturnTreeData>
	 */
	@Override
	public List<ReturnTreeData> getAppResourceCheckHas(Long projectId,Long userId,Long roleId){
		Map<String,Object> searchParam = Maps.newHashMap();
		if(roleId==null){
			searchParam.put("roleIdIsNull","123");
		}else {
			searchParam.put("roleId",roleId);
		}

		searchParam.put("userId",userId);
		List<SysUserResourceEntity> sysUserResourceEntities = sysUserResourceService.queryAll(searchParam);
		List<ReturnTreeData> returnTreeDatas = getAppResourceTree(projectId);
		if(UtilCollection.sizeIsEmpty(sysUserResourceEntities)){
			return returnTreeDatas;
		}
		Set<Long> resourceIds = sysUserResourceEntities.stream().map(map -> map.getResourceId()).collect(Collectors.toSet());
		returnTreeDatas.stream().forEach(returnTreeData -> {
			if(resourceIds.contains(returnTreeData.getId())){
				returnTreeData.setChekced(true);
			}
		});
		return returnTreeDatas;
	}

	/**
	 * 角色资源授权返回单选资源树
	 * @param projectId
	 * @param userId
	 * @param roleId
	 * @author liuke
	 * @date 2021/12/3 15:33
	 * @return java.util.List<com.geek.system.support.system.entity.sys.ReturnTreeData>
	 */
	@Override
	public List<ReturnTreeData> getAppResourceCheckHasAndNoRole(Long projectId,Long userId,Long roleId){
		Map<String,Object> searchParam = Maps.newHashMap();
		searchParam.put("projectId",projectId);
		searchParam.put("roleId",roleId);
		List<ReturnTreeData> returnTreeDatas = getAppResourceCheckHas( projectId, userId,roleId);
		List<SysRoleResourceEntity> sysRoleResourceEntities = sysRoleResourceService.queryAll(searchParam);
		if(UtilCollection.sizeIsEmpty(sysRoleResourceEntities)){
			return returnTreeDatas;
		}
		Set<Long> resourceIds = sysRoleResourceEntities.stream().map(map -> map.getResourceId()).collect(Collectors.toSet());
		returnTreeDatas.stream().forEach(returnTreeData -> {
			if (resourceIds.contains(returnTreeData.getId())){
				returnTreeData.setChekced(true);
				returnTreeData.setEnable(false);
			}
		});
		return returnTreeDatas;
	}

	/**
	 * 角色资源授权返回单选资源树
	 * @param projectId
	 * @param appId
	 * @param roleId
	 * @author liuke
	 * @date 2021/12/3 15:33
	 * @return java.util.List<com.geek.system.support.system.entity.sys.ReturnTreeData>
	 */
	@Override
	public List<ReturnTreeData> getRoleResource(Long projectId,Long appId,Long roleId){
		Map<String,Object> resParam = Maps.newHashMap();
		resParam.put("appId",appId);
		List<SysResourceEntity> sysResourceEntities = queryAll(resParam);
		if(UtilCollection.sizeIsEmpty(sysResourceEntities)){
			return Lists.newArrayList();
		}
		List<ReturnTreeData> returnTreeDatas = Lists.newArrayList();
		for (SysResourceEntity sysResourceEntity : sysResourceEntities) {
			ReturnTreeData returnTreeData1 = new ReturnTreeData();
			returnTreeData1.setName(sysResourceEntity.getMenuName());
			returnTreeData1.setId(sysResourceEntity.getId());
			returnTreeData1.setType("Resource");
			returnTreeData1.setParentId(sysResourceEntity.getParentId());
			returnTreeDatas.add(returnTreeData1);
		}
		Map<String,Object> searchParam = Maps.newHashMap();
		searchParam.put("projectId",projectId);
		searchParam.put("roleId",roleId);
		List<SysRoleResourceEntity> sysRoleResourceEntities = sysRoleResourceService.queryAll(searchParam);
		if(UtilCollection.sizeIsEmpty(sysRoleResourceEntities)){
			return returnTreeDatas;
		}
		Set<Long> resourceIds = sysRoleResourceEntities.stream().map(map -> map.getResourceId()).collect(Collectors.toSet());
		returnTreeDatas.stream().forEach(returnTreeData -> {
			if (resourceIds.contains(returnTreeData.getId())){
				returnTreeData.setChekced(true);
			}
		});
		return returnTreeDatas;
	}

	@Override
	public SysResourceEntity get(Long aLong) {
		Map<String,Object> searchParam = Maps.newHashMap();
		searchParam.put("id",aLong);
		return entityDao.queryById(searchParam);
	}
}
