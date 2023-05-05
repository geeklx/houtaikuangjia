package com.fosung.workbench.service.config;

import java.util.*;
import java.util.concurrent.ExecutionException;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.exception.AppException;
import com.fosung.workbench.common.MessageContent;
import com.fosung.workbench.dao.config.TerminalConfigApiDao;
import com.fosung.workbench.entity.config.TerminalConfigApiEntity;
import com.fosung.workbench.util.CacheUtil;
import com.google.api.client.util.Lists;
import com.google.api.client.util.Maps;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;

/**
 * 描述:  终端接口配置服务实现类
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Service
public class TerminalConfigApiServiceImpl extends AppJPABaseDataServiceImpl<TerminalConfigApiEntity, TerminalConfigApiDao>
	implements TerminalConfigApiService {

	/**
	 * 描述:  注入终端服务
	 * @createDate: 2021/10/25 11:15
	 * @author: gaojian
	 */

	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("terminalId","terminalId:EQ");
				put("bindCategory","bindCategory:EQ");
				put("bindType","bindType:EQ");
				put("bindGroupId","bindGroupId:EQ");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}


	/**
	 * 描述:  保存授权信息
	 *
	 * @param list
	 * @createDate: 2021/10/24 20:47
	 * @author: gaojian
	 * @modify:
	 * @return: void
	 */
	@Override
	public void saveInfo(List<TerminalConfigApiEntity> list) {

		// 1. 非空判断
		if(list == null || list.isEmpty()){
			throw new AppException(MessageContent.PARAMS_IS_NULL);
		}

		// 2. 删除以前的授权
		entityDao.deleteAllByBindGroupId(list.get(0).getBindGroupId());

		// 3. 循环插入数据
		list.forEach(terminalConfigApiEntity -> {

			Map<String,Object> searchParams = UtilDTO.toDTO(terminalConfigApiEntity, Arrays.asList("terminalId","bindCategory","bindType"));
			List<TerminalConfigApiEntity> result = queryAll(searchParams);
			if( result != null && result.size() > 0){
				throw new AppException(MessageContent.TERMINAL_API_IS_EXIST);
			}

			//  保存授权信息
			save(terminalConfigApiEntity);
		});

	}

	/**
	 * 描述:  查询应用信息
	 *
	 * @param terminalConfigApiEntity
	 * @createDate: 2021/10/25 11:08
	 * @author: gaojian
	 * @modify:
	 * @return: java.util.Map<java.lang.String, java.lang.Object>
	 */
	@Override
	public Map<String, Object> queryInfo(TerminalConfigApiEntity terminalConfigApiEntity) {

		// 1. 查询已授权终端
		List<Map<String,Object>> checked = entityDao.queryBindTerminal(terminalConfigApiEntity.getBindGroupId());

		// 2. 查询未授权终端
		Map<String,Object> searchParams = UtilDTO.toDTO(terminalConfigApiEntity,Arrays.asList("bindCategory","bindType"));
		List<Map<String,Object>> uncheck = entityDao.queryUnBindTerminal(searchParams);

		// 3. 组装返回值
		Map<String,Object> result = new HashMap<>(8);
		result.put("checked",checked);
		result.put("uncheck",uncheck);
		return result;
	}

	/**
	 * 描述:  终端应用配置缓存
	 * @createDate: 2021/10/15 14:56
	 * @author: liuke
	 * @modify:
	 * @param null
	 * @return:
	 */
	public LoadingCache<Long,List<TerminalConfigApiEntity>> terminalConfigApiCache = CacheUtil.getInstance().build(
			new CacheLoader<Long,List<TerminalConfigApiEntity>>() {
				@Override
				public List<TerminalConfigApiEntity> load(Long key) throws Exception {
					Map<String,Object> searchParam = Maps.newHashMap();
					searchParam.put("terminalId",key);
					return queryAll(searchParam);
				}
			}
	);


	/**
	 *
	 * 根据终端获取应用
	 * @param terminalId
	 * @author liuke
	 * @date 2021/10/18 10:05
	 * @return java.util.List<com.fosung.workbench.entity.terminal.TerminalApplicationConfigEntity>
	 */
	@Override
	public List<TerminalConfigApiEntity> getCacheTerminalApiByTerminal(Long terminalId){
		List<TerminalConfigApiEntity> entities = Lists.newArrayList();
		try {
			entities = terminalConfigApiCache.get(terminalId);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return entities;
	}
}
