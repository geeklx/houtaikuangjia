package com.fosung.system.support.system.service.sys;

import java.util.*;

import com.fosung.framework.common.util.UtilCollection;
import com.fosung.system.support.system.dao.sys.SysUserPostDao;
import com.fosung.system.support.system.entity.sys.SysUserPostEntity;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;

@Service
public class SysUserPostServiceImpl extends AppJPABaseDataServiceImpl<SysUserPostEntity, SysUserPostDao>
	implements SysUserPostService {
	
	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("userId","userId:EQ");
				put("postId","postId:EQ");
				put("postIds","postId:IN");
				put("projectId","projectId:EQ");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}

	/**
	 * 保存之前的操作
	 * @param entity
	 */
	@Override
	public void preSaveHandler(SysUserPostEntity entity) {
		super.preSaveHandler(entity);
		HashMap<String,Object> searchParam = Maps.newHashMap();
		searchParam.put("userId",entity.getUserId());
		searchParam.put("postId",entity.getPostId());
		searchParam.put("projectId",entity.getProjectId());
		List<SysUserPostEntity> sysUserPostEntityList = this.queryAll(searchParam);
		if(!UtilCollection.sizeIsEmpty(sysUserPostEntityList)){
			Set<Long> ids = UtilCollection.extractToSet(sysUserPostEntityList,"id",Long.class);
			this.delete(ids);
		}
	}
	/**
	 *根据职位id删除所有关联
	 *
	 * @param roleId
	 * @param appId
	 * @author liuke
	 * @date 2021/4/8 14:47
	 * @return void
	 */
	@Override
	public void deleteByPostId(Long roleId, Long appId) {
		entityDao.deleteByPostId(roleId,appId);
	}

	/**
	 *删除用户所有职位
	 *
	 * @param userId
	 * @param appId
	 * @author liuke
	 * @date 2021/4/8 14:48
	 * @return void
	 */
	@Override
	public void deleteByUserId(Long userId, Long appId) {
		entityDao.deleteByUserId(userId,appId);
	}
}
