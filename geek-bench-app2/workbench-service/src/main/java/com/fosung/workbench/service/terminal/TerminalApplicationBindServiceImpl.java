package com.fosung.workbench.service.terminal;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.exception.AppException;
import com.fosung.framework.dao.config.mybatis.page.MybatisPageRequest;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import com.fosung.workbench.common.AppMessageContent;
import com.fosung.workbench.config.TerminalContent;
import com.fosung.workbench.dao.terminal.TerminalApplicationBindDao;
import com.fosung.workbench.dict.SearchOperateType;
import com.fosung.workbench.dict.StatusType;
import com.fosung.workbench.dto.terminal.TerminalApplicationBindDto;
import com.fosung.workbench.entity.application.ApplicationVersionEntity;
import com.fosung.workbench.entity.terminal.TerminalApplicationBindEntity;
import com.fosung.workbench.entity.terminal.TerminalApplicationConfigEntity;
import com.fosung.workbench.service.application.ApplicationVersionService;
import com.fosung.workbench.service.search.UniSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TerminalApplicationBindServiceImpl extends AppJPABaseDataServiceImpl<TerminalApplicationBindEntity, TerminalApplicationBindDao>
	implements TerminalApplicationBindService {

	@Autowired
	private TerminalApplicationConfigService configService;
	@Autowired
	private ApplicationVersionService applicationVersionService;

	@Autowired
	private UniSearchService uniSearchService;
	
	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("projectId","projectId:EQ");
				put("terminalId","terminalId:EQ");
				put("appId","appId:EQ");
				put("appVersionId","appVersionId:EQ");
				put("appConfigId","appConfigId:EQ");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}

	/**
	 * 查询终端应用版本信息分页
	 * @param terminalApplicationBindDto
	 * @return
	 * @author fuhao
	 * @date 2021/10/15 10:16
	 */
	@Override
	public Page<TerminalApplicationBindEntity> queryTerminalAppVersion(TerminalApplicationBindDto terminalApplicationBindDto) {
		// 获取拼装参数
		Map<String, Object> searchParam =  UtilDTO.toDTO(terminalApplicationBindDto, null);
		// 拼装分页信息
		Pageable pageable = MybatisPageRequest.of(terminalApplicationBindDto.getPageNum(),terminalApplicationBindDto.getPageSize());
		// 分页查询终端应用app版本信息
		Page<TerminalApplicationBindEntity> terminalAppVersionPage = entityDao.queryTerminalAppVersion(searchParam, pageable);

		return terminalAppVersionPage;
	}

	/**
	 * 终端应用版本发布下线功能
	 * @param terminalApplicationBindDto
	 * @author fuhao
	 * @date 2021/10/15 10:16
	 */
	@Override
	@Transactional
	public void updateStatus(TerminalApplicationBindDto terminalApplicationBindDto) {
		Map<String, Object> searchParam =  UtilDTO.toDTO(terminalApplicationBindDto, null);
		// 更改绑定应用版本状态
		entityDao.updateBind(searchParam);
		// 更改绑定应用状态
		if(terminalApplicationBindDto.getStatus().equals(StatusType.offline.getValue())){
			entityDao.updateTerminalAppConfig(searchParam);

			// 同步信息到统一搜索 2021 12 24 加
			TerminalApplicationConfigEntity terminalAppConfig = configService.get(terminalApplicationBindDto.getAppConfigId());
			uniSearchService.searchSynchronizationData(terminalAppConfig, SearchOperateType.del);

		}else if(terminalApplicationBindDto.getStatus().equals(StatusType.release.getValue())){
			List<TerminalApplicationBindEntity> terminalApplicationBindEntities = this.queryAll(searchParam);
			List<TerminalApplicationBindEntity> releaseTerminalApplicationBindEntities = terminalApplicationBindEntities
					.stream().filter( m -> StatusType.release.equals(m.getStatus())).collect(Collectors.toList());
			if(releaseTerminalApplicationBindEntities != null && releaseTerminalApplicationBindEntities.size() > 1){
				throw new AppException("存在已发布的版本，请先下线发布的版本！");
			}
			ApplicationVersionEntity applicationVersion = applicationVersionService.get(releaseTerminalApplicationBindEntities.get(0).getAppVersionId());

			searchParam.put(TerminalContent.VERSION_NUM,applicationVersion.getVersionNum());
			searchParam.put(TerminalContent.VERSION_NAME,applicationVersion.getVersionName());
			searchParam.put(TerminalContent.APP_VERSION_ID,applicationVersion.getId());
			entityDao.updateTerminalAppConfig(searchParam);

			// 同步信息到统一搜索 2021 12 24 加
			TerminalApplicationConfigEntity terminalAppConfig = configService.get(terminalApplicationBindDto.getAppConfigId());
			uniSearchService.searchSynchronizationData(terminalAppConfig, SearchOperateType.add);
		}

	}

	/**
	 * 描述:  根据应用id删除绑定信息
	 *
	 * @param appId
	 * @createDate: 2021/10/21 14:34
	 * @author: gaojian
	 * @modify:
	 * @return: void
	 */
	@Override
	public void deleteBindByAppId(Long appId) {

		// 1. 非空判断
		Assert.notNull(appId, AppMessageContent.APP_ID_IS_NULL);

		// 2. 删除应用版本信息
		entityDao.deleteAllByAppId(appId);
	}

	/**
	 * 描述:  根据版本id删除终端绑定的版本
	 *
	 * @param versionId
	 * @createDate: 2021/10/21 14:34
	 * @author: gaojian
	 * @modify:
	 * @return: void
	 */
	@Override
	public void deleteBindByVersionId(Long versionId) {

		// 1. 非空判断
		Assert.notNull(versionId, AppMessageContent.APP_ID_IS_NULL);

		// 2. 删除终端绑定应用版本信息
		entityDao.deleteAllByAppVersionId(versionId);
	}


}
