package com.fosung.system.support.system.service.sys;

import java.util.*;
import java.util.stream.Collectors;

import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.common.util.UtilString;
import com.fosung.system.support.system.dao.sys.SysOrgDao;
import com.fosung.system.support.system.dict.OrgType;
import com.fosung.system.support.system.dict.RoleType;
import com.fosung.system.support.system.entity.sys.*;
import com.fosung.system.support.system.service.mq.SysMQProducerService;
import com.fosung.system.support.system.util.CodeGenerateTool;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;

@Service
public class SysOrgServiceImpl extends AppJPABaseDataServiceImpl<SysOrgEntity, SysOrgDao>
	implements SysOrgService {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysUserRoleScopService sysUserRoleScopService;

	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysMQProducerService sysMQProducerService;
	
	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("appId","appId:EQ");
				put("id","id:EQ");
				put("ids","id:IN");
				put("del","del:EQ");
				put("orgName","orgName:EQ");
				put("orgNames","orgName:IN");
				put("orgNameL","orgName:LIKE");
				put("orgCode","orgCode:RLIKE");
				put("orgCodes","orgCode:IN");
				put("parentId","parentId:EQ");
				put("hasChildren","hasChildren:EQ");
				put("level","level:EQ");
				put("status","status:EQ");
				put("orgType","orgType:EQ");
				put("parentIdIsNull","parentId:ISNULL");
				put("projectId","projectId:EQ");
				put("projectIds","projectId:IN");
				put("parentIds","parentId:IN");
				put("levelType","levelType:EQ");
				put("levelTypes","levelType:IN");
				put("orgCodeEq","orgCode:EQ");
				put("leaf","leaf:EQ");
				put("dtOrgId","dtOrgId:EQ");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}


	/**
	 *获取外部链接（暂不支持）
	 *
	 * @param url
	 * @param searchMaap
	 * @author liuke
	 * @date 2021/4/15 10:31
	 * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
	 */
	@Override
	public List<Map<String, Object>> getOutResource(String url, Map<String, Object> searchMaap) {
        //TODO
		return null;
	}

	/**
	 *根据组织ID循环遍历所有子组织，得到组织id
	 *
	 * @param orgId
	 * @author liuke
	 * @date 2021/4/22 9:57
	 * @return java.util.Set<java.lang.Long>
	 */
	@Override
	public Set<Long> getOrgIds(Long orgId){
		Set<Long> ids = Sets.newHashSet();
//		SysOrgEntity sysOrgEntity = this.get(orgId);
//		//如果当前id不存在，直接返回空值
//		if(sysOrgEntity == null){
//			return ids;
//		}
//		ids.add(sysOrgEntity.getId());
//		if(sysOrgEntity.getHasChildren()){
//			Map<String,Object> searchParam = Maps.newHashMap();
//			searchParam.put("parentId",sysOrgEntity.getId());
//			List<SysOrgEntity> sysOrgEntities = this.queryAll(searchParam);
//			if(!UtilCollection.sizeIsEmpty(sysOrgEntities)) {
//				sysOrgEntities.forEach(sysOrgEntity1 -> {
//					ids.addAll(getOrgIds(sysOrgEntity1.getId()));
//				});
//			}
//		}
		Set<SysOrgEntity> sysOrgEntities = getOrgsByParentId(orgId);
		ids = sysOrgEntities.stream().map(SysOrgEntity:: getId).collect(Collectors.toSet());
		return ids;
	}



	/**
	 *根据组织ID循环遍历所有子组织，得到组织id下所有组织组织
	 *
	 * @param orgId
	 * @author liuke
	 * @date 2021/12/2 18:47
	 * @return java.util.Set<com.fosung.system.support.system.entity.sys.SysOrgEntity>
	 */
	@Override
	public Set<SysOrgEntity> getOrgsByParentId(Long orgId){
		SysOrgEntity sysOrg = this.get(orgId);
		if(sysOrg==null){
			return Sets.newHashSet();
		}
		Map<String,Object> searchParam = Maps.newHashMap();
		searchParam.put("orgCode",sysOrg.getOrgCode());
		searchParam.put("projectId",sysOrg.getProjectId());
		List<SysOrgEntity> sysOrgEntities = this.queryAll(searchParam,new String[]{"num:asc","createDatetime:asc"});
		return Sets.newHashSet(sysOrgEntities);
	}

	@Override
	public Set<SysOrgEntity> getChildOrgsByParentId(Long orgId) {
		LinkedHashSet<SysOrgEntity> entities = new LinkedHashSet<>();
		SysOrgEntity sysOrgEntity = this.get(orgId);
		//如果当前id不存在，直接返回空值
		if(sysOrgEntity == null){
			return entities;
		}
		entities.add(sysOrgEntity);
		Map<String,Object> searchParam = Maps.newHashMap();
		searchParam.put("parentId",sysOrgEntity.getId());
		String[] orderBy = new String[]{"num:asc","createDatetime:asc"};
		List<SysOrgEntity> orgEntities = this.queryAll(searchParam,orderBy);
		if(!UtilCollection.sizeIsEmpty(orgEntities)) {
			orgEntities.forEach(sysOrgEntity1 -> {
				sysOrgEntity1.setParentName(sysOrgEntity.getOrgName());
				entities.addAll(getOrgsByParentId(sysOrgEntity1.getId()));
			});
		}
		return Sets.newHashSet(entities);
	}

	/**
	 *根据用户id获取用户组织信息
	 *
	 * @param userId
	 * @author liuke
	 * @date 2021/4/23 8:57
	 * @return java.util.List<SysOrgEntity>
	 */
	@Override
	public SysOrgEntity getOrgByUserId(Long userId){
		return entityDao.selectByUserId(userId);
	}


	/**
	 *查询全部组织人员树
	 *
	 * @param searchParams
	 * @author liuke
	 * @date 2021/11/5 9:58
	 * @return java.util.List<com.fosung.system.support.system.entity.sys.ReturnTreeData>
	 */
	@Override
	public List<ReturnTreeData> queryUserOrgTree(Map<String, Object> searchParams) {
		List<ReturnTreeData> data = Lists.newArrayList();
		List<SysOrgEntity> entities = super.queryAll(searchParams);
		Map<String,Object> searchParam = Maps.newHashMap();
		for (SysOrgEntity entity : entities) {
			ReturnTreeData returnTreeData = new ReturnTreeData();
			returnTreeData.setId(entity.getId());
			returnTreeData.setCode(entity.getOrgCode());
			returnTreeData.setName(entity.getOrgName());
			returnTreeData.setType("0");
			returnTreeData.setParentId(entity.getParentId());
			data.add(returnTreeData);
			searchParam.put("orgId",entity.getId());
			List<SysUserEntity> users = sysUserService.queryAll(searchParam);
			users.stream().forEach(user ->{
				ReturnTreeData returnTree = new ReturnTreeData();
				returnTree.setId(user.getId());
				returnTree.setCode(user.getSex());
				returnTree.setName(user.getUserName());
				returnTree.setType("1");
				returnTree.setImg(user.getHeadImgUrl());
				returnTree.setParentId(returnTreeData.getId());
				data.add(returnTree);
			});
		}
    return data;

	}




	/**
	 *查询全部组织树
	 *
	 * @author liuke
	 * @date 2021/11/5 9:58
	 * @return java.util.List<com.fosung.system.support.system.entity.sys.ReturnTreeData>
	 */
	@Override
	public List<ReturnTreeData> queryOrgTree(Long projectId ,String orgType) {
		List<ReturnTreeData> data = Lists.newArrayList();
		Map<String,Object> searchParam = Maps.newHashMap();
		if(projectId!=null){
			searchParam.put("projectId",projectId);
		}
		if(UtilString.isNotBlank(orgType)){
			searchParam.put("orgType",orgType);
		}
		List<SysOrgEntity> entities = super.queryAll(searchParam);
		for (SysOrgEntity entity : entities) {
			ReturnTreeData returnTreeData = new ReturnTreeData();
			returnTreeData.setId(entity.getId());
			returnTreeData.setCode(entity.getOrgCode());
			returnTreeData.setName(entity.getOrgName());
			returnTreeData.setType("0");
			returnTreeData.setParentId(entity.getParentId());
			data.add(returnTreeData);

		}
		return data;

	}

	/**
	 *根据父节点查询组织
	 *
	 * @author liuke
	 * @date 2021/11/5 9:58
	 * @return java.util.List<com.fosung.system.support.system.entity.sys.ReturnTreeData>
	 */
	@Override
	public List<SysOrgEntity> queryOrgsByParentId(Map<String,Object> searchParam) {
		List<SysOrgEntity> entities = entityDao.queryOrgsByParentId(searchParam);
		return entities;

	}

	/**
	 *根据父节点查询子集组织（懒加载用）
	 *
	 * @author liuke
	 * @date 2021/11/5 9:58
	 * @return java.util.List<com.fosung.system.support.system.entity.sys.ReturnTreeData>
	 */
	@Override
	public List<ReturnTreeData> queryOrgsByParentId(Long orgId) {
		Map<String,Object> searchParam = Maps.newHashMap();
		searchParam.put("parentId",orgId);
		List<SysOrgEntity> entities = entityDao.queryOrgsByParentId(searchParam);
		List<ReturnTreeData> returnTreeDatas = Lists.newArrayList();
		for (SysOrgEntity entity : entities) {
			returnTreeDatas.add(new ReturnTreeData(entity.getId(),entity.getOrgCode(),entity.getOrgName(),"org","",entity.getParentId()));
		}
		return returnTreeDatas;

	}
	/**
	 *根据角色管理范围查询组织
	 *
	 * @param userId
	 * @param roleId
	 * @param projectId
	 * @author liuke
	 * @date 2021/12/2 18:48
	 * @return java.util.List<com.fosung.system.support.system.entity.sys.SysOrgEntity>
	 */
	@Override
	public List<SysOrgEntity> getOrgByRoleIdAndUserId(Long userId,Long roleId,Long projectId,String orgType){
		if(roleId == null){
			Map<String,Object> searchParam = Maps.newHashMap();
			searchParam.put("userId",userId);
			searchParam.put("roleIdNull","123");
			searchParam.put("projectId",projectId);
			List<SysUserRoleScopEntity> sysUserRoleScopEntities = sysUserRoleScopService.queryAll(searchParam);
			if(UtilCollection.sizeIsEmpty(sysUserRoleScopEntities)){
				return Lists.newArrayList();
			}
			Set<SysOrgEntity> sysOrgEntities = Sets.newHashSet();
			for (SysUserRoleScopEntity sysUserRoleScopEntity : sysUserRoleScopEntities) {
				sysOrgEntities.addAll(getChildOrgsByParentId(sysUserRoleScopEntity.getOrgId()));
			}
			return Lists.newArrayList(sysOrgEntities);
		}else {
			SysRoleEntity sysRoleEntity = sysRoleService.get(roleId);
			if(sysRoleEntity == null){
				return Lists.newArrayList();
			}
			if(sysRoleEntity.getRoleType().equals(RoleType.USER)){
				return Lists.newArrayList();
			}else if(sysRoleEntity.getRoleType().equals(RoleType.superadmin)){
				Map<String,Object> searchParam = Maps.newHashMap();
				searchParam.put("projectId",projectId);
				if(UtilString.isNotBlank(orgType)){
					searchParam.put("orgType",orgType);
				}
				return this.queryAll(searchParam,new String[]{"num:asc","createDatetime:asc"});
			}else if (sysRoleEntity.getRoleType().equals(RoleType.ADMIN)){
				HashMap<String,Object> searchParam = Maps.newHashMap();
				searchParam.put("userId",userId);
				searchParam.put("roleId",roleId);
				searchParam.put("shelvesType","role");
				searchParam.put("projectId",projectId);
				searchParam.put("orgType",orgType);
				List<SysUserRoleScopEntity> sysUserRoleScopEntities = sysUserRoleScopService.queryAllUserRoleScope(searchParam);
				if(UtilCollection.sizeIsEmpty(sysUserRoleScopEntities)){
					return Lists.newArrayList();
				}
				Set<SysOrgEntity> sysOrgEntities = Sets.newHashSet();
				for (SysUserRoleScopEntity sysUserRoleScopEntity : sysUserRoleScopEntities) {
					sysOrgEntities.addAll(getChildOrgsByParentId(sysUserRoleScopEntity.getOrgId()));
				}
				return Lists.newArrayList(sysOrgEntities);
			}else {
				return Lists.newArrayList();
			}
		}

	}

	/**
	 *根据角色管理范围查询党组织
	 *
	 * @param userId
	 * @param roleId
	 * @param projectId
	 * @author liuke
	 * @date 2021/12/2 18:48
	 * @return java.util.List<com.fosung.system.support.system.entity.sys.SysOrgEntity>
	 */
	@Override
	public List<SysOrgEntity> getPartyOrgByRoleIdAndUserId(Long userId,Long roleId,Long projectId){
		SysRoleEntity sysRoleEntity = sysRoleService.get(roleId);
		if(sysRoleEntity == null){
			return Lists.newArrayList();
		}
		if(sysRoleEntity.getRoleType().equals(RoleType.USER)){
			return Lists.newArrayList();
		}else if(sysRoleEntity.getRoleType().equals(RoleType.superadmin)){
			Map<String,Object> searchParam = Maps.newHashMap();
			searchParam.put("projectId",projectId);
			searchParam.put("orgType","party");
			return this.queryAll(searchParam);
		}else if (sysRoleEntity.getRoleType().equals(RoleType.ADMIN)){
			Map<String,Object> searchParam = Maps.newHashMap();
			searchParam.put("userId",userId);
			searchParam.put("roleId",roleId);
			searchParam.put("projectId",projectId);
			List<SysUserRoleScopEntity> sysUserRoleScopEntities = sysUserRoleScopService.queryAll(searchParam);
			if(UtilCollection.sizeIsEmpty(sysUserRoleScopEntities)){
				return Lists.newArrayList();
			}
			Set<SysOrgEntity> sysOrgEntities = Sets.newHashSet();
			for (SysUserRoleScopEntity sysUserRoleScopEntity : sysUserRoleScopEntities) {
				sysOrgEntities.addAll(getPartOrgsByParentId(sysUserRoleScopEntity.getOrgId()));
			}
			return Lists.newArrayList(sysOrgEntities);
		}else {
			return Lists.newArrayList();
		}

	}

	/**
	 *根据组织ID循环遍历所有子党组织，得到组织id下所有组织组织
	 *
	 * @param orgId
	 * @author liuke
	 * @date 2021/12/2 18:47
	 * @return java.util.Set<com.fosung.system.support.system.entity.sys.SysOrgEntity>
	 */
	public Set<SysOrgEntity> getPartOrgsByParentId(Long orgId){
		Set<SysOrgEntity> entities = Sets.newHashSet();
		SysOrgEntity sysOrgEntity = this.get(orgId);
		//如果当前id不存在，直接返回空值
		if(sysOrgEntity == null||sysOrgEntity.getOrgType().equals(OrgType.administration)){
			return entities;
		}
		entities.add(sysOrgEntity);
		Map<String,Object> searchParam = Maps.newHashMap();
		searchParam.put("parentId",sysOrgEntity.getId());
		List<SysOrgEntity> orgEntities = this.queryAll(searchParam);
		if(!UtilCollection.sizeIsEmpty(orgEntities)) {
			orgEntities.forEach(sysOrgEntity1 -> {
				sysOrgEntity1.setParentName(sysOrgEntity.getOrgName());
				entities.addAll(getOrgsByParentId(sysOrgEntity1.getId()));
			});
		}

		return entities;
	}


	/**
	 *根据角色管理范围懒加载查询组织
	 *
	 * @param userId
	 * @param roleId
	 * @param projectId
	 * @author liuke
	 * @date 2021/12/2 18:48
	 * @return java.util.List<com.fosung.system.support.system.entity.sys.SysOrgEntity>
	 */
	@Override
	public List<SysOrgEntity> getLazyOrgByRoleIdAndUserId(Long userId,Long roleId,Long projectId){
		if(roleId != null){
			SysRoleEntity sysRoleEntity = sysRoleService.get(roleId);
			if(sysRoleEntity == null){
				return Lists.newArrayList();
			}
			if(sysRoleEntity.getRoleType().equals(RoleType.USER)){
				return Lists.newArrayList();
			}else if(sysRoleEntity.getRoleType().equals(RoleType.superadmin)){
				Map<String,Object> searchParam = Maps.newHashMap();
				searchParam.put("projectId",projectId);
				searchParam.put("parentIdIsNull","123");
				return this.queryAll(searchParam);
			}else if (sysRoleEntity.getRoleType().equals(RoleType.ADMIN)){
				Map<String,Object> searchParam = Maps.newHashMap();
				searchParam.put("userId",userId);
				searchParam.put("roleId",roleId);
				searchParam.put("projectId",projectId);
				List<SysUserRoleScopEntity> sysUserRoleScopEntities = sysUserRoleScopService.queryAll(searchParam);
				if(UtilCollection.sizeIsEmpty(sysUserRoleScopEntities)){
					return Lists.newArrayList();
				}
				Set<SysOrgEntity> sysOrgEntities = Sets.newHashSet();
				for (SysUserRoleScopEntity sysUserRoleScopEntity : sysUserRoleScopEntities) {
					sysOrgEntities.add(get(sysUserRoleScopEntity.getOrgId()));
				}
				return Lists.newArrayList(sysOrgEntities);
			}else {
				return Lists.newArrayList();
			}
		}else {
			Map<String,Object> searchParam = Maps.newHashMap();
			searchParam.put("userId",userId);
			searchParam.put("roleIdNull","123");
			searchParam.put("projectId",projectId);
			List<SysUserRoleScopEntity> sysUserRoleScopEntities = sysUserRoleScopService.queryAll(searchParam);
			if(UtilCollection.sizeIsEmpty(sysUserRoleScopEntities)){
				return Lists.newArrayList();
			}
			Set<SysOrgEntity> sysOrgEntities = Sets.newHashSet();
			for (SysUserRoleScopEntity sysUserRoleScopEntity : sysUserRoleScopEntities) {
				sysOrgEntities.add(get(sysUserRoleScopEntity.getOrgId()));
			}
			return Lists.newArrayList(sysOrgEntities);
		}
	}

	/**
	 *根据角色管理范围懒加载查询党组织
	 *
	 * @param userId
	 * @param roleId
	 * @param projectId
	 * @author liuke
	 * @date 2021/12/2 18:48
	 * @return java.util.List<com.fosung.system.support.system.entity.sys.SysOrgEntity>
	 */
	@Override
	public List<SysOrgEntity> getPartyLazyOrgByRoleIdAndUserId(Long userId,Long roleId,Long projectId){
		SysRoleEntity sysRoleEntity = sysRoleService.get(roleId);
		if(sysRoleEntity == null){
			return Lists.newArrayList();
		}
		if(sysRoleEntity.getRoleType().equals(RoleType.USER)){
			return Lists.newArrayList();
		}else if(sysRoleEntity.getRoleType().equals(RoleType.superadmin)){
			Map<String,Object> searchParam = Maps.newHashMap();
			searchParam.put("projectId",projectId);
			searchParam.put("parentIdIsNull","123");
			searchParam.put("orgType","party");
			return this.queryAll(searchParam);
		}else if (sysRoleEntity.getRoleType().equals(RoleType.ADMIN)){
			Map<String,Object> searchParam = Maps.newHashMap();
			searchParam.put("userId",userId);
			searchParam.put("roleId",roleId);
			searchParam.put("projectId",projectId);
			List<SysUserRoleScopEntity> sysUserRoleScopEntities = sysUserRoleScopService.queryAll(searchParam);
			if(UtilCollection.sizeIsEmpty(sysUserRoleScopEntities)){
				return Lists.newArrayList();
			}
			Set<SysOrgEntity> sysOrgEntities = Sets.newHashSet();
			for (SysUserRoleScopEntity sysUserRoleScopEntity : sysUserRoleScopEntities) {
				SysOrgEntity sysOrgEntity = get(sysUserRoleScopEntity.getOrgId());
				if (sysOrgEntity.getOrgType().equals(OrgType.party)){
					sysOrgEntities.add(sysOrgEntity);
				}
			}
			return Lists.newArrayList(sysOrgEntities);
		}else {
			return Lists.newArrayList();
		}

	}


	/**
	 *根据组织id获取组织人员树（通讯录）
	 *
	 * @param orgId
	 * @author liuke
	 * @date 2021/12/6 14:09
	 * @return java.util.List<com.fosung.system.support.system.entity.sys.ReturnTreeData>
	 */
	@Override
	public List<ReturnTreeData> queryOrgAndUserTree(Long orgId){
		List<ReturnTreeData> returnTreeDataList = Lists.newArrayList();
		Set<SysOrgEntity> entities = getOrgsByParentId(orgId);
		for (SysOrgEntity entity : entities) {
			returnTreeDataList.add(new ReturnTreeData(entity.getId(),entity.getOrgCode(),entity.getOrgName(),"org","",entity.getParentId()));
		    Map<String,Object> searchParam = Maps.newHashMap();
		    searchParam.put("orgId",entity.getId());
		    List<SysUserEntity> sysUserEntities = sysUserService.queryAll(searchParam);
		    if(!UtilCollection.sizeIsEmpty(sysUserEntities)){
				for (SysUserEntity sysUserEntity : sysUserEntities) {
					returnTreeDataList.add(new ReturnTreeData(sysUserEntity.getId(),"",sysUserEntity.getRealName(),"user",sysUserEntity.getHeadImgUrl(),entity.getId()));
				}
			}
		}
		return returnTreeDataList;

	}
	/**
	 *懒加载查询通讯录
	 *
	 * @param orgId
	 * @param projectId
	 * @param orgType
	 * @author liuke
	 * @date 2022/1/14 10:12
	 * @return java.util.List<com.fosung.system.support.system.entity.sys.ReturnTreeData>
	 */
	@Override
	public List<ReturnTreeData> queryOrgAndUserTreeLazy(String orgId,Long projectId,String orgType){
		List<ReturnTreeData> returnTreeDataList = Lists.newArrayList();
		Map<String,Object> searchParamOrg = Maps.newHashMap();
		searchParamOrg.put("projectId",projectId);
		searchParamOrg.put("orgType",orgType);
		if(UtilString.isBlank(orgId)){
			searchParamOrg.put("parentIdIsNull",1);
		}else {
			searchParamOrg.put("parentId",Long.valueOf(orgId));
		}
		List<SysOrgEntity> entities = queryAll(searchParamOrg);

		for (SysOrgEntity entity : entities) {
			returnTreeDataList.add(new ReturnTreeData(entity.getId(),entity.getOrgCode(),entity.getOrgName(),"org","",entity.getParentId()));
		}
		if(UtilString.isBlank(orgId)){
			return returnTreeDataList;
		}
		Map<String,Object> searchParam = Maps.newHashMap();
		searchParam.put("orgId",Long.valueOf(orgId));
		List<SysUserEntity> sysUserEntities = sysUserService.queryAll(searchParam);
		if(!UtilCollection.sizeIsEmpty(sysUserEntities)){
			for (SysUserEntity sysUserEntity : sysUserEntities) {
				returnTreeDataList.add(new ReturnTreeData(sysUserEntity.getId(),"",sysUserEntity.getRealName(),"user",sysUserEntity.getHeadImgUrl(),Long.valueOf(orgId)));
			}
		}
		return returnTreeDataList;

	}

	/**
	 *懒加载查询组织
	 *
	 * @param orgId
	 * @param projectId
	 * @param orgType
	 * @author liuke
	 * @date 2022/1/14 10:12
	 * @return java.util.List<com.fosung.system.support.system.entity.sys.ReturnTreeData>
	 */
	@Override
	public List<ReturnTreeData> queryOrgTreeLazy(String orgId,Long projectId,String orgType){
		List<ReturnTreeData> returnTreeDataList = Lists.newArrayList();
		Map<String,Object> searchParamOrg = Maps.newHashMap();
		searchParamOrg.put("projectId",projectId);
		searchParamOrg.put("orgType",orgType);
		if(UtilString.isBlank(orgId)){
			searchParamOrg.put("parentIdIsNull",1);
		}else {
			searchParamOrg.put("parentId",Long.valueOf(orgId));
		}
		List<SysOrgEntity> entities = queryAll(searchParamOrg);

		for (SysOrgEntity entity : entities) {
			returnTreeDataList.add(new ReturnTreeData(entity.getId(),entity.getOrgCode(),entity.getOrgName(),"org","",entity.getParentId()));
		}
		return returnTreeDataList;

	}

	/**
	 *根据项目获取组织人员树（通讯录）
	 *
	 * @param projectId
	 * @param orgType
	 * @author liuke
	 * @date 2021/12/6 14:09
	 * @return java.util.List<com.fosung.system.support.system.entity.sys.ReturnTreeData>
	 */
	@Override
	public List<ReturnTreeData> queryAllOrgAndUserTree(Long projectId,String orgType){
		List<ReturnTreeData> returnTreeDataList = Lists.newArrayList();
		Map<String,Object> orgSearchParam = Maps.newHashMap();
		if(projectId!=null){
			orgSearchParam.put("projectId",projectId);
		}
		if(UtilString.isNotBlank(orgType)){
			orgSearchParam.put("orgType",orgType);
		}
		Set<SysOrgEntity> entities = Sets.newHashSet(queryAll(orgSearchParam));
		for (SysOrgEntity entity : entities) {
			returnTreeDataList.add(new ReturnTreeData(entity.getId(),entity.getOrgCode(),entity.getOrgName(),"org","",entity.getParentId()));
			Map<String,Object> searchParam = Maps.newHashMap();
			searchParam.put("orgId",entity.getId());
			List<SysUserEntity> sysUserEntities = sysUserService.queryAll(searchParam);
			if(!UtilCollection.sizeIsEmpty(sysUserEntities)){
				for (SysUserEntity sysUserEntity : sysUserEntities) {
					returnTreeDataList.add(new ReturnTreeData(sysUserEntity.getId(),"",sysUserEntity.getRealName(),"user",sysUserEntity.getHeadImgUrl(),entity.getId()));
				}
			}
		}
		return returnTreeDataList;

	}

	/**
	 *根据组织id获取组织树
	 *
	 * @param orgId
	 * @author liuke
	 * @date 2021/12/6 14:09
	 * @return java.util.List<com.fosung.system.support.system.entity.sys.ReturnTreeData>
	 */
	@Override
	public List<ReturnTreeData> queryReturnOrgTree(Long orgId){
		List<ReturnTreeData> returnTreeDataList = Lists.newArrayList();
		Set<SysOrgEntity> entities = getOrgsByParentId(orgId);
		for (SysOrgEntity entity : entities) {
			returnTreeDataList.add(new ReturnTreeData(entity.getId(),entity.getOrgCode(),entity.getOrgName(),"org","",entity.getParentId()));
		}
		return returnTreeDataList;

	}


	/**
	 * 分页查询组织
	 * @param searchParam
	 * @param pageable
	 * @return
	 */
	@Override
	public Page<SysOrgEntity> queryPage(Map<String, Object> searchParam, Pageable pageable) {
		return entityDao.queryPage(searchParam,pageable);
	}


	@Override
	public void deleteInfo(List<Long> collect) {
		// 删除本级
		this.delete(collect);
		// 查询二级
		Map<String, Object> searchParam = Maps.newHashMap();
		searchParam.put("parentIds",collect);
		while (true){
			List<SysOrgEntity> sysOrgEntities = this.queryAll(searchParam);
			this.delete(sysOrgEntities.stream().map(map -> map.getId()).collect(Collectors.toList()));
			searchParam.put("parentIds",sysOrgEntities.stream().map(map -> map.getId()).collect(Collectors.toList()));
			if(UtilCollection.isEmpty(sysOrgEntities)){
				break;
			}
		}
	}

	/**
	 * 保存之前的业务操作
	 * @param entity
	 */
	@Override
	public void preSaveHandler(SysOrgEntity entity) {
		super.preSaveHandler(entity);
		if(StringUtils.isBlank(entity.getOrgCode())){
			Map<String,Object> param = Maps.newHashMap();
			if(entity.getParentId()!=null){
				param.put("parentId",entity.getParentId());
				SysOrgEntity sysOrgEntity = get(entity.getParentId());
				List<SysOrgEntity> sysOrgEntities = this.queryAll(param);
				List<String> codes = sysOrgEntities.stream().map(SysOrgEntity::getOrgCode).collect(Collectors.toList());
				if(UtilString.isBlank(entity.getOrgCode())){
					try {
						entity.setOrgCode(CodeGenerateTool.generateOrgCode(sysOrgEntity.getOrgCode(),codes));
					} catch (Exception e) {
						entity.setOrgCode("000001");
					}
				}
			}else {
				param.put("parentIdIsNull","123");
				param.put("projectId",entity.getProjectId());
				List<SysOrgEntity> sysOrgEntities = this.queryAll(param);
				List<String> codes = sysOrgEntities.stream().map(SysOrgEntity::getOrgCode).collect(Collectors.toList());
				try {
					entity.setOrgCode(CodeGenerateTool.generateOrgCode("000001",codes));
				} catch (Exception e) {
					entity.setOrgCode("000001");
				}
			}

		}
	}

	/**
	 *更新之后的操作
	 *
	 * @param sysOrgEntity
	 * @param updateFields
	 * @author liuke
	 * @date 2022/2/28 15:08
	 * @return void
	 */
	@Override
	public void postUpdateHandler(SysOrgEntity sysOrgEntity,Collection<String> updateFields){
		super.postUpdateHandler(sysOrgEntity,updateFields);
		SysOrgEntity sysOrg = this.get(sysOrgEntity.getId());
		//发送工单消息修改组织
		if(sysOrg.getDel()){
			//发送工单消息修改组织
			sysMQProducerService.sendOrgMessage(sysOrg,"deleteOrg");
		}else {
			sysMQProducerService.sendOrgMessage(sysOrg,"updateOrg");
		}
	}
	/**
	 *保存之后的操作
	 *
	 * @param sysOrg
	 * @author liuke
	 * @date 2022/2/28 15:15
	 * @return void
	 */
	@Override
	public void postSaveHandler(SysOrgEntity sysOrg){
		super.postSaveHandler(sysOrg);
		//新增后发送工单消息
		sysMQProducerService.sendOrgMessage(sysOrg,"addOrg");
	}

	/**
	 *删除后的操作
	 *
	 * @param id
	 * @author liuke
	 * @date 2022/2/28 15:18
	 * @return void
	 */
	@Override
	public void postDeleteHandler(Long id){
		super.postDeleteHandler(id);
		SysOrgEntity sysOrg = this.get(id);
		//发送工单消息修改组织
		sysMQProducerService.sendOrgMessage(sysOrg,"deleteOrg");
	}

	/**
	 *12.查询所有直属上级，以组织树结构返回，返回数据包含本级
	 * 
	 * @param id
	 * @author liuke
	 * @date 2022/2/17 11:27
	 * @return java.util.List<com.fosung.system.support.system.entity.sys.SysOrgEntity>
	 */
	public List<SysOrgEntity> getParentOrgs(Long id){
		List<SysOrgEntity> sysOrgEntities = Lists.newArrayList();
		if(id!=null){
			SysOrgEntity sysOrgEntity = this.get(id);
			sysOrgEntities.add(sysOrgEntity);
			sysOrgEntities.addAll(getParentOrgs(sysOrgEntity.getParentId())) ;
		}
		return sysOrgEntities;
	}

	/**
	 *12.查询所有直属上级到rootId，以组织树结构返回，返回数据包含本级
	 *
	 * @param id
	 * @author liuke
	 * @date 2022/2/17 11:27
	 * @return java.util.List<com.fosung.system.support.system.entity.sys.SysOrgEntity>
	 */
	public List<SysOrgEntity> getParentOrgs(Long id,Long rootId){
		List<SysOrgEntity> sysOrgEntities = Lists.newArrayList();
		if(id.equals(rootId)){
			SysOrgEntity sysOrgEntity = this.get(id);
			sysOrgEntities.add(sysOrgEntity);
		}else if(id!=null){
			SysOrgEntity sysOrgEntity = this.get(id);
			sysOrgEntities.addAll(getParentOrgs(sysOrgEntity.getParentId(),rootId)) ;
			sysOrgEntities.add(sysOrgEntity);
		}
		return sysOrgEntities;
	}

	/**
	 *12.查询所有直属上级到rootId，以组织树结构返回，返回数据包含本级
	 *
	 * @param id
	 * @author liuke
	 * @date 2022/2/17 11:27
	 * @return java.util.List<com.fosung.system.support.system.entity.sys.SysOrgEntity>
	 */
	public List<SysOrgEntity> getParentOrgs(Long id,String levelType){
		List<SysOrgEntity> sysOrgEntities = Lists.newArrayList();
		 if(id!=null){
			SysOrgEntity sysOrgEntity = this.get(id);
			if(!sysOrgEntity.getLevelType().equals(levelType)){
				sysOrgEntities.addAll(getParentOrgs(sysOrgEntity.getParentId(),levelType)) ;
			}
			sysOrgEntities.add(sysOrgEntity);
		}
		return sysOrgEntities;
	}



	@Override
	public SysOrgEntity queryParentOrgById( Map<String,Object> searchParam){
		return entityDao.queryParentOrgById(searchParam);
	}

	/**
	 *查询用户管理范围
	 *
	 * @param searchParam
	 * @author liuke
	 * @date 2022/3/31 15:53
	 * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
	 */
	@Override
	public List<Map<String,Object>> queryScopOrgByUser(Map<String,Object> searchParam){
		return entityDao.queryScopOrgByUser(searchParam);
	}
}
