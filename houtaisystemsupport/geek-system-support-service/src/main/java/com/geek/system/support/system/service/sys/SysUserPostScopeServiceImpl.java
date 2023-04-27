package com.geek.system.support.system.service.sys;


import java.util.*;

import com.geek.system.support.system.dao.sys.SysUserPostScopeDao;
import com.geek.system.support.system.entity.sys.SysPostEntity;
import com.geek.system.support.system.entity.sys.SysUserPostScopeEntity;
import com.geek.system.support.system.service.mq.SysMQProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
@Service
public class SysUserPostScopeServiceImpl extends AppJPABaseDataServiceImpl<SysUserPostScopeEntity, SysUserPostScopeDao>
	implements SysUserPostScopeService {

	@Autowired
	private SysUserPostScopeService sysUserPostScopeService;
	@Autowired
	private SysMQProducerService sysMQProducerService;
	@Autowired
	private SysUserService sysUserService;
	
	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("userId","userId:EQ");
				put("userIds","userId:IN");
				put("postId","postId:EQ");
				put("postIds","postId:IN");
				put("projectId","projectId:EQ");
				put("postCode","postCode:EQ");
				put("orgId","orgId:EQ");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}

	/**
	 * 根据用户id获取职务
	 * @param userParams
	 * @return
	 */
	@Override
	public List<SysPostEntity> queryPostByUserIds(HashMap<String, Object> userParams) {
		return entityDao.queryPostByUserIds(userParams);
	}

	/**
	 * 描述: 是否是首次任职并发送mq
	 * @param userId
	 * @return void
	 * @author fuhao
	 * @date 2022/3/1 11:39
	 **/
	@Override
	public void isFirstAddPost(Long userId,Long orgId,String type) {
//		// 判断是否是首次任职
//		HashMap<String, Object> userPostMap = Maps.newHashMap();
//		userPostMap.put("userId",userId);
//		userPostMap.put("orgId",orgId);
//		List<SysUserPostScopeEntity> sysUserPostScopeEntities = sysUserPostScopeService.queryAll(userPostMap);
//		// 获取人员信息
//		SysUserEntity sysUserEntity = sysUserService.get(userId);
//		for (SysUserPostScopeEntity userPostScopeEntity:sysUserPostScopeEntities) {
//			sysMQProducerService.sendUserPostMessage(sysUserEntity,userPostScopeEntity,type);
//		}

	}

	/**
	 * 描述: 是否是最后删除任职，并发送mq
	 * @param userId
	 * @param orgId
	 * @return void
	 * @author fuhao
	 * @date 2022/3/1 15:12
	 **/
	@Override
	public void isLastDelPost(Long userId, Long orgId) {
//		// 判断是否最后删除
//		HashMap<String, Object> userPostMap = Maps.newHashMap();
//		userPostMap.put("userId",userId);
//		userPostMap.put("orgId",orgId);
//		List<SysUserPostScopeEntity> sysUserPostScopeEntities = sysUserPostScopeService.queryAll(userPostMap);
//		if(UtilCollection.sizeIsEmpty(sysUserPostScopeEntities)){
//
//			sysMQProducerService.sendUserPostMessage(userId,"delComPos",orgId);
//		}else {
//			sysMQProducerService.sendUserPostMessage(userId,"delPos",orgId);
//		}
	}

//	@Override
//	public void preSaveHandler(SysUserPostScopeEntity entity) {
//		super.preSaveHandler(entity);
//		HashMap<String,Object> searchParam = Maps.newHashMap();
//		searchParam.put("userId",entity.getUserId());
//		searchParam.put("projectId",entity.getProjectId());
//		List<SysUserPostScopeEntity> sysUserPostEntityList = this.queryAll(searchParam);
//		if(!UtilCollection.sizeIsEmpty(sysUserPostEntityList)){
//			Set<Long> ids = UtilCollection.extractToSet(sysUserPostEntityList,"id",Long.class);
//			this.delete(ids);
//		}
//	}

}