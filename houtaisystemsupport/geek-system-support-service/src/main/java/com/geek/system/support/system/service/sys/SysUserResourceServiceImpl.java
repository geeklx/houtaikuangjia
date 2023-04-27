package com.geek.system.support.system.service.sys;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fosung.framework.common.util.UtilBean;
import com.fosung.framework.common.util.UtilCollection;
import com.geek.system.support.system.dao.sys.SysUserResourceDao;
import com.geek.system.support.system.entity.sys.SysUserResourceEntity;
import org.springframework.stereotype.Service;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;

@Service
public class SysUserResourceServiceImpl extends AppJPABaseDataServiceImpl<SysUserResourceEntity, SysUserResourceDao>
	implements SysUserResourceService {
	
	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("userId","userId:EQ");
				put("roleId","roleId:EQ");
				put("roleIdIsNull","roleId:ISNULL");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}

	/**
	 *先删除后保存用户自定义资源
	 *
	 * @param sysUserResourceEntities
	 * @author liuke
	 * @date 2021/12/3 16:13
	 * @return void
	 */
	@Override
	public void saveAfterDelete(List<SysUserResourceEntity> sysUserResourceEntities){
		delUserResource(sysUserResourceEntities);
		List<SysUserResourceEntity> lists = UtilBean.copyBeans(sysUserResourceEntities, SysUserResourceEntity.class);
		saveBatch(lists);
	}

	/**
	 * 描述:
	 * @param sysUserResourceEntities
	 * @return void
	 * @author fuhao
	 * @date 2022/2/17 11:39
	 **/
	@Override
	public void delUserResource(List<SysUserResourceEntity> sysUserResourceEntities) {
		if(UtilCollection.sizeIsEmpty(sysUserResourceEntities)){
			return;
		}
		Set<Long> roleIds = UtilBean.copyBeans(sysUserResourceEntities,SysUserResourceEntity.class).stream().map(map -> map.getRoleId()).collect(Collectors.toSet());
		for (Long roleId : roleIds) {
			entityDao.deleteByUserIdAndRole(sysUserResourceEntities.get(0).getUserId(),roleId);
		}
	}


}
