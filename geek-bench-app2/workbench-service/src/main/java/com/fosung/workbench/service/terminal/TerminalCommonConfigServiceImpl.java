package com.fosung.workbench.service.terminal;

import com.alibaba.fastjson.JSONArray;
import com.fosung.framework.common.json.JsonMapper;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.system.pbs.api.SystemSysApi;
import com.fosung.workbench.config.TerminalContent;
import com.fosung.workbench.dao.terminal.TerminalConfigCommonDao;
import com.fosung.workbench.entity.terminal.TerminalConfigCommonEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class TerminalCommonConfigServiceImpl extends AppJPABaseDataServiceImpl<TerminalConfigCommonEntity, TerminalConfigCommonDao>
	implements TerminalConfigCommonService {

	@Autowired
	private SystemSysApi systemSysApi;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("terminalId","terminalId:EQ");
				put("configType","configType:EQ");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}


	@Override
	public List<TerminalConfigCommonEntity> getAllCommonConfigurationInfo(TerminalConfigCommonEntity TerminalConfigCommonEntity) {
		return entityDao.getAllCommonConfigurationInfo(TerminalConfigCommonEntity);
	}

	@Override
	public void updateConfigValue(TerminalConfigCommonEntity TerminalConfigCommonEntity) {
		entityDao.updateConfigValue(TerminalConfigCommonEntity);
	}


	/**
	 * 在缓存取出认证配置
	 * @param id
	 * @author liuke
	 * @date 2021/10/18 10:05
	 * @return java.util.List<com.fosung.workbench.entity.terminal.TerminalApplicationConfigEntity>
	 */
	@Override
	public List<TerminalConfigCommonEntity> getTerminalConfigCommonByTerminalId(Long id){
		TerminalConfigCommonEntity terminalConfigCommonEntity = new TerminalConfigCommonEntity();
		terminalConfigCommonEntity.setTerminalId(id);
		return getAllCommonConfigurationInfo(terminalConfigCommonEntity);
	}

	/**
	 *	获取基础配置信息
	 * TODO 后续将置灰设置改为删除置灰配置信息，不保留空字符串
	 * @return java.util.List<java.lang.String>
	 * @author fuhao
	 * @date 2021/10/25 16:47
	 */
	public Set<String> queryCommonConfigDict(String mapKey){
		//
		Set<String> result = new HashSet<>();
		// 获取redis并判断是否存在，不存在则查询数据库
		String resultDict = stringRedisTemplate.opsForValue().get(TerminalContent.REDIS_TERMINAL_COMMOM_CONFIG_KEY);
		if(StringUtils.isNotBlank(resultDict)){
			List<Map> redisResult = JsonMapper.parseArray(resultDict, Map.class);
			for (int i = 0; i < redisResult.size(); i++) {
				Map redisMap = redisResult.get(i);
				Set<String> redisSet = redisMap.keySet();
				for (String redisKey:redisSet ) {
					if(redisKey.contains(mapKey)){
						// 将type放到返回集合
						result.add(mapKey);
						JSONArray jsonArray = (JSONArray) redisMap.get(redisKey);
						for (int j = 0; j < jsonArray.size(); j++) {
							// 将volue值放到返回集合
							Map map = (Map) jsonArray.get(j);
							String dictValue = (String) map.get("dictValue");
							result.add(dictValue);
						}
					}
				}
			}
			return result;
		}
		// 查询数据库并重新插入redis
		ResponseParam responseParam = systemSysApi.querySysTemDict("");
		ArrayList datalist = (ArrayList) responseParam.get("datalist");
		// 存入redis的数据集
		ArrayList<Map> dbResult = new ArrayList<>();
		if(datalist!=null && datalist.size()>0){
			HashMap collect = (HashMap) datalist.stream().collect(Collectors.groupingBy((LinkedHashMap m) -> (String) m.get("dictType")));
			Set<String> dbKeys = collect.keySet();
			for (String dbKey:dbKeys) {
				if(dbKey.contains("ConfigDict")){
					HashMap<String, List> hashMap = new HashMap<>();
					ArrayList keyList = (ArrayList) collect.get(dbKey);
					hashMap.put(dbKey,keyList);
					dbResult.add(hashMap);
				}
			}
		}
		// 将数据集合统一存储到redis中
		stringRedisTemplate.opsForValue().set(TerminalContent.REDIS_TERMINAL_COMMOM_CONFIG_KEY,JsonMapper.toJSONString(dbResult),TerminalContent.EXPIRES_HOURS, TimeUnit.DAYS);
		// 数据库返回验证集合
		if(dbResult != null && dbResult.size() > 0){
			for (int i = 0; i < dbResult.size(); i++) {
				Map dbMap = dbResult.get(i);
				Set<String> dbSet = dbMap.keySet();
				for (String dbKey:dbSet ) {
					if(dbKey.contains(mapKey)){
						// 将type放到返回集合
						result.add(mapKey);
						ArrayList dbList = (ArrayList) dbMap.get(dbKey);
						for (int j = 0; j < dbList.size(); j++) {
							// 将volue值放到返回集合
							LinkedHashMap dbResultMap = (LinkedHashMap) dbList.get(j);
							result.add((String) dbResultMap.get("dictValue"));
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * 保存终端管理基础配置信息
	 * @param params
	 * @return ResponseParam
	 * @author fuhao
	 * @date 2021/10/25 16:47
	 */
	@Override
	@Transactional
	public ResponseParam saveCommonConfiguration(Map<String, Object> params) {
		if(params.get(TerminalContent.TERMINAL_ID) == null){
			return ResponseParam.fail().message(TerminalContent.CHECK_TERMINAL_ID);
		}
		// 保存修改
		Long terminalId = Long.valueOf(params.get(TerminalContent.TERMINAL_ID).toString());
		Set<String> configurationTypes = params.keySet();
		configurationTypes.remove(TerminalContent.TERMINAL_ID);
		for (String configurationType : configurationTypes){
			Map configurationTypeObject = (Map) params.get(configurationType);
			Set<String> configurationKeys = configurationTypeObject.keySet();
			// 查询redis缓存中存在的值
			Set<String> commonConfigDictList = queryCommonConfigDict(configurationType);
			for (String configurationKey : configurationKeys){
				// 判断参数是否是合法参数，不是则跳过
				if(!commonConfigDictList.contains(configurationKey) || !commonConfigDictList.contains(configurationType) ){
					continue;
				}

				TerminalConfigCommonEntity terminalCommonConfigurationInfo = new TerminalConfigCommonEntity();
				terminalCommonConfigurationInfo.setConfigType(configurationType);
				terminalCommonConfigurationInfo.setConfigCode(configurationKey);
				String configValue = (String) configurationTypeObject.get(configurationKey);
				terminalCommonConfigurationInfo.setConfigValue(configValue);
				terminalCommonConfigurationInfo.setTerminalId(terminalId);
				List<TerminalConfigCommonEntity> allCommonConfigurationInfo
						= this.getAllCommonConfigurationInfo(terminalCommonConfigurationInfo);
				if(allCommonConfigurationInfo != null && allCommonConfigurationInfo.size() > 0){
					this.updateConfigValue(terminalCommonConfigurationInfo);
				}else {
					this.save(terminalCommonConfigurationInfo);
				}
			}
		}
		return ResponseParam.updateSuccess();
	}

}
