package com.geek.workbench.service.config;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.exception.AppException;
import com.fosung.framework.common.util.UtilBean;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.dao.config.mybatis.page.MybatisPage;
import com.fosung.framework.dao.config.mybatis.page.MybatisPageRequest;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import com.geek.workbench.common.AppMessageContent;
import com.geek.workbench.common.ConfigApiVariableKey;
import com.geek.workbench.common.MessageContent;
import com.geek.workbench.dao.config.ConfigApiDao;
import com.geek.workbench.dto.config.ConfigApiDto;
import com.geek.workbench.dto.config.TerminalConfigApiDto;
import com.geek.workbench.entity.config.ConfigApiEntity;
import com.geek.workbench.entity.config.ConfigApiGroupEntity;
import com.geek.workbench.util.CacheUtil;
import com.google.api.client.util.Lists;
import com.google.api.client.util.Maps;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * 描述:  接口配置服务实现类
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Service
public class ConfigApiServiceImpl extends AppJPABaseDataServiceImpl<ConfigApiEntity, ConfigApiDao>
	implements ConfigApiService {

	/**
	 * 描述:  注入接口组服务
	 * @createDate: 2021/10/24 10:45
	 * @author: gaojian
	 */
	@Autowired
	private ConfigApiGroupService configApiGroupService;


	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("id","id:EQ");
				put("apiName","apiName:LIKE");
				put("apiType","apiType:EQ");
				put("apiGroupId","apiGroupId:EQ");
				put("apiCategory","apiCategory:EQ");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}


	/**
	 * 描述:  保存接口信息
	 *
	 * @param configApiDto
	 * @createDate: 2021/10/24 10:20
	 * @author: gaojian
	 * @modify:
	 * @return: void
	 */
	@Override
	public void saveApiInfo(ConfigApiDto configApiDto) {

		// 1. 非空判断
		if(configApiDto != null && configApiDto.getApiList().isEmpty()){
			throw new AppException(MessageContent.PARAMS_IS_NULL);
		}

		// 2. 保存接口所属组信息
		ConfigApiGroupEntity configApiGroupEntity = new ConfigApiGroupEntity();
		if(StringUtils.isNotBlank(configApiDto.getName())){
			configApiGroupEntity.setApiGroupName(configApiDto.getName());
		}
		configApiGroupEntity.setApiCategory(configApiDto.getApiCategory());
		configApiGroupEntity.setApiType(configApiDto.getApiType());
		if(StringUtils.isNotBlank(configApiDto.getRemark())){
			configApiGroupEntity.setRemark(configApiDto.getRemark());
		}
		configApiGroupService.save(configApiGroupEntity);

		// 3. 保存接口信息
		configApiDto.getApiList().forEach( configApiEntity -> {
			configApiEntity.setApiGroupId(configApiGroupEntity.getId());
		});
		saveBatch(configApiDto.getApiList());
	}

	/**
	 * 描述:  修改接口信息
	 *
	 * @param configApiDto
	 * @createDate: 2021/10/24 15:40
	 * @author: gaojian
	 * @modify:
	 * @return: void
	 */
	@Override
	public void updateApiInfo(ConfigApiDto configApiDto) {

		// 1. 修改组名称
		ConfigApiGroupEntity configApiGroupEntity = new ConfigApiGroupEntity();
		configApiGroupEntity.setId(configApiDto.getApiGroupId());
		configApiGroupEntity.setApiGroupName(configApiDto.getName());
		configApiGroupEntity.setRemark(configApiDto.getRemark());
		configApiGroupService.update(configApiGroupEntity,Arrays.asList("apiGroupName","remark"));

		// 2. 修改接口信息
		configApiDto.getApiList().forEach( configApiEntity -> {

			// 2.1. 获取信息
			ConfigApiEntity result = entityDao.getFirstByApiGroupIdAndApiCode(configApiDto.getApiGroupId(),configApiEntity.getApiCode());

			// 2.2. 复制修改内容
			List<String> ignorePropertiesList = Arrays.asList("id","apiGroupId","apiCode");
			String[] ignoreProperties = new String[ignorePropertiesList.size()];
			UtilBean.copyProperties(configApiEntity,result,ignorePropertiesList.toArray(ignoreProperties));

			// 2.3. 执行修改
			Set<String> updateFields = UtilDTO.toDTOExcludeFields(result, Arrays.asList("id","apiType","apiCode")).keySet();
			update(result,updateFields);
		});
	}

	/**
	 * 描述:  查询信息
	 *
	 * @param configApiDto
	 * @createDate: 2021/10/24 11:49
	 * @author: gaojian
	 * @modify:
	 * @return: org.springframework.data.domain.Page<java.util.Map < java.lang.String, java.lang.Object>>
	 */
	@Override
	public Page<Map<String, Object>> queryInfo(ConfigApiDto configApiDto) {

		// 1. 非空判断并获取本类型下的接口
		Assert.notNull(configApiDto.getApiCategory(),MessageContent.API_CATEGORY_IS_NULL);
		Pageable pageable = MybatisPageRequest.of(configApiDto.getPageNum(),configApiDto.getPageSize());
		MybatisPage<Map<String,Object>> pageInfo = entityDao.queryGroupInfoAllByApiCategory(configApiDto.getApiCategory().name(),pageable);

		// 2. 循环获取每个分组的信息
		pageInfo.forEach( map ->{

			if(map.containsKey(ConfigApiVariableKey.API_GROUP_ID) && map.get(ConfigApiVariableKey.API_GROUP_ID) != null ){
				Map<String,Object> searchParams = new HashMap<>(8);
				searchParams.put(ConfigApiVariableKey.API_GROUP_ID,map.get(ConfigApiVariableKey.API_GROUP_ID));
				List<ConfigApiEntity> list = queryAll(searchParams);
				mergeInfo(map,list);
			}
		});
		return pageInfo;
	}

	/**
	 * 描述:  接口删除
	 *
	 * @param list
	 * @createDate: 2021/10/24 14:49
	 * @author: gaojian
	 * @modify:
	 * @return: void
	 */
	@Override
	public void deleteInfo(List<ConfigApiDto> list) {

		// 1. 非空判断
		if (UtilCollection.isEmpty(list)) {
			throw new AppException(AppMessageContent.PARAMS_IS_NULL);
		}

		list.forEach( configApiDto -> {

			// 1. 删除组信息
			configApiGroupService.delete(configApiDto.getApiGroupId());

			// 2. 删除接口信息
			entityDao.updateAllByApiGroupId(configApiDto.getApiGroupId());

			// 3. 删除终端绑定的接口
			entityDao.deleteApiBindInfo(configApiDto.getApiGroupId());
		});
	}

	/**
	 * 描述:  获取接口配置信息
	 *
	 * @param configApiDto
	 * @createDate: 2021/10/24 14:57
	 * @author: gaojian
	 * @modify:
	 * @return: java.util.Map<java.lang.String, java.lang.Object>
	 */
	@Override
	public Map<String, Object> getInfo(ConfigApiDto configApiDto) {

		// 1. 非空判断
		Assert.notNull(configApiDto.getApiGroupId(),MessageContent.API_GROUP_ID_IS_NULL);

		// 2. 获取接口组信息
		ConfigApiGroupEntity configApiGroupEntity = configApiGroupService.get(configApiDto.getApiGroupId());

		// 3. 获取接口所属组接口信息
		Map<String,Object> map = new HashMap<>(8);
		map.put(ConfigApiVariableKey.API_GROUP_ID,configApiDto.getApiGroupId());
		List<ConfigApiEntity> list = queryAll(map);

		// 4. 添加参数
		map.put(ConfigApiVariableKey.NAME,configApiGroupEntity.getApiGroupName());
		map.put(ConfigApiVariableKey.API_CATEGORY,configApiGroupEntity.getApiCategory());
		map.put(ConfigApiVariableKey.API_TYPE,configApiGroupEntity.getApiType());
		map.put(ConfigApiVariableKey.API_REMARK,configApiGroupEntity.getRemark());
		mergeInfo(map,list);
		return map;
	}

	/**
	 * 描述:  合并接口信息
	 * @createDate: 2021/10/24 12:16
	 * @author: gaojian
	 * @modify:
	 * @param result
	 * @param list
	 * @return: void
	 */
	public void mergeInfo(Map<String,Object> result,List<ConfigApiEntity> list){

		list.forEach( configApiEntity -> {

			// 接口名称
			result.put(configApiEntity.getApiCode(),configApiEntity.getApiHost() + configApiEntity.getApiAddress());
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = null;
			if( configApiEntity.getCreateDatetime() != null ){
				dateString = formatter.format(configApiEntity.getCreateDatetime());
			}
			result.put(ConfigApiVariableKey.API_CREATE_TIME,dateString);
			if( !result.containsKey(ConfigApiVariableKey.API_REMARK)) {
				result.put(ConfigApiVariableKey.API_REMARK, configApiEntity.getRemark());
			}
		});
	}

	/**
	 *终端应用配置缓存
	 *
	 * @author liuke
	 * @date 2021/10/15 14:56
	 * @return
	 */
	public LoadingCache<Long,List<ConfigApiEntity>> configApiCache = CacheUtil.getInstance().build(
			new CacheLoader<Long,List<ConfigApiEntity>>() {
				@Override
				public List<ConfigApiEntity> load(Long key) throws Exception {
					Map<String,Object> searchParam = Maps.newHashMap();
					searchParam.put("id",key);
					return queryAll(searchParam,new String[]{"num"});
				}
			}
	);


	/**
	 *
	 * 根据终端获取应用
	 * @param terminalId
	 * @author liuke
	 * @date 2021/10/18 10:05
	 * @return java.util.List<com.geek.workbench.entity.terminal.TerminalApplicationConfigEntity>
	 */
	@Override
	public List<ConfigApiEntity> configApiCache(Long terminalId){
		List<ConfigApiEntity> entities = Lists.newArrayList();
		try {
			entities = configApiCache.get(terminalId);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return entities;
	}
	@Override
	public List<ConfigApiEntity> queryTree(TerminalConfigApiDto configApiDto){
		return entityDao.queryTree(configApiDto);
	}
}
