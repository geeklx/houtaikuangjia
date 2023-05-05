package com.fosung.system.support.system.service.sys;

import java.util.*;
import java.util.stream.Collectors;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.common.util.UtilBean;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.common.util.UtilString;
import com.fosung.framework.common.util.UtilTree;
import com.fosung.system.support.system.dao.sys.SysRoleDao;
import com.fosung.system.support.system.dict.*;
import com.fosung.system.support.system.dto.sys.SysResourceDto;
import com.fosung.system.support.system.dto.sys.SysRoleDto;
import com.fosung.system.support.system.dto.sys.SysRoleResourceDto;
import com.fosung.system.support.system.entity.sys.*;
import com.fosung.system.support.system.service.feign.DtSourceService;
import com.fosung.system.support.system.vo.SysRoleAndResourceVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;

@Service
public class SysRoleServiceImpl extends AppJPABaseDataServiceImpl<SysRoleEntity, SysRoleDao>
	implements SysRoleService {

	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Autowired
	private SysRoleResourceService sysRoleResourceService;

	@Autowired
	private SysRoleAppService sysRoleAppService;

	@Autowired
	private SysResourceService sysResourceService;

	@Autowired
	private SysRoleScopService sysRoleScopService;

	@Autowired
	private SysApplicationService sysApplicationService;

	@Autowired
	private DtSourceService dtSourceService;

	@Autowired
	private SysUserRoleScopService sysUserRoleScopService;

	@Autowired
	private SysResourceAppService sysResourceAppService;
	
	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("ids", "id:IN");
				put("appId","appId:EQ");
				put("roleCode","roleCode:EQ");
				put("id", "id:EQ");
				put("roleNameL","roleName:LIKE");
				put("roleName","roleName:EQ");
				put("roleNames","roleName:IN");
				put("roleType","roleType:EQ");
				put("projectId","projectId:EQ");
				put("del","del:EQ");
				put("parentId","parentId:EQ");
				put("status","status:EQ");
				put("level","level:EQ");
				put("menuName","menuName:LIKE");
				put("menuCode","menuCode:RLIKE");
				put("startTime", "createDatetime:GTEDATE");
				put("endTime", "createDatetime:LTEDATE");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}


	/**
	  * 根据用户id获取角色列表
	  *
	  * @param userId
	  * @return java.util.List<SysRoleEntity>
	  * @author liuke
	  * @date 2021/4/7 9:37
	  */
	@Override
	public List<SysRoleEntity> queryRoleListByUserId(Long userId){
		HashMap<String,Object> searchParam= Maps.newHashMap();
		searchParam.put("userId",userId);
		List<SysUserRoleEntity> sysUserRoleEntities=sysUserRoleService.queryAll(searchParam);
		if(!UtilCollection.sizeIsEmpty(sysUserRoleEntities)){
			Set<Long> ids = UtilCollection.extractToSet(sysUserRoleEntities,"roleId",Long.class);
			if(!UtilCollection.sizeIsEmpty(ids)){
				HashMap<String,Object> searchRoleParam = Maps.newHashMap();
				searchRoleParam.put("ids",ids);
				return this.queryAll(searchRoleParam);
			}
		}
		return null;
	}

	/**
	 *
	 * 绑定资源到角色
	 * @param roleId
	 * @param resourceIds 
	 * @author liuke
	 * @date 2021/4/8 8:57
	 * @return void
	 */
//	public void bindResource(Long roleId,Set<Long> resourceIds){
//		if(UtilCollection.sizeIsEmpty(resourceIds)){
//			return;
//		}
//		ArrayList<SysRoleResourceEntity> roleResourceEntities = new ArrayList<SysRoleResourceEntity>();
//		resourceIds.forEach(resourceId ->{
//			roleResourceEntities.add(new SysRoleResourceEntity(roleId,resourceId));
//		});
//		sysRoleResourceService.saveBatch(roleResourceEntities);
//	}

	/**
	 *移除原有资源，重新绑定资源
	 *
	 * @param roleId
	 * @param resourceIds
	 * @author liuke
	 * @date 2021/4/8 10:19
	 * @return void
	 */
//	public void rebindResource(Long roleId,Set<Long> resourceIds ){
//		if(!UtilCollection.sizeIsEmpty(resourceIds)){
//			List<SysRoleResourceEntity> sysRoleResourceEntities = sysRoleResourceService.getRoleResourceByRole(roleId);
//			if(!UtilCollection.sizeIsEmpty(sysRoleResourceEntities)) {
//				Set<Long> ids = UtilCollection.extractToSet(sysRoleResourceEntities, "id", Long.class);
//			    sysRoleResourceService.delete(ids);
//			}
//			bindResource(roleId,resourceIds);
//		}
//	}

	/**
	 * 描述: 角色重新绑定应用资源
	 * @param sysRoleResourceDto
	 * @return void
	 * @author fuhao
	 * @date 2021/11/29 16:51
	 **/
//	@Override
//	public void rebindResource(SysRoleResourceDto sysRoleResourceDto) {
//		if(!UtilCollection.sizeIsEmpty(sysRoleResourceDto.getBindScopes())){
//			delBindResource(sysRoleResourceDto);
//			bindResource(sysRoleResourceDto);
//		}
//	}

	/**
	 * 描述: 取消角色已绑定的资源
	 * @param sysRoleResourceDto
	 * @return void
	 * @author fuhao
	 * @date 2022/2/16 18:17
	 **/
	@Override
	public void delBindResource(SysRoleResourceDto sysRoleResourceDto){
		if(!UtilCollection.sizeIsEmpty(sysRoleResourceDto.getBindScopes())){
			List<SysRoleResourceEntity> sysRoleResourceEntities = sysRoleResourceService.getRoleResourceByRole(sysRoleResourceDto.getRoleId());
			if(!UtilCollection.sizeIsEmpty(sysRoleResourceEntities)) {
				Set<Long> ids = UtilCollection.extractToSet(sysRoleResourceEntities, "id", Long.class);
				sysRoleResourceService.delete(ids);
			}
		}
	}

	/**
	 * 描述: 角色绑定资源
	 * @param sysRoleResourceDto
	 * @return void
	 * @author fuhao
	 * @date 2021/11/29 16:54
	 **/
	@Override
	public void bindResource(SysRoleResourceEntity sysRoleResourceDto){
		if(UtilCollection.sizeIsEmpty(sysRoleResourceDto.getResources())){
			return;
		}
		List<SysRoleResourceEntity> bindResource = new ArrayList<>();
		List<Long> resourceIds = sysRoleResourceDto.getResources();
		resourceIds.forEach(resourceId ->{
			SysRoleResourceEntity sysRoleResource = new SysRoleResourceEntity();
			sysRoleResource.setAppId(sysRoleResourceDto.getAppId());
			sysRoleResource.setRoleId(sysRoleResourceDto.getRoleId());
			sysRoleResource.setResourceId(resourceId);
			sysRoleResource.setProjectId(sysRoleResourceDto.getProjectId());
			sysRoleResource.setSource(RoleSource.role);
			bindResource.add(sysRoleResource);
		});
		sysRoleResourceService.saveBatch(bindResource);
	}


	/**
	 *
	 * 角色重新绑定用户
	 * @param roleId
	 * @param userIds
	 * @author liuke
	 * @date 2021/4/8 11:34
	 * @return void
	 */
	public void rebindUser(Long roleId,Set<Long> userIds ){
		sysUserRoleService.deleteByRoleId(roleId);
		if(!UtilCollection.sizeIsEmpty(userIds)){
			List<SysUserRoleEntity> list = new ArrayList<SysUserRoleEntity>();
			userIds.forEach(userId ->{
				list.add(new SysUserRoleEntity(userId,roleId,null));
			});
			sysUserRoleService.saveBatch(list);
		}
	}

	/**
	 *根据编码获取角色
	 *
	 * @param roleCode
	 * @author liuke
	 * @date 2021/4/8 10:08
	 * @return SysRoleEntity
	 */
	public SysRoleEntity getRoleByCode(String roleCode){
		HashMap<String,Object> searchParam = Maps.newHashMap();
		searchParam.put("roleCode",roleCode);
		return queryAll(searchParam).get(0);
	}

	/**
	 *根据编码获取角色
	 *
	 * @param roleId
	 * @author liuke
	 * @date 2021/4/8 10:08
	 * @return SysRoleEntity
	 */
	public SysRoleEntity getRoleById(String roleId){
		HashMap<String,Object> searchParam = Maps.newHashMap();
		searchParam.put("roleId",roleId);
		return queryAll(searchParam).get(0);
	}

	/**
	 *根据用户id获取角色信息及关联的资源信息
	 *
	 * @param userId
	 * @author liuke
	 * @date 2021/4/19 15:29
	 * @return java.util.List<SysRoleAndResourceVo>
	 */
	@Override
	public List<SysRoleAndResourceVo> getRoleAndResourceByUser(Long userId){
		List<SysRoleAndResourceVo> resultList = Lists.newArrayList();
		Map<String,Object> userRoleSearchMap  = Maps.newHashMap();
		userRoleSearchMap.put("userId",userId);
		//根据用户id查询用户下所有角色
		List<SysUserRoleEntity> sysUserRoleEntities=sysUserRoleService.queryAll(userRoleSearchMap);
		//查询用户当前角色下的管理范围
		List<SysUserRoleScopEntity> su = sysUserRoleScopService.queryAllUserRoleScope(new HashMap<>(userRoleSearchMap));
		if(!UtilCollection.sizeIsEmpty(sysUserRoleEntities)) {
			sysUserRoleEntities.forEach(sysUserRoleEntity -> {
				SysRoleAndResourceVo sysRoleAndResourceVo = new SysRoleAndResourceVo();
				//查询角色基本信息
				SysRoleEntity sysRoleEntity=this.get(sysUserRoleEntity.getRoleId());
				sysRoleAndResourceVo = UtilBean.copyBean(sysRoleEntity,SysRoleAndResourceVo.class);
				sysRoleAndResourceVo.setRoleId(sysUserRoleEntity.getRoleId());
				//赋值管理的组织
				sysRoleAndResourceVo.setSysUserRoleScopEntity(su.stream().filter(map -> map.getRoleId().equals(sysRoleEntity.getId())).collect(Collectors.toList()));
				//sysRoleAndResourceVo.setOrgCode(sysUserRoleEntity.getOrgCode());
				//查询用户关联的所有资源
				SysResourceDto sysResourceDto = new SysResourceDto();
				sysResourceDto.setStatus(0);
				List<SysResourceEntity> sysResourceEntities = sysResourceService.queryResourceByRoleId(sysUserRoleEntity.getRoleId(),sysResourceDto);
                //将资源赋值到结果集
				if(sysResourceEntities==null){
					sysResourceEntities = Lists.newArrayList();
				}
				sysRoleAndResourceVo.setSysResourceEntities(sysResourceEntities);
				resultList.add(sysRoleAndResourceVo);

			});
		}
		return resultList;
	}

	/**
	 * 描述: 分页查询
	 * @param searchParam
	 * @param pageable
	 * @return org.springframework.data.domain.Page<com.fosung.system.support.system.entity.sys.SysRoleEntity>
	 * @author fuhao
	 * @date 2021/11/27 10:07
	 **/
	@Override
	public Page<SysRoleEntity> queryPage(Map<String, Object> searchParam, Pageable pageable) {
		return entityDao.queryPage(searchParam,pageable);
	}

	/**
	 * 描述: 项目下查询全部的角色
	 * @param sysRoleDto
	 * @return List
	 * @author fuhao
	 * @date 2021/11/27 10:05
	 **/
	@Override
	public List<SysRoleEntity> queryAllRole(SysRoleDto sysRoleDto) {
		HashMap<String, Object> searchParam = Maps.newHashMap();
		searchParam.put("projectId",sysRoleDto.getProjectId());
		searchParam.put("status", RoleStatus.normal);
		List<SysRoleEntity> allRoleInfo = this.queryAll(searchParam);
		searchParam.put("roleId",sysRoleDto.getId());
		List<SysRoleScopEntity> bindRoleScope = sysRoleScopService.queryAll(searchParam);
		for (SysRoleEntity sysRole : allRoleInfo){
			if(UtilCollection.isEmpty(bindRoleScope)) sysRole.setCheckFlag(false);
			for (SysRoleScopEntity sysRoleScope:bindRoleScope ) {
				if(Long.toString(sysRole.getId()).equals(Long.toString(sysRoleScope.getRoleScopId()))){
					sysRole.setCheckFlag(true);
					break;
				}else {
					sysRole.setCheckFlag(false);
				}
			}
		}
		return allRoleInfo;
	}

	/**
	 * 描述: 绑定角色管理范围
	 * @param sysRoleScop
	 * @return void
	 * @author fuhao
	 * @date 2021/11/27 10:58
	 **/
	@Override
	public void saveInfo(SysRoleDto sysRoleScop) {
		// 查询角色范围是否存在
		HashMap<String, Object> searchParam = Maps.newHashMap();
		searchParam.put("projectId",sysRoleScop.getProjectId());
		searchParam.put("roleId",sysRoleScop.getId());
		List<SysRoleScopEntity> sysRoleScopList = sysRoleScopService.queryAll(searchParam);
		// 如果存在就删除
		if(UtilCollection.isNotEmpty(sysRoleScopList)){
			sysRoleScopService.delete(sysRoleScopList.stream().map(map -> map.getId()).collect(Collectors.toList()));
		}
		List<SysRoleScopEntity> roles = sysRoleScop.getRoles();
		if(!UtilCollection.isEmpty(roles)){
			roles.forEach(role -> {
				role.setProjectId(sysRoleScop.getProjectId());
				role.setRoleId(sysRoleScop.getId());
			});
			// 重新保存
			sysRoleScopService.saveBatch(roles);
		}
	}

	/**
	 * 描述: 获取角色权限信息
	 * @param sysRoleResourceDto
	 * @return List`
	 * @author fuhao
	 * @date 2021/11/29 15:38
	 **/
	@Override
	public List<SysProjectApp> queryRolePower(SysRoleResourceDto sysRoleResourceDto) {
		// 查询全部的应用
		HashMap<String, Object> searchAllAppParam = Maps.newHashMap();
		searchAllAppParam.put("status",AppStatus.normal);
		searchAllAppParam.put("projectId",sysRoleResourceDto.getProjectId());
		List<SysProjectApp> queryAllApp = entityDao.queryProjectApp(searchAllAppParam);
		List<SysRoleResourceEntity> sysRoleResourceList = queryBindRolePower(sysRoleResourceDto);
		List<SysRoleAppEntity> sysRoleAppList = queryRoleBindApp(sysRoleResourceDto);
		for (SysProjectApp allApp:queryAllApp) {
			List<SysRoleResourceEntity> rolesList = new ArrayList<>();
			for (SysRoleResourceEntity role:sysRoleResourceList ) {
				if(Long.toString(allApp.getAppId()).equals(Long.toString(role.getAppId()))){
					if(AppType.BOX.equals(allApp.getAppType())||AppType.pcClient.equals(allApp.getAppType())||AppType.android.equals(allApp.getAppType())||AppType.ios.equals(allApp.getAppType())){
						List<Map<String,Object>> lists = sysResourceAppService.getWorkBenchApp(allApp.getAppId());
						lists = lists.stream().collect(Collectors.collectingAndThen(
								Collectors.toCollection(() -> new TreeSet<>(
										Comparator.comparing(a -> a.get("appId").toString())
								)),ArrayList::new
								));
						Map<Long,String> map = lists.stream().collect(Collectors.toMap( t -> Long.valueOf(t.get("appId").toString()) ,t -> t.get("appName").toString() ));
						role.setMenuName(map.get(role.getResourceId()));
					}
					rolesList.add(role);
				}
			}

			// 转换格式(树结构)
			List<Map<String, Object>> roleList = UtilDTO.toDTO(rolesList,
					(DTOCallbackHandler) null) ;
			List<Map<String, Object>> treeData = UtilTree.getTreeData(roleList, "resourceId", "parentId", "children", false);
			allApp.setRoleResources(treeData==null?Lists.newArrayList():treeData);
			// 选择应用资源的回显
			if(UtilCollection.isNotEmpty(allApp.getRoleResources())){
				allApp.setCheckFlag(true);
			}else {
				allApp.setCheckFlag(false);
			}
			// 只选择应用的回显
			for (SysRoleAppEntity app:sysRoleAppList ) {
				if(Long.toString(allApp.getAppId()).equals(Long.toString(app.getAppId()))){
					allApp.setCheckFlag(true);
				}
			}
		}
		return queryAllApp;
	}

	/**
	 * 描述: 查询这个角色下绑定的应用
	 * @param sysRoleResourceDto
	 * @return java.util.List<com.fosung.system.support.system.entity.sys.SysRoleResourceEntity>
	 * @author fuhao
	 * @date 2021/12/20 11:23
	 **/
	@Override
	public List<SysRoleResourceEntity>  queryBindRolePower(SysRoleResourceDto sysRoleResourceDto){
		// 查询这个角色下绑定的应用
		HashMap<String, Object> searchParam = Maps.newHashMap();
		searchParam.put("roleId",sysRoleResourceDto.getRoleId());
		List<SysRoleResourceEntity> sysRoleResourceList = entityDao.queryRolePower(searchParam);
		return sysRoleResourceList;
	}

	/**
	 * 描述: 获取角色绑定应用
	 * @param sysRoleResourceDto
	 * @return java.util.List<com.fosung.system.support.system.entity.sys.SysApplicationEntity>
	 * @author fuhao
	 * @date 2021/11/29 15:59
	 **/
	@Override
	public List<SysRoleAppEntity> queryRoleBindApp(SysRoleResourceDto sysRoleResourceDto) {
		// 查询这个角色下绑定的应用
		HashMap<String, Object> searchParam = Maps.newHashMap();
		searchParam.put("roleId",sysRoleResourceDto.getRoleId());
		List<SysRoleAppEntity> sysRoleResourceList = entityDao.queryRolePowerApp(searchParam);
		return sysRoleResourceList;
	}

	/**
	 * 查询角色下应用id
	 * @param appCode
	 * @param projectId
	 * @return
	 */
	public List<SysRoleAppEntity> queryRoleByApp(String appCode,Long projectId) {
		// 查询这个角色下绑定的应用
		HashMap<String, Object> searchParam = Maps.newHashMap();
		searchParam.put("appCode",appCode);
		searchParam.put("projectId",projectId);
		List<SysRoleAppEntity> sysRoleResourceList = entityDao.queryRolePowerApp(searchParam);
		return sysRoleResourceList;
	}

	/**
	 * 描述: 获取角色绑定资源
	 * @param sysRoleResourceDto
	 * @return java.util.List<com.fosung.system.support.system.entity.sys.SysResourceEntity>
	 * @author fuhao
	 * @date 2021/11/29 16:30
	 **/
	@Override
	public List<SysResourceEntity> queryRoleBindResource(SysRoleResourceDto sysRoleResourceDto) {
		// 查询这个角色下绑定的应用
//		List<SysRoleResourceEntity> sysRoleResourceList = queryRoleResource(sysRoleResourceDto);
		List<Map<String,Object>> lists = sysResourceAppService.getWorkBenchApp(sysRoleResourceDto.getAppId());
		if(UtilCollection.sizeIsEmpty(lists)){
			// 查询全部的资源
			HashMap<String, Object> searchParam = Maps.newHashMap();
			searchParam.put("appId",sysRoleResourceDto.getAppId());
			List<SysResourceEntity> allResourceList = sysResourceService.queryAll(searchParam);
			return allResourceList;
		}else {
			List<SysResourceEntity> allResourceList = Lists.newArrayList();
			for (Map<String, Object> list : lists) {
				SysResourceEntity sysResource = new SysResourceEntity();
				sysResource.setId(Long.valueOf(list.get("appId").toString()));
				sysResource.setMenuName(list.get("appName").toString());
				allResourceList.add(sysResource);
			}
			return allResourceList;
		}

	}

	/**
	 * 描述: 获取角色绑定的权限信息
	 * @param sysRoleResourceDto
	 * @return java.util.List<com.fosung.system.support.system.entity.sys.SysRoleResourceEntity>
	 * @author fuhao
	 * @date 2021/11/29 16:33
	 **/
	public List<SysRoleResourceEntity> queryRoleResource(SysRoleResourceDto sysRoleResourceDto){
		HashMap<String, Object> searchParam = Maps.newHashMap();
		searchParam.put("roleId",sysRoleResourceDto.getRoleId());
		List<SysRoleResourceEntity> sysRoleResourceList = entityDao.queryRolePower(searchParam);
		return sysRoleResourceList;
	}

	/**
	 *懒加载查询灯塔党组织
	 *
	 * @param parentId
	 * @author liuke
	 * @date 2022/2/9 10:30
	 * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
	 */
	public List<Map<String,Object>> queryDtRoleList(String parentId){
		try {
			if (UtilString.isEmpty(parentId)){
				return dtSourceService.queryDtRootOrg();
			}else {
				List<Map<String, Object>> maps = dtSourceService.queryDtSubOrg(parentId);
				for (int i = 0; i < maps.size(); i++) {
					Map<String, Object> dtOrg = maps.get(i);
					if(dtOrg.get("orgId").equals(parentId)){
						maps.remove(i);
					}
				}
				return maps;
			}
		}catch (Exception e){
          return null;
		}
	}

	/**
	 * 描述: 角色重新绑定应用
	 * @param sysRoleResourceDto
	 * @return void
	 * @author fuhao
	 * @date 2022/2/16 15:17
	 **/
	@Override
	public void rebindApp(SysRoleResourceDto sysRoleResourceDto) {
		if(!UtilCollection.sizeIsEmpty(sysRoleResourceDto.getBindScopes())){
			delBindApp(sysRoleResourceDto);
			// 转换类型
			List<SysRoleResourceEntity> roleResourceEntities = sysRoleResourceDto.getBindScopes();
			roleResourceEntities.forEach(roleResource ->{
				List<Long> resources = roleResource.getResources();
				if(roleResource.getCheckFlag()){
					if(!UtilCollection.isNotEmpty(resources)){
						roleResource.setProjectId(sysRoleResourceDto.getProjectId());
						roleResource.setRoleId(sysRoleResourceDto.getRoleId());
						bindApp(roleResource);
					}
				}

			});

		}
	}

	/**
	 * 描述: 绑定应用
	 * @param sysRoleResourceDto
	 * @return void
	 * @author fuhao
	 * @date 2022/2/16 15:26
	 **/
	@Override
	public void bindApp(SysRoleResourceEntity sysRoleResourceDto){
		if(sysRoleResourceDto.getCheckFlag()){
			SysRoleAppEntity sysRoleResource = new SysRoleAppEntity();
			sysRoleResource.setAppId(sysRoleResourceDto.getAppId());
			sysRoleResource.setRoleId(sysRoleResourceDto.getRoleId());
			sysRoleResource.setProjectId(sysRoleResourceDto.getProjectId());
			sysRoleAppService.save(sysRoleResource);
		}
	}

	/**
	 * 描述: 取消角色已绑定的应用
	 * @param sysRoleResourceDto
	 * @return void
	 * @author fuhao
	 * @date 2022/2/16 18:19
	 **/
	@Override
	public void delBindApp(SysRoleResourceDto sysRoleResourceDto){
		if(!UtilCollection.sizeIsEmpty(sysRoleResourceDto.getBindScopes())){
			List<SysRoleAppEntity> roleAppByRole = sysRoleAppService.getRoleAppByRole(sysRoleResourceDto.getRoleId());
			if(!UtilCollection.sizeIsEmpty(roleAppByRole)) {
				Set<Long> ids = UtilCollection.extractToSet(roleAppByRole, "id", Long.class);
				sysRoleAppService.delete(ids);
			}
		}
	}

	@Override
	public List<Map<String,Object>> queryRoleByAppIds(Long projectId, List<Long> ids){
		return entityDao.queryRoleByAppIds(projectId,ids);
	}

	@Override
	public void deleteByRoleId(Long roleId,String tableName) {
		entityDao.deleteByRoleId( roleId,tableName);
	}

	@Override
	public boolean checkHasSysUser(List<Long> roleIds){
		Map<String,Object> searchParam = Maps.newHashMap();
		searchParam.put("ids",roleIds);
		List<SysRoleEntity> sysRoleEntities = queryAll(searchParam);
		for (SysRoleEntity sysRoleEntity : sysRoleEntities) {
			if(sysRoleEntity.getRoleType().equals(RoleType.ADMIN)){
				return true;
			}
		}
		return false;
	}

}
