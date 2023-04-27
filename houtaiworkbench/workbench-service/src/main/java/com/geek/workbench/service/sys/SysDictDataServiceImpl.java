package com.geek.workbench.service.sys;

import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import com.geek.workbench.dao.sys.SysDictDataDao;
import com.geek.workbench.entity.sys.SysDictDataEntity;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class SysDictDataServiceImpl extends AppJPABaseDataServiceImpl<SysDictDataEntity, SysDictDataDao>
	implements SysDictDataService {

	private final static String SPLIT = "@_@";

	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("id","id:EQ");
				put("ids","id:IN");
				put("dictType","dictType:EQ");
				put("dictTypes","dictType:IN");
				put("projectId","projectId:EQ");
				put("dictLabel","dictLabel:EQ");
				put("dictValue","dictValue:EQ");
				put("dictValuesNotIn","dictValue:NOTIN");
				put("dictValues","dictValue:IN");
				put("status","status:EQ");

	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}

	private static LoadingCache<String, SysDictDataEntity> loadingCache;

	/**
	 * 初始化缓存器
	 */
	private void init() {
		loadingCache = CacheBuilder.newBuilder()
				// 缓存容器的初始容量
				.initialCapacity(1)
				// 缓存容器最大缓存容量
				.maximumSize(1000)
				// 设置并发级别、并发级别是指同时写缓存的线程数
				.concurrencyLevel(8)
				// 当缓存想在指定的时间段内没有被读或写就会被回收
				.expireAfterWrite(3, TimeUnit.HOURS)
				.build(new CacheLoader<String, SysDictDataEntity>() {
					/**
					 * 读取数据并缓存起来，null不会缓存
					 *
					 * @param sysDictData
					 * @return
					 * @throws Exception
					 */
					@Override
					public SysDictDataEntity load(String sysDictData) throws Exception {
						Map<String,Object> map = Maps.newHashMap();
						String[] arr = sysDictData.split(SPLIT);
						map.put("dictValue",arr[0]);
						map.put("dictType",arr[1]);
						map.put("projectId",Long.valueOf(arr[2]));
						List<SysDictDataEntity> sys = queryAll(map);
						if(UtilCollection.sizeIsEmpty(sys)){
							return new SysDictDataEntity();
						}else {
							return sys.get(0);
						}
					}
				});
	}

	/**
	 *
	 * 获取缓存
	 * @author liuke
	 * @date 2022/5/13 9:32
	 * @return com.google.common.cache.LoadingCache<com.fosung.system.support.system.entity.sys.SysDictDataEntity,com.fosung.system.support.system.entity.sys.SysDictDataEntity>
	 */
	private LoadingCache<String, SysDictDataEntity> getLoadingCache() {
		// 使用双重校验锁保证只有一个cache实例
		if (loadingCache == null) {
			synchronized (this) {
				if (loadingCache == null) {
					this.init();
				}
			}
		}
		return loadingCache;
	}

	/**
	 *获取字典对应名称
	 *
	 * @param dictValue
	 * @param dictType
	 * @param projectId
	 * @author liuke
	 * @date 2022/5/13 10:01
	 * @return java.lang.String
	 */
	@Override
	public String getItemName(String dictValue, String dictType,Long projectId) {
		LoadingCache<String, SysDictDataEntity> loadingCache = getLoadingCache();
		SysDictDataEntity sysDictData = new SysDictDataEntity();
		try {
			sysDictData = loadingCache.get(dictValue+SPLIT+dictType+SPLIT+projectId);
			if (sysDictData.getId()==null) {
				return null;
			}
			return sysDictData.getDictLabel();
		} catch (ExecutionException e) {
			log.error("获取缓存信息失败：", e);
			return null;
		}
	}

}
