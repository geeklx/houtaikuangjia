package com.geek.workbench.service.project;

import java.util.*;

import com.fosung.framework.common.exception.AppException;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.workbench.common.GlobalVariableKey;
import com.geek.workbench.common.MessageContent;
import com.geek.workbench.dao.project.ProjectManagerDao;
import com.geek.workbench.dto.project.ProjectManagerSaveDto;
import com.geek.workbench.entity.project.ProjectManagerEntity;
import com.geek.workbench.service.feign.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * 描述:  项目管理员服务实现类
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Service
public class ProjectManagerServiceImpl extends AppJPABaseDataServiceImpl<ProjectManagerEntity, ProjectManagerDao>
	implements ProjectManagerService {

	/**
	 * 描述:  角色服务
	 * @createDate: 2021/11/2 8:56
	 * @author: gaojian
	 */
	@Autowired
	private RoleService roleService;

	@Value(value = "${app.customParams.appId:3496254400060468413}")
	private Long appId;

	@Value(value = "${app.customParams.projectRoleCode:projectAdmin}")
	private String roleCode;
	
	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("id","id:EQ");
				put("userId","userId:EQ");
				put("projectId","projectId:EQ");
				put("userName","userName:LIKE");
				put("userPhone", "userPhone:EQ");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}


	/**
	 * 描述:  保存项目管理员
	 *
	 * @param data
	 * @createDate: 2021/10/13 20:42
	 * @author: gaojian
	 * @modify:
	 * @return: void
	 */
	@Override
	@Transactional( rollbackFor = RuntimeException.class)
	public void saveInfo(ProjectManagerSaveDto data) {

		// 1. 非空判断
		if(data == null || data.getProjectId() == null){
			throw new AppException(MessageContent.PARAMS_IS_NULL);
		}

		// 2. 删除以前管理员
		entityDao.deleteAllByProjectId(data.getProjectId());

		// 3. 循环插入数据
		if(data.getList() != null && !data.getList().isEmpty()){

			data.getList().forEach(projectManagerEntity -> {

				// 3.1. 判断此用户是否已经属于此项目的管理员
				ProjectManagerEntity result = entityDao.getFirstByProjectIdAndUserIdAndDel(projectManagerEntity.getProjectId()
						,projectManagerEntity.getUserId(),
						Boolean.FALSE);
				if(result != null){
					throw new AppException(MessageContent.PROJECT_MANAGER_IS_EXIST);
				}

				// 3.2. 保存信息
				save(projectManagerEntity);
			});
		}
	}

	/**
	 * 描述:  查询项目管理员
	 *
	 * @param projectId
	 * @createDate: 2021/11/2 8:54
	 * @author: gaojian
	 * @modify:
	 * @return: java.util.Map<java.lang.String, java.lang.Object>
	 */
	@Override
	public Map<String, Object> queryInfo(Long projectId) {

		// 1. 查询已选中
		Map<String,Object> searchParams = new HashMap<>(8);
		searchParams.put("projectId",projectId);
		List<ProjectManagerEntity> list = queryAll(searchParams);
		List<Map<String,Object>> checked = new ArrayList<>();
		Set<String> userIds = new HashSet<>();
		list.forEach(projectManagerEntity -> {
			Map<String,Object> checkedMap = new HashMap<>(8);
			userIds.add(projectManagerEntity.getUserId());
			checkedMap.put(GlobalVariableKey.USER_ID,projectManagerEntity.getUserId());
			checkedMap.put(GlobalVariableKey.USER_NAME,projectManagerEntity.getUserName());
			checked.add(checkedMap);
		});

		// 1. 根据角色编码查询用户信息
		List<Map<String,Object>> uncheck = new ArrayList<>();
		ResponseParam responseParam = roleService.queryData(roleCode);
		if((Boolean) responseParam.get(ResponseParam.SUCCESS_PARAM_VALUE)){
			List<Map<String,Object>> allUser = (List<Map<String, Object>>) responseParam.get(ResponseParam.DATA_LIST_PARAM);
			for(Map map : allUser ){
//				if(map.get(GlobalVariableKey.ID) != null && !userIds.contains(map.get(GlobalVariableKey.ID).toString())){
					Map<String,Object> uncheckMap = new HashMap<>(8);
					// 用户权限中心系统的用户只有 status 值为 VALID 的用户才是有效用户
					if("VALID".equals(map.get("status"))){
						uncheckMap.put(GlobalVariableKey.USER_ID,map.get(GlobalVariableKey.ID));
						uncheckMap.put(GlobalVariableKey.USER_NAME,(map.get("realName") == null ? "" : map.get("realName")));
						uncheck.add(uncheckMap);
					}
//				}
			}
		}

		// 3. 组装返回值
		Map<String,Object> result = new HashMap<>(8);
		result.put(GlobalVariableKey.CHECKED,checked);
		result.put(GlobalVariableKey.UNCHECK,uncheck);
		return result;
	}

	/**
	 * 描述:  根据项目id删除管理员信息
	 *
	 * @param projectId
	 * @createDate: 2021/10/19 8:45
	 * @author: gaojian
	 * @modify:
	 * @return: java.lang.Integer
	 */
	@Override
	public Integer deleteByProjectId(Long projectId) {

		Assert.notNull(projectId,MessageContent.PROJECT_ID_IS_NULL);
		return entityDao.deleteAllByProjectId(projectId);
	}
}
