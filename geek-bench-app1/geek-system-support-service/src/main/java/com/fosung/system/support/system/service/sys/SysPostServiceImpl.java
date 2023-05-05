package com.fosung.system.support.system.service.sys;

import java.util.*;

import com.fosung.framework.common.util.UtilCollection;
import com.fosung.system.support.system.dao.sys.SysPostDao;
import com.fosung.system.support.system.entity.sys.SysPostEntity;
import com.fosung.system.support.system.entity.sys.SysUserPostEntity;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;

@Service
public class SysPostServiceImpl extends AppJPABaseDataServiceImpl<SysPostEntity, SysPostDao>
	implements SysPostService {

	@Autowired
	private SysUserPostService sysUserPostService;
	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("postName","postName:EQ");
				put("id","id:EQ");
				put("ids","id:IN");
				put("postCode","postCode:EQ");
				put("status","status:EQ");
				put("projectId","projectId:EQ");
				put("del","del:EQ");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}


	/**
	 *
	 * 绑定职位
	 * @param userId
	 * @param postIds
	 * @author liuke
	 * @date 2021/4/8 14:56
	 * @return void
	 */
	public void bindPost(Long userId, Set<Long> postIds,Long projectId){
		if(!UtilCollection.sizeIsEmpty(postIds)){
			List<SysUserPostEntity> list = new ArrayList<SysUserPostEntity>();
			postIds.forEach(postId ->{
				list.add(new SysUserPostEntity(userId,postId,projectId));
			});
			sysUserPostService.saveBatch(list);
		}
	}

	/**
	 *删除原有职位，重新绑定职位
	 *
	 * @param userId
	 * @param postIds
	 * @param appId
	 * @author liuke
	 * @date 2021/4/8 14:58
	 * @return void
	 */
	public void rebindPost(Long userId, Set<Long> postIds,Long appId){
		sysUserPostService.deleteByUserId(userId,appId);
		this.bindPost(userId,postIds,appId);
	}


	/**
	 *根据用户得到岗位
	 *
	 * @param userId
	 * @author liuke
	 * @date 2021/4/19 16:09
	 * @return java.util.List<SysPostEntity>
	 */
	public List<SysPostEntity> getPostByUser(Long userId){
		Map<String,Object> searchParam = Maps.newHashMap();
		searchParam.put("userId",userId);
		List<SysUserPostEntity> sysUserPostEntityList=sysUserPostService.queryAll(searchParam);
		if(!UtilCollection.sizeIsEmpty(sysUserPostEntityList)){
			Set<Long> postIds = UtilCollection.extractToSet(sysUserPostEntityList,"postId",Long.class);
			Map<String,Object> postParam = Maps.newHashMap();
			postParam.put("ids",postIds);
			return this.queryAll(postParam);
		}
		return null;
	}

	/**
	 * 描述: 岗位分页查询
	 * @param searchParam
	 * @param pageable
	 * @return org.springframework.data.domain.Page<com.fosung.system.support.system.entity.sys.SysPostEntity>
	 * @author fuhao
	 * @date 2022/2/8 14:32
	 **/
	@Override
	public Page<SysPostEntity> queryPage(Map<String, Object> searchParam, Pageable pageable) {
		return entityDao.queryPage(searchParam,pageable);
	}

}
