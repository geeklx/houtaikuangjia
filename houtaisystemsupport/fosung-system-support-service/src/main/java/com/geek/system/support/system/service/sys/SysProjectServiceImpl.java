package com.geek.system.support.system.service.sys;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.id.IdGenerators;
import com.fosung.framework.common.json.JsonMapper;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.common.util.UtilString;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import com.geek.system.support.system.dao.sys.SysProjectDao;
import com.geek.system.support.system.dict.AppStatus;
import com.geek.system.support.system.dict.DictStatus;
import com.geek.system.support.system.dict.RedisConstant;
import com.geek.system.support.system.entity.sys.*;
import com.geek.system.support.system.entity.sys.*;
import com.geek.system.support.system.util.Constant;
import com.geek.system.support.system.util.ProjectConstant;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class SysProjectServiceImpl extends AppJPABaseDataServiceImpl<SysProjectEntity, SysProjectDao>
	implements SysProjectService {



	@Autowired
	private SysApplicationService sysApplicationService;

	@Autowired
	private SysProjectAppService sysProjectAppService;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private SysProjectConfigService sysProjectConfigService;

	@Autowired
	private SysDictTypeService sysDictTypeService;

	@Autowired
	private SysDictDataService sysDictDataService;

	
	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("id","id:EQ");
				put("projectName","projectName:EQ");
				put("projectCode","projectCode:EQ");
				put("startTime", "createDatetime:GTEDATE");
				put("endTime", "createDatetime:LTEDATE");
				put("projectNameL","projectName:LIKE");
				put("del","del:EQ");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}

	/**
	 * 描述: 项目关联应用
	 * @param id
	 * @return com.fosung.framework.web.http.ResponseParam
	 * @author fuhao
	 * @date 2021/11/25 15:33
	 **/
	@Override
	public List<SysApplicationEntity> queryApp(Long id) {
		// 拼接查询参数
		Map<String, Object> searchAppParam = new HashMap<>();
		searchAppParam.put("status", AppStatus.normal);
		// 查询状态为开启的全部应用
		List<SysApplicationEntity> queryAllSysApp = sysApplicationService.queryAll(searchAppParam);
		if(id != null){
			searchAppParam.put("projectId",id);
			// 查询已经绑定的应用
			List<SysProjectApp> queryAllBindApp = sysProjectAppService.queryAll(searchAppParam);

			for(SysApplicationEntity allSysApp:queryAllSysApp){
				if(UtilCollection.isEmpty(queryAllBindApp)) allSysApp.setCheckFlag(false);
				for (SysProjectApp bindProjectApp:queryAllBindApp) {
					if(Long.toString(allSysApp.getId()).equals(Long.toString(bindProjectApp.getAppId()))){
						allSysApp.setCheckFlag(true);
						allSysApp.setCategoryId(bindProjectApp.getCategoryCode());
						break;
					}else {
						allSysApp.setCheckFlag(false);
					}
				}
			}
		}else {
			queryAllSysApp.forEach(sysApp -> {
				sysApp.setCheckFlag(false);
			});
		}

		return queryAllSysApp;
	}

	/**
	 * 描述: 项目绑定应用
	 * @param sysProjectApp
	 * @return void
	 * @author fuhao
	 * @date 2021/11/25 19:11
	 **/
	@Override
	public void bindApp(SysProjectEntity sysProjectApp) {
		// 拼装参数
		HashMap<String, Object> searchParam = new HashMap<>();
		searchParam.put("projectId",sysProjectApp.getId());
		// 先查询存在的
		List<SysProjectApp> sysProjectApps = sysProjectAppService.queryAll(searchParam);
		if(UtilCollection.isNotEmpty(sysProjectApps)){
			// 删除所有的已存在的
			sysProjectAppService.delete(sysProjectApps.stream().map(map -> map.getId()).collect(Collectors.toList()));
		}
		// 重新保存
		if (sysProjectApp.getExistApps()) {
			sysProjectAppService.saveBatch(sysProjectApp.getApps());
		}
	}
	/**
	 * 描述: 保存项目信息
	 * @param sysProject
	 * @return void
	 * @author fuhao
	 * @date 2021/11/26 9:55
	 **/
	@Override
	public void saveInfo(SysProjectEntity sysProject) {
		if(sysProject.getId() != null){
			//清除缓存
			stringRedisTemplate.delete(RedisConstant.PROJECT_CONFIG+sysProject.getId());

			//由请求参数中获取需要更新的字段
			Set<String> updateFields = UtilDTO.toDTOExcludeFields(sysProject, Arrays.asList("id")).keySet(); ;
			updateFields.remove("del");
			updateFields.remove("projectCode");
			updateFields.remove("status");
			//按照字段更新对象
			this.update( sysProject , updateFields );
			// 重新绑定医用
			List<SysProjectApp> apps = sysProject.getApps();
			if (UtilCollection.isNotEmpty(apps)) {
				apps.forEach(projectApp -> {
					projectApp.setProjectId(sysProject.getId());
					projectApp.setCategoryCode(projectApp.getCategoryCode() == null?"OTHER":projectApp.getCategoryCode());
				});
			}
			this.bindApp(sysProject);
			sysProject.getSysProjectConfigEntity().setProjectId(sysProject.getId());
			//新增修改配置
			if(sysProject.getSysProjectConfigEntity().getId()!=null){
				sysProjectConfigService.update(sysProject.getSysProjectConfigEntity(), Lists.newArrayList("checkParty","idCardMust","faceCheck","theme"));
			}else {
				sysProjectConfigService.save(sysProject.getSysProjectConfigEntity());
			}
		}else {
			String projectCode = sysProject.getProjectCode() == null ? ProjectConstant.PROJECT_CODE_PREFIX + System.currentTimeMillis() : sysProject.getProjectCode();
			sysProject.setProjectCode(projectCode);
			SysProjectEntity save = this.save(sysProject);
			if (sysProject.getExistApps()) {
				// 保存项目应用关联
				List<SysProjectApp> apps = sysProject.getApps();
				if (UtilCollection.isNotEmpty(apps)) {
					apps.forEach(projectApp -> {
						projectApp.setProjectId(save.getId());
						projectApp.setCategoryCode(projectApp.getCategoryCode() == null?"OTHER":projectApp.getCategoryCode());
					});
					this.bindApp(sysProject);
				}
			}
			//新增保存配置
			sysProject.getSysProjectConfigEntity().setProjectId(save.getId());
			sysProjectConfigService.save(sysProject.getSysProjectConfigEntity());

			//初始化
			initProjectConfig(save.getId(),save.getProjectCode());
		}
	}

	/**
	 *初始化项目信息
	 *
	 * @param projectId
	 * @param projectCode
	 * @author liuke
	 * @date 2022/5/11 10:07
	 * @return void
	 */
	public void initProjectConfig(Long projectId ,String projectCode){
		List<SysDictTypeEntity> infos = Lists.newArrayList();

		//初始化租户的组织类型字典
		SysDictTypeEntity sysDictType = new SysDictTypeEntity();
		sysDictType.setId(IdGenerators.getNextId());
		sysDictType.setCreateDatetime(new Date());
		sysDictType.setLastUpdateDatetime(new Date());
		sysDictType.setDictType(Constant.LEVEL_TYPE);
		sysDictType.setDictName("组织层级类型");
		sysDictType.setRemark("组织层级类型数据");
		sysDictType.setProjectId(projectId);
		sysDictType.setStatus(DictStatus.normal);
		infos.add(sysDictType);

		//初始化学历
		SysDictTypeEntity sysDictEdu = new SysDictTypeEntity();
		sysDictEdu.setId(IdGenerators.getNextId());
		sysDictEdu.setCreateDatetime(new Date());
		sysDictEdu.setLastUpdateDatetime(new Date());
		sysDictEdu.setDictType(Constant.EDUCATION);
		sysDictEdu.setDictName("学历");
		sysDictEdu.setRemark("学历字典");
		sysDictEdu.setProjectId(projectId);
		sysDictEdu.setStatus(DictStatus.normal);
		infos.add(sysDictEdu);

		//初始化租户政治面貌
		SysDictTypeEntity sysDictPo = new SysDictTypeEntity();
		sysDictPo.setId(IdGenerators.getNextId());
		sysDictPo.setCreateDatetime(new Date());
		sysDictPo.setLastUpdateDatetime(new Date());
		sysDictPo.setDictType(Constant.POLITICS_STATUS);
		sysDictPo.setDictName("政治面貌");
		sysDictPo.setRemark("政治面貌");
		sysDictPo.setProjectId(projectId);
		sysDictPo.setStatus(DictStatus.normal);
		infos.add(sysDictPo);
		SysDictDataEntity sysDictDataEntity = new SysDictDataEntity();
		sysDictDataEntity.setDictType(Constant.POLITICS_STATUS);
		sysDictDataEntity.setProjectId(projectId);
		sysDictDataEntity.setDictValue("PARTY");
		sysDictDataEntity.setDictLabel("中共党员");
		sysDictDataEntity.setStatus(DictStatus.normal);
		sysDictDataService.save(sysDictDataEntity);
		SysDictDataEntity sysDictData = new SysDictDataEntity();
		sysDictData.setDictType(Constant.POLITICS_STATUS);
		sysDictData.setProjectId(projectId);
		sysDictData.setDictValue("YUBEIDANGYUAN");
		sysDictData.setDictLabel("预备党员");
		sysDictData.setStatus(DictStatus.normal);
		sysDictDataService.save(sysDictData);

		//初始化租户的组织类型字典
		SysDictTypeEntity sysDictNa = new SysDictTypeEntity();
		sysDictNa.setId(IdGenerators.getNextId());
		sysDictNa.setCreateDatetime(new Date());
		sysDictNa.setLastUpdateDatetime(new Date());
		sysDictNa.setDictType(Constant.NATION);
		sysDictNa.setDictName("民族");
		sysDictNa.setRemark("民族数据");
		sysDictNa.setProjectId(projectId);
		sysDictNa.setStatus(DictStatus.normal);
		infos.add(sysDictNa);

		//初始化租户的组织类型字典
		SysDictTypeEntity sysDictPos = new SysDictTypeEntity();
		sysDictPos.setId(IdGenerators.getNextId());
		sysDictPos.setCreateDatetime(new Date());
		sysDictPos.setLastUpdateDatetime(new Date());
		sysDictPos.setDictType(Constant.POSITIONTYPE);
		sysDictPos.setDictName("岗位");
		sysDictPos.setRemark("岗位类型");
		sysDictPos.setProjectId(projectId);
		sysDictPos.setStatus(DictStatus.normal);
		infos.add(sysDictPos);

		//初始化岗位身份类别
		SysDictTypeEntity sysDictAttr = new SysDictTypeEntity();
		sysDictAttr.setId(IdGenerators.getNextId());
		sysDictAttr.setCreateDatetime(new Date());
		sysDictAttr.setLastUpdateDatetime(new Date());
		sysDictAttr.setDictType(Constant.IDATTRS);
		sysDictAttr.setDictName("身份类型");
		sysDictAttr.setRemark("身份类型");
		sysDictAttr.setProjectId(projectId);
		sysDictAttr.setStatus(DictStatus.normal);
		infos.add(sysDictAttr);

		//初始化岗位身份类别
		SysDictTypeEntity sysDictApp = new SysDictTypeEntity();
		sysDictApp.setId(IdGenerators.getNextId());
		sysDictApp.setCreateDatetime(new Date());
		sysDictApp.setLastUpdateDatetime(new Date());
		sysDictApp.setDictType(Constant.APPCATE);
		sysDictApp.setDictName("应用类型");
		sysDictApp.setRemark("应用类型");
		sysDictApp.setProjectId(projectId);
		sysDictApp.setStatus(DictStatus.normal);
		infos.add(sysDictApp);
		//初始化手机号脱敏
		SysDictTypeEntity sysDictTel = new SysDictTypeEntity();
		sysDictTel.setId(IdGenerators.getNextId());
		sysDictTel.setCreateDatetime(new Date());
		sysDictTel.setLastUpdateDatetime(new Date());
		sysDictTel.setDictType(Constant.TELEPHONE_DESENSITIZATION);
		sysDictTel.setDictName("手机号是否脱敏显示");
		sysDictTel.setRemark("手机号是否脱敏显示");
		sysDictTel.setProjectId(projectId);
		sysDictTel.setStatus(DictStatus.normal);
		infos.add(sysDictTel);
		SysDictDataEntity sysDictDataTel = new SysDictDataEntity();
		sysDictDataTel.setDictType(Constant.TELEPHONE_DESENSITIZATION);
		sysDictDataTel.setProjectId(projectId);
		sysDictDataTel.setDictValue("N");
		sysDictDataTel.setDictLabel("开启/关闭（Y/N）");
		sysDictDataTel.setStatus(DictStatus.normal);
		sysDictDataService.save(sysDictDataTel);

		//字典管理真实姓名
		SysDictTypeEntity sysDictRealName = new SysDictTypeEntity();
		sysDictRealName.setId(IdGenerators.getNextId());
		sysDictRealName.setCreateDatetime(new Date());
		sysDictRealName.setLastUpdateDatetime(new Date());
		sysDictRealName.setDictType(Constant.REAL_NAME_MAST);
		sysDictRealName.setDictName("真实姓名是否必填");
		sysDictRealName.setRemark("真实姓名是否必填");
		sysDictRealName.setProjectId(projectId);
		sysDictRealName.setStatus(DictStatus.normal);
		infos.add(sysDictRealName);
		SysDictDataEntity sysDictDataRealName = new SysDictDataEntity();
		sysDictDataRealName.setDictType(Constant.REAL_NAME_MAST);
		sysDictDataRealName.setProjectId(projectId);
		sysDictDataRealName.setDictValue("N");
		sysDictDataRealName.setDictLabel("开启/关闭（Y/N）");
		sysDictDataRealName.setStatus(DictStatus.normal);
		sysDictDataService.save(sysDictDataRealName);
		sysDictTypeService.saveinfo(infos);
	}

	/**
	 * 描述: 项目级联删除
	 * @param list
	 * @return void
	 * @author fuhao
	 * @date 2021/12/3 10:39
	 **/
	@Override
	public void deleteInfo(List<AppBaseIdParam> list) {
		List<Long> ids = list.stream().map(map -> map.getId()).collect(Collectors.toList());
		// 删除项目信息
		this.delete(ids);
		for (int i = 0; i < list.size(); i++) {
			Map<String,Object> delParam = new HashMap<>();
			delParam.put("projectId",list.get(i).getId());
			// 管理要删除的表名(物理删除)
			List<String> deleteTableNames = new ArrayList<>();
			// 项目关联应用
			deleteTableNames.add("sys_project_app");
			// 角色资源表
			deleteTableNames.add("sys_role_resource");
			// 角色应用资源表
			deleteTableNames.add("sys_role_app");
			// 角色管理范围表
			deleteTableNames.add("sys_role_scop");
			// 用户资源表
			deleteTableNames.add("sys_user_resource");
			// 用户角色表
			deleteTableNames.add("sys_user_role");
			// 用户角色范围表
			deleteTableNames.add("sys_user_role_scop");
			for (String tablename:deleteTableNames) {
				delParam.put("tablename",tablename);
				entityDao.deleteAllByProjectId(delParam);
			}
			////////////////////
			Map<String,Object> updateParam = new HashMap<>();
			updateParam.put("projectId",list.get(i).getId());
			// 管理要删除的表名(逻辑删除)
			List<String> updateTableNames = new ArrayList<>();
			// 角色表
			updateTableNames.add("sys_role");
			// 用户表
			updateTableNames.add("sys_user");
			// 组织表
			updateTableNames.add("sys_org");
			for (String tablename:updateTableNames) {
				updateParam.put("tablename",tablename);
				entityDao.updateAllByProjectId(updateParam);
			}
		}

	}

	/**
	 *根据组织编码获取组织id
	 *
	 * @author liuke
	 * @date 2022/3/9 14:46
	 * @return java.lang.Long
	 */
	@Override
	public Long getProjectId(String projectCode){
		if(UtilString.isBlank(projectCode)){
			return 0L;
		}
		String projectId = stringRedisTemplate.opsForValue().get(RedisConstant.PROJECT_DETAIL+projectCode);
		if(UtilString.isBlank(projectId)){
			Map<String,Object> params = Maps.newHashMap();
			params.put("projectCode",projectCode);
			List<SysProjectEntity> projectEntities = this.queryAll(params);
			if(!UtilCollection.sizeIsEmpty(projectEntities)){
				projectId = projectEntities.get(0).getId().toString();
				stringRedisTemplate.opsForValue().set(RedisConstant.PROJECT_DETAIL+projectCode,projectId);
			}
		}
		if(UtilString.isBlank(projectId)){
			return 0l;
		}
		return Long.valueOf(projectId);
	}

	/**
	 *根据项目id获取项目配置
	 *
	 * @author liuke
	 * @date 2022/3/9 14:46
	 * @return java.lang.Long
	 */
	@Override
	public SysProjectConfigEntity getProjectConfig(Long projectId){
		SysProjectConfigEntity sysProjectConfigEntity = new SysProjectConfigEntity();
		if(projectId==null||projectId==0l){
			return sysProjectConfigEntity;
		}
		String projectConfig = stringRedisTemplate.opsForValue().get(RedisConstant.PROJECT_CONFIG+projectId);
		if(UtilString.isBlank(projectConfig)){
			Map<String,Object> searchParam = Maps.newHashMap();
			searchParam.put("projectId",projectId);
			List<SysProjectConfigEntity> sysProjectConfigEntities = sysProjectConfigService.queryAll(searchParam);
			if(UtilCollection.isNotEmpty(sysProjectConfigEntities)){
				sysProjectConfigEntity = sysProjectConfigEntities.get(0);
			}
			//获取字典里的配置
			getDictConfig(projectId,sysProjectConfigEntity);
			stringRedisTemplate.opsForValue().set(RedisConstant.PROJECT_CONFIG+projectId,JsonMapper.toJSONString(sysProjectConfigEntity),1, TimeUnit.HOURS);

		}else {
           sysProjectConfigEntity =  JsonMapper.parseObject(projectConfig,SysProjectConfigEntity.class);
        }
		return sysProjectConfigEntity;
	}

	private void getDictConfig(Long projectId,SysProjectConfigEntity sysProjectConfigEntity){
		Map<String,Object> dictParam = Maps.newHashMap();
		dictParam.put("projectId",projectId);
		dictParam.put("dictTypes",SysDictDataServiceImpl.CONFIG_DICT);
		List<SysDictDataEntity> dictParams = sysDictDataService.queryAll(dictParam);
		for (SysDictDataEntity param : dictParams) {
			switch (param.getDictType()){
				case  Constant.TELEPHONE_DESENSITIZATION:
					sysProjectConfigEntity.setTelephoneDesensitization(UtilString.equals("Y",param.getDictValue()));
					break;
				case  Constant.REAL_NAME_MAST:
					sysProjectConfigEntity.setRealNameMast(UtilString.equals("Y",param.getDictValue()));
					break;
				default:
					break;
			}
		}
	}

	/**
	 *移除过期redis
	 *
	 * @param projectId
	 * @author liuke
	 * @date 2022/5/5 16:19
	 * @return void
	 */
	public void removeConfigRedis(Long projectId){
		stringRedisTemplate.delete(RedisConstant.PROJECT_CONFIG+projectId);
	}
}
