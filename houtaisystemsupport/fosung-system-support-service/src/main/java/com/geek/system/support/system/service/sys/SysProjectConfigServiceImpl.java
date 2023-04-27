package com.geek.system.support.system.service.sys;


import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import com.geek.system.support.system.dao.sys.SysProjectConfigDao;
import com.geek.system.support.system.entity.sys.SysProjectConfigEntity;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SysProjectConfigServiceImpl extends AppJPABaseDataServiceImpl<SysProjectConfigEntity, SysProjectConfigDao>
	implements SysProjectConfigService {




	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("id","id:EQ");
				put("projectId","projectId:EQ");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}

	/**
	 *查询项目配置
	 *
	 * @param projectId
	 * @author liuke
	 * @date 2022/5/5 16:36
	 * @return com.fosung.system.support.system.entity.sys.SysProjectConfigEntity
	 */
	public SysProjectConfigEntity getByProjectId(Long projectId){
		Map<String,Object> map = Maps.newHashMap();
		map.put("projectId",projectId);
		List<SysProjectConfigEntity> sysProjectConfigEntities = this.queryAll(map);
		if(UtilCollection.sizeIsEmpty(sysProjectConfigEntities)){
			return new SysProjectConfigEntity();
		}else {
             return sysProjectConfigEntities.get(0);
        }
	}

}
