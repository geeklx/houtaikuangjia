package com.fosung.system.support.system.service.sys;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.fosung.framework.common.json.JsonMapper;
import com.fosung.framework.common.util.UtilString;
import com.fosung.system.support.system.dao.sys.SysResourceAppDao;
import com.fosung.system.support.system.dict.AppType;
import com.fosung.system.support.system.dict.RedisConstant;
import com.fosung.system.support.system.entity.sys.SysApplicationEntity;
import com.fosung.system.support.system.entity.sys.SysResourceAppEntity;
import com.fosung.system.support.system.service.feign.WorkbenchAppService;
import com.google.common.collect.Lists;
import feign.Feign;
import feign.Target;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;

@Service
@Import(FeignClientsConfiguration.class)
public class SysResourceAppServiceImpl extends AppJPABaseDataServiceImpl<SysResourceAppEntity, SysResourceAppDao>
	implements SysResourceAppService {

	private WorkbenchAppService workbenchAppService;

	@Autowired
	private SysApplicationService sysApplicationService;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	
	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}

	/**
	 *初始化fegin执行器
	 *
	 * @param decoder
	 * @param encoder
	 * @author liuke
	 * @date 2022/3/9 14:51
	 * @return
	 */
	@Autowired
	public SysResourceAppServiceImpl(Decoder decoder, Encoder encoder) {
		workbenchAppService = Feign.builder()
				//.client(client)
				.encoder(encoder)
				.decoder(decoder)
				.target(Target.EmptyTarget.create(WorkbenchAppService.class));
	}


	/**
	 *
	 * 获取工作台应用
	 * @param appId
	 * @author liuke
	 * @date 2022/3/9 14:48
	 * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
	 */

	@Override
	public List<Map<String,Object>> getWorkBenchApp(Long appId){
		try {
			String redisKey = RedisConstant.SYSTEM_RESOURCE_APP_INFO+appId;
			String sysAppStr = stringRedisTemplate.opsForValue().get(redisKey);
			if(UtilString.isBlank(sysAppStr)){
				//根据应用id查询应用基本信息
				SysApplicationEntity sysApplicationEntity = sysApplicationService.get(appId);
				if(sysApplicationEntity== null){
					return Lists.newArrayList();
				}
				//如果不是app类型不执行
				if(sysApplicationEntity.getAppType().equals(AppType.h5)||sysApplicationEntity.getAppType().equals(AppType.pcClient)||sysApplicationEntity.getAppType().equals(AppType.pcWeb)){
					return Lists.newArrayList();
				}
				//判断是否配置第三方路径
				if(UtilString.isBlank(sysApplicationEntity.getResourceUrl())){
					return Lists.newArrayList();
				}
				URI uri = new URI(sysApplicationEntity.getResourceUrl());
				//URI uri = new URI("http://192.168.42.59:8081/api/terminal/application/config/query/app/params?packagename=com.fosung.lighthouse.dt2&terminaltype=android");
				Map<String,Object> map =  workbenchAppService.getWorkbenchApp(uri);
				if((Boolean) map.get("success")){
					List<Map<String,Object>> lists = (List<Map<String,Object>>)map.get("datalist");
					for (Map<String, Object> list : lists) {
						list.remove("appId");
						list.put("appId",list.get("id"));
					}
					stringRedisTemplate.opsForValue().set(redisKey,JsonMapper.toJSONString(lists),RedisConstant.EXPIRES_MINUTES, TimeUnit.MINUTES);
					return lists;
				}else {
					return Lists.newArrayList();
				}
			}else {
				return (List<Map<String,Object>>)JsonMapper.parse(sysAppStr);
            }


		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
