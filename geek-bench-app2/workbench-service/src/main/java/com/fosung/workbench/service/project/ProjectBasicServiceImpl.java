package com.fosung.workbench.service.project;

import java.util.*;
import java.util.stream.Collectors;

import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.exception.AppException;
import com.fosung.framework.common.secure.auth.AppUserDetails;
import com.fosung.framework.common.secure.auth.AppUserDetailsService;
import com.fosung.workbench.common.GlobalVariableKey;
import com.fosung.workbench.common.MessageContent;
import com.fosung.workbench.common.ProjectStatusEnum;
import com.fosung.workbench.common.RoleCode;
import com.fosung.workbench.dao.project.ProjectBasicDao;
import com.fosung.workbench.entity.project.ProjectBasicEntity;
import com.fosung.workbench.entity.terminal.TerminalBasicEntity;
import com.fosung.workbench.service.terminal.TerminalBasicService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.server.context.SecurityContextServerWebExchange;
import org.springframework.stereotype.Service;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * 描述:  项目基本信息服务实现类
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Service
public class ProjectBasicServiceImpl extends AppJPABaseDataServiceImpl<ProjectBasicEntity, ProjectBasicDao>
	implements ProjectBasicService {

	/**
	 * 描述:  注入登录用户详情服务类
	 * @createDate: 2021/10/13 18:03
	 * @author: gaojian
	 */
	@Autowired
	private AppUserDetailsService appUserDetailsService ;

	/**
	 * 描述:  项目管理员服务类
	 * @createDate: 2021/10/19 8:48
	 * @author: gaojian
	 */
	@Autowired
	private ProjectManagerService projectManagerService;

	/**
	 * 描述:  终端基础服务
	 * @createDate: 2021/10/19 8:48
	 * @author: gaojian
	 */
	@Autowired
	private TerminalBasicService terminalBasicService;

	@Value(value = "${app.customParams.superRoleCode:superAdmin}")
	private String superRoleCode;

	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("id","id:EQ");
				put("projectCode","projectCode:EQ");
				put("projectName","projectName:LIKE");
				put("remark","remark:LIKE");
				put("start","start:EQ");
				put("person","person:LIKE");
				put("startTime", "createDatetime:GTEDATE");
				put("endTime", "createDatetime:LTEDATE");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}


	/**
	 * 描述:  查询项目为选项信息
	 *
	 * @createDate: 2021/10/13 17:50
	 * @author: gaojian
	 * @modify:
	 * @return: java.util.Map<java.lang.String, java.lang.Object>
	 */
	@Override
	public List<Map<String, Object>> queryProjectOption() {

		// 1. 获取当前登录用户信息
		AppUserDetails appUserDetails = appUserDetailsService.getAppUserDetails();
		if( appUserDetails == null){
			throw new AppException(MessageContent.LOGIN_USER_IS_NULL);
		}

		// 2. 判断登录用户是否为超级管理员如果为超级管理员则查询全部项目
		Set<String> roleCodes = appUserDetails.getUserRoleCodes();
		for(String roleCode : roleCodes){
			if(StringUtils.equals(roleCode, superRoleCode)){
				return entityDao.queryAllProjectOption();
			}
		}

		// 3. 登录用户没有超级管理员权限则只能查询自己管理的项目
		return entityDao.queryProjectOption(appUserDetails.getUserId());

	}

	/**
	 * 描述:  查询全部项目
	 *
	 * @createDate: 2021/10/27 19:36
	 * @author: gaojian
	 * @modify:
	 * @return: java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
	 */
	@Override
	public List<Map<String, Object>> queryAllProjectOption() {
		return entityDao.queryAllProjectOption();
	}

	/**
	 * 描述:  保存项目信息
	 *
	 * @param projectBasicEntity
	 * @createDate: 2021/10/13 19:17
	 * @author: gaojian
	 * @modify:
	 * @return:
	 */
	@Override
	public void saveInfo(ProjectBasicEntity projectBasicEntity) {

		// 1. 判断项目编码是否已存在
		projectBasicEntity.setStatus(ProjectStatusEnum.RELEASE.getValue());
		ProjectBasicEntity result = entityDao.getFirstByProjectCodeAndDel(projectBasicEntity.getProjectCode(),Boolean.FALSE);
		if(result != null){
			throw new AppException(MessageContent.PROJECT_CODE_IS_EXIST);
		}

		// 2. 保存项目
		save(projectBasicEntity);
	}

	/**
	 * 描述:  修改项目信息
	 *
	 * @param projectBasicEntity
	 * @createDate: 2021/10/13 19:17
	 * @author: gaojian
	 * @modify:
	 * @return:
	 */
	@Override
	public void updateInfo(ProjectBasicEntity projectBasicEntity) {

		// 1. 项目编码存在且项目编码和当前修改id不一致说明是两条不同的数据，两条不同的数据项目编码不可以相同
		ProjectBasicEntity result = entityDao.getFirstByProjectCodeAndDel(projectBasicEntity.getProjectCode(),Boolean.FALSE);
		if(result != null && !projectBasicEntity.getId().equals(result.getId())){
			throw new AppException(MessageContent.PROJECT_CODE_IS_EXIST);
		}

		// 2. 修改项目
		update(projectBasicEntity, Arrays.asList("projectCode","projectName","remark","person"));
	}

	/**
	 * 描述:  删除项目信息
	 *
	 * @param list
	 * @createDate: 2021/10/19 8:43
	 * @author: gaojian
	 * @modify:
	 * @return: void
	 */
	@Override
	@Transactional( rollbackFor = RuntimeException.class)
	public void deleteInfo(List<AppBaseIdParam> list) {

		// 1. 删除项目信息
		delete(list.stream().map(map -> map.getId()).collect(Collectors.toList()));

		// 2. 删除终端相关信息
		list.forEach(appBaseIdParam -> {

			// 3. 删除项目管理员
			projectManagerService.deleteByProjectId(appBaseIdParam.getId());

			// 4. 查询项目绑定终端
			Map<String,Object> searchParams = new HashMap<>(8);
			searchParams.put(GlobalVariableKey.PROJECT_ID,appBaseIdParam.getId());
			List<TerminalBasicEntity> terminalBasicEntities = terminalBasicService.queryAll(searchParams);

			// 5. 组装终端主键集合
			List<AppBaseIdParam> appBaseIdParamList = new ArrayList<>();
			terminalBasicEntities.forEach(terminalBasicEntity -> {
				AppBaseIdParam appBaseIdParam1 = new AppBaseIdParam();
				appBaseIdParam1.setId(terminalBasicEntity.getId());
				appBaseIdParamList.add(appBaseIdParam1);
			});

			// 6. 删除终端信息
			terminalBasicService.deleteAllTerminal(appBaseIdParamList);
		});


	}

	/**
	 * 描述:  根据项目主键顺序查询项目信息
	 * @createDate: 2021/10/27 18:29
	 * @author: gaojian
	 * @modify:
	 * @param list
	 * @return: java.util.List<com.fosung.workbench.entity.project.ProjectBasicEntity>
	 */
	@Override
	public List<ProjectBasicEntity> queryProjectInfoByIds(List<Long> list) {
		return entityDao.queryProjectInfoByIds(list);
	}
}
