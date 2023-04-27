package com.geek.system.support.system.service.sys;

import java.util.*;

import com.fosung.framework.common.util.UtilCollection;
import com.geek.system.support.system.dao.sys.SysUserRoleDao;
import com.geek.system.support.system.entity.sys.SysUserRoleEntity;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import org.springframework.util.Assert;

@Service
public class SysUserRoleServiceImpl extends AppJPABaseDataServiceImpl<SysUserRoleEntity, SysUserRoleDao>
	implements SysUserRoleService {
	
	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("userId","userId:EQ");
				put("roleId","roleId:EQ");
				put("appId","appId:EQ");
				put("orgId","orgId:EQ");
				put("orgCode","orgCode:EQ");
				put("appId","appId:EQ");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}

	@Override
	public void preSaveHandler(SysUserRoleEntity entity) {
		super.preSaveHandler(entity);
		HashMap<String,Object> searchParam = Maps.newHashMap();
		searchParam.put("userId",entity.getUserId());
		searchParam.put("roleId",entity.getRoleId());
		searchParam.put("projectId",entity.getProjectId());
		List<SysUserRoleEntity> sysUserRoleEntities = this.queryAll(searchParam);
		if(!UtilCollection.sizeIsEmpty(sysUserRoleEntities)){
			Set<Long> ids = UtilCollection.extractToSet(sysUserRoleEntities,"id",Long.class);
			this.delete(ids);
		}
	}

	/**
	 *
	 * 为用户重新绑定角色
	 * @param sysUserRoleEntities 
	 * @author liuke
	 * @date 2021/4/16 10:51
	 * @return void
	 */
	public void reBindRole(List<SysUserRoleEntity> sysUserRoleEntities){
		Set<Long> userIds=UtilCollection.extractToSet(sysUserRoleEntities,"userId",Long.class);
		userIds.forEach(userId->{
			this.deleteByUserId(userId);
		});
		this.saveBatch(sysUserRoleEntities);
	}

	/**
	 * 描述: 根据角色编码查询人员
	 *
	 * @param appId
	 * @param roleCode
	 * @createDate: 2021/11/1 19:20
	 * @author: gaojian
	 * @modify:
	 * @return: java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
	 */
	@Override
	public List<Map<String, Object>> queryUserByRoleCode(Long appId, String roleCode) {

		Assert.notNull(appId,"应用主键不能为空！");
		Assert.notNull(roleCode,"角色编码不能为空！");
		return entityDao.queryUserByRoleCode(appId,roleCode);
	}

	/**
	 *
	 * 根据角色Id删除关联用户
	 * @param roleId
	 * @author liuke
	 * @date 2021/4/8 14:04
	 * @return void
	 */
	public void deleteByRoleId(Long roleId){
		entityDao.deleteByRoleId(roleId);
	}
	/**
	 *根据用户id删除关联角色
	 *
	 * @param userId
	 * @author liuke
	 * @date 2021/4/8 14:04
	 * @return void
	 */
	public void deleteByUserId(Long userId){
		entityDao.deleteByUserId(userId);
	}
	

}
