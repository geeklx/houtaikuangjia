package com.geek.workbench.service.terminal;

import java.util.*;
import java.util.concurrent.ExecutionException;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.exception.AppException;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.dao.config.mybatis.page.MybatisPageRequest;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.workbench.AppBean.ApkBasic;
import com.geek.workbench.common.GlobalVariableKey;
import com.geek.workbench.common.TerminalMessageContent;
import com.geek.workbench.config.TerminalContent;
import com.geek.workbench.dao.terminal.TerminalVersionDao;
import com.geek.workbench.dict.RangeType;
import com.geek.workbench.dict.StatusType;
import com.geek.workbench.dict.TerminalCommonConfigType;
import com.geek.workbench.dict.UmsMessageType;
import com.geek.workbench.dto.application.ApplicationQueryDto;
import com.geek.workbench.dto.terminal.TerminalVersionDto;
import com.geek.workbench.dto.ums.UmsDto;
import com.geek.workbench.entity.config.TerminalConfigManageEntity;
import com.geek.workbench.entity.terminal.*;
import com.geek.workbench.entity.ums.TerminalPersonEntity;
import com.geek.workbench.service.application.ApplicationQueryService;
import com.geek.workbench.service.config.TerminalConfigManageService;
import com.geek.workbench.service.project.ProjectBasicService;
import com.geek.workbench.service.ums.TerminalPersonService;
import com.geek.workbench.service.ums.UmsService;
import com.geek.workbench.util.ApkParserUtil;
import com.geek.workbench.util.CacheUtil;
import com.geek.workbench.entity.terminal.*;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;

import javax.transaction.Transactional;

@Service
public class TerminalVersionServiceImpl extends AppJPABaseDataServiceImpl<TerminalVersionEntity, TerminalVersionDao>
	implements TerminalVersionService {

	@Autowired
	private TerminalBasicService terminalBasicService;

	@Autowired
	private TerminalUpdateLogService updateLogService;

	@Autowired
	private TerminalVersionUpgradeRangeService upgradeRangeService;

	@Autowired
	private ProjectBasicService projectBasicService;

	/**
	 * 描述:  注入终端运行配置服务
	 * @createDate: 2021/11/3 18:44
	 * @author: gaojian
	 */
	@Autowired
	private TerminalConfigManageService terminalConfigManageService;


	/**
	 * 描述:  注入终端公共配置服务
	 * @createDate: 2021/11/3 18:44
	 * @author: gaojian
	 */
	@Autowired
	private TerminalConfigCommonService terminalConfigCommonService;

	/**
	 * 描述:  注入查询服务
	 * @createDate: 2021/11/6 18:18
	 * @author: gaojian
	 */
	@Autowired
	private ApplicationQueryService applicationQueryService;

	/**
	 * 描述:  注入统一消息服务
	 * @createDate: 2022年2月23日09:11:49
	 * @author: gaojian
	 */
	@Autowired
	private UmsService umsService;

	/**
	 * 描述:  注入终端绑定人员服务  -- TODO 测试用 正式环境需更换获取人员的方式
	 * @createDate: 2022年2月23日09:11:55
	 * @author: gaojian
	 */
	@Autowired
	private TerminalPersonService terminalPersonService;
	
	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("terminalId","terminalId:EQ");
				put("status","status:EQ");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}

	/**
	 *终端版本基本信息缓存
	 *
	 * @author liuke
	 * @date 2021/10/15 14:56
	 * @return
	 */
	public LoadingCache<String,TerminalVersionEntity> terminalVersionCache = CacheUtil.getInstance().build(
			new CacheLoader<String,TerminalVersionEntity>() {
				@Override
				public TerminalVersionEntity load(String terminalId) throws Exception {
					Map<String,Object> searchParam = Maps.newHashMap();
					searchParam.put(TerminalContent.TERMINAL_ID,terminalId);
					searchParam.put("status", StatusType.release);
					String[] sorts = {"terminalVersion:desc"};
					List<TerminalVersionEntity> result = queryAll(searchParam,sorts);
					if(UtilCollection.sizeIsEmpty(result)){
						return new TerminalVersionEntity();
					}else {
						return result.get(0);
					}
				}
			}
	);
	/**
	 * 保存实体对象 。
	 * @param terminalVersionEntity
	 * @return
	 * @author fuhao
	 * @date 2021/10/14 9:18
	 */
	@Override
	@Transactional
	public ResponseParam saveInfo(TerminalVersionEntity terminalVersionEntity) {
		// 判断包名是否一致，如果不一致则不能保存
		Map apkInfo =  ApkParserUtil.apkParser(terminalVersionEntity.getInstallationPackage());
		TerminalBasicEntity terminalBasicEntity = terminalBasicService.get(terminalVersionEntity.getTerminalId());
		if(apkInfo != null && terminalBasicEntity != null){
			if(!apkInfo.get("appPackageName").equals(terminalBasicEntity.getPackageName())){
				return ResponseParam.fail().message("包名不一致不能上传!");
			}
		}else {
			return ResponseParam.fail().message("未能解析安装包");
		}

		// 获取应用范围
		List<TerminalVersionUpgradeRangeEntity> upgradeRangeValues = terminalVersionEntity.getUpgradeRangeValues();

		// 如果是全部获取更新范围
		if(RangeType.all.equals(terminalVersionEntity.getUpgradeRangeType())){
			upgradeRangeValues = getAllUpgradeRange();
		}
		upgradeRangeValues = upgradeRangeValues == null ? new ArrayList<>():upgradeRangeValues;
		TerminalVersionEntity terminalVersion = new TerminalVersionEntity();
		// 新增或修改
		if(terminalVersionEntity.getId() != null) {

			// 查询纯数字最新版本号
			Integer maxVersionNum = entityDao.getMaxVersionNum(terminalVersionEntity);
			terminalVersionEntity.setTerminalVersionNum(maxVersionNum + 1);

			// 按照字段更新对象
			Set<String> updateFields = UtilDTO.toDTOExcludeFields(terminalVersionEntity, Arrays.asList("id")).keySet();

			// 移除不需要更新的字段
			updateFields.remove("downloadNumber");
			updateFields.remove("installationsNumber");
			updateFields.remove("status");
			this.update(terminalVersionEntity, updateFields);

			// 拼装日志参数
			terminalVersion.setUpgradeRangeType(terminalVersionEntity.getUpgradeRangeType());
			terminalVersion.setId(terminalVersionEntity.getId());
		}else {
			terminalVersionEntity.setStatus(StatusType.noRelease);
			terminalVersionEntity.setDownloadNumber(0);
			terminalVersionEntity.setInstallationsNumber(0);
			terminalVersionEntity.setTerminalVersionNum(0);
			terminalVersion = this.save(terminalVersionEntity);
		}

		// 更新之前物理删除以前存在的数据
		Integer maxUpdateIndex = 1 ;
		List<TerminalVersionUpgradeRangeEntity> deleteList = queryUpgradeRange(terminalVersionEntity);
		if(deleteList != null && deleteList.size() > 0){
			maxUpdateIndex = deleteList.get(0).getUpdateIndex() + 1;
			deleteList.forEach(upgradeRange -> {
				upgradeRangeService.delete(upgradeRange.getId());
			});
		}

		// 保存新的应用范围
		for (TerminalVersionUpgradeRangeEntity upgradeRangeValue:upgradeRangeValues) {
			upgradeRangeValue.setUpgradeRangeType(terminalVersion.getUpgradeRangeType());
			upgradeRangeValue.setTerminalVersionId(terminalVersion.getId());
			upgradeRangeValue.setUpdateIndex(maxUpdateIndex);
		}
		upgradeRangeService.saveBatch(upgradeRangeValues);
		return ResponseParam.saveSuccess();
	}

	/**
	 * 分页查询终端版本信息
	 * @param searchParam
	 * @return
	 * @author fuhao
	 * @date 2021/10/14 9:18
	 */
	@Override
	public Page<TerminalVersionEntity> queryByPageTerminalVersion(Map<String, Object> searchParam, TerminalVersionDto terminalVersionDto) {
		List<Map<String, Object>> projectList = projectBasicService.queryProjectOption();

		// 如果项目信息为空则返回空的终端信息
		if(projectList.size() == 0 ){
			return Page.empty();
		}

		ArrayList<Long> projects = new ArrayList<>();
		for (int i = 0; i < projectList.size(); i++) {
			Map<String, Object> project = projectList.get(i);
			projects.add((Long) project.get("dictValue"));
		}
		searchParam.put("projectList",projects);
		Pageable pageable = MybatisPageRequest.of(terminalVersionDto.getPageNum(),terminalVersionDto.getPageSize());
		return entityDao.queryByPageTerminalVersion(searchParam, pageable);
	}
	/**
	 * 终端版本发布下线功能
	 * @param terminalVersion
	 * @return
	 * @author fuhao
	 * @date 2021/10/14 9:18
	 */
	@Override
	public void updateStatus(TerminalVersionEntity terminalVersion) {

		if(terminalVersion.getStatus() != null && StringUtils.equals(StatusType.release.getValue(),terminalVersion.getStatus().getValue())){

			// 1. 校验运行配置信息是否配置
			TerminalVersionEntity result = get(terminalVersion.getId());
			checkConfigInfo(result.getTerminalId());

			// 2. 保存发布记录
			updateLogService.saveUpdateLog(result);

			// 3. 删除编辑历史记录
			updateLogService.deleteHistoryLog(result);

			// 4. 调用发送升级通知接口信息
			sendUpgradeMessage(result);

		}else if(terminalVersion.getStatus() != null && StringUtils.equals(StatusType.offline.getValue(),terminalVersion.getStatus().getValue())){

			// 5. 下线保存下线日志
			saveUpdateLog(terminalVersion.getId(),"下线");

		}

		// 6. 修改状态
		this.update(terminalVersion , Arrays.asList("status")) ;
	}

	/**
	 * 描述:  发送升级通知
	 * @createDate: 2022/2/22 15:58
	 * @author: gaojian
	 * @modify:
	 * @param
	 * @return: void
	 */
	public void sendUpgradeMessage(TerminalVersionEntity terminalVersionEntity){

		// 1. 组装参数
		UmsDto umsDto = new UmsDto();
		umsDto.setType(UmsMessageType.upgrade);
		umsDto.setTemplateCode("workbench-upgrade");
		String messageContent = terminalVersionEntity.getRemark();
		if(StringUtils.isBlank(messageContent)){
			umsDto.setMessageContent("1、性能调优、优化体验；2、修复了一些已知问题；");
		}else if(messageContent.length() > 20){
			umsDto.setMessageContent(messageContent.substring(0,20)+"...");
		}else{
			umsDto.setMessageContent(messageContent);
		}
		Long terminalId = terminalVersionEntity.getTerminalId();
		Map<String,Object> searchParams = new HashMap<>(8);
		searchParams.put("terminalId",terminalId);
		List<TerminalPersonEntity> list = terminalPersonService.queryAll(searchParams);
		Set<String> userIds = Sets.newHashSet();
		for(TerminalPersonEntity terminalPersonEntity : list ){
			userIds.add(terminalPersonEntity.getUserId());
		}
		umsDto.setUserIds(userIds);
		umsDto.setBusId(terminalVersionEntity.getId().toString());

		// 2. 调用消息推送接口
		umsService.sendUserMsg(umsDto);
	}

	/**
	 * 查询更新范围
	 * @param terminalVersionEntity
	 * @return
	 * @author fuhao
	 * @date 2021/10/14 9:18
	 */
	public List<TerminalVersionUpgradeRangeEntity> queryUpgradeRange(TerminalVersionEntity terminalVersionEntity){
		Long versionId = terminalVersionEntity.getId();
		Map<String,Object> search = new HashMap<>();
		search.put("terminalVersionId",versionId);
		List<TerminalVersionUpgradeRangeEntity> upgradeRangeList = upgradeRangeService.queryAll(search);
		if(upgradeRangeList != null && upgradeRangeList.size() > 0){
			return upgradeRangeList;
		}
		return null;
	}

	/**
	 * 保存发布范围日志
	 * @param terminalVersionId
	 * @param content
	 * @author fuhao
	 * @date 2021/10/14 9:18
	 */
	public void saveUpdateLog(Long terminalVersionId,String content){
		TerminalUpdateLogEntity terminalUpdateLogEntity = new TerminalUpdateLogEntity();
		terminalUpdateLogEntity.setTerminalVersionId(terminalVersionId);
		terminalUpdateLogEntity.setUpdateContent(content);
		updateLogService.save(terminalUpdateLogEntity);
	}
	/**
	 *
	 * 在缓存根据包取出终端版本
	 * @param terminalId
	 * @author liuke
	 * @date 2021/10/18 10:05
	 * @return java.util.List<com.fosung.workbench.entity.terminal.TerminalApplicationConfigEntity>
	 */
	@Override
	public TerminalVersionEntity getCacheTerminal(String terminalId){
		TerminalVersionEntity terminalVersionEntity  = new TerminalVersionEntity();
		try {
			terminalVersionEntity = terminalVersionCache.get(terminalId);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return terminalVersionEntity;
	}
	/**
	 * 获取版本信息
	 * @param terminalVersion
	 * @param terminalBasic
	 * @return
	 */
	@Override
	public ApkBasic getInfo(TerminalVersionEntity terminalVersion, TerminalBasicEntity terminalBasic){
		ApkBasic infoNew = new ApkBasic();
		infoNew.setServerVersionCode(terminalVersion.getTerminalVersion() == null? "" : terminalVersion.getTerminalVersion());
		infoNew.setServerVersionName(terminalVersion.getVersionName() == null ? "" :terminalVersion.getVersionName());
		infoNew.setAppPackageName(terminalBasic.getPackageName() == null ? "" :terminalBasic.getPackageName());
		infoNew.setApplicationLable(terminalBasic.getPackageName()== null ? "" : terminalBasic.getPackageName());
		infoNew.setMd5(DigestUtils.md5Hex(terminalBasic.getPackageName()== null ? "" : terminalBasic.getPackageName()));
		infoNew.setUpdateInfoTitle(terminalVersion.getUpgradeTitle()==null?"更新提醒":terminalVersion.getUpgradeTitle());
		infoNew.setUpdateInfo(terminalVersion.getRemark() == null?"版本优化":terminalVersion.getRemark());
		infoNew.setIsForce(null!=terminalVersion.getForceUpgrade() ? terminalVersion.getForceUpgrade():false);
		infoNew.setUpgradeBackImg(terminalVersion.getUpgradeBackImg()==null?"":terminalVersion.getUpgradeBackImg());
		infoNew.setApkPath(terminalVersion.getInstallationPackage());
		return infoNew;
	}

	/**
	 * 描述:  校验终端
	 * @createDate: 2021/11/3 16:40
	 * @author: gaojian
	 * @modify:
	 * @param terminalId
	 * @return: void
	 */
	public void checkConfigInfo(Long terminalId){

		// 1. 判断运行配置是否都进行选择
		Map<String,Object> searchParams = new HashMap<>(8);
		searchParams.put(GlobalVariableKey.TERMINAL_ID,terminalId);
		List<TerminalConfigManageEntity> list = terminalConfigManageService.queryAll(searchParams);
		list.forEach(terminalConfigManageEntity -> {
			if( StringUtils.isBlank(terminalConfigManageEntity.getConfigPlatform()) ||
				terminalConfigManageEntity.getConfigType() == null){
				throw new AppException(TerminalMessageContent.PLATFORM_IS_NULL);
			}
		});

		// 2. 校验访问控制是否存在
		TerminalConfigCommonEntity terminalConfigCommonEntity = new TerminalConfigCommonEntity();
		terminalConfigCommonEntity.setTerminalId(terminalId);
		terminalConfigCommonEntity.setConfigType(TerminalCommonConfigType.authorization.name());
		List<TerminalConfigCommonEntity> authorization = terminalConfigCommonService.getAllCommonConfigurationInfo(terminalConfigCommonEntity);
		authorization.forEach(terminalConfigCommonEntity1 -> {
			if(StringUtils.isBlank(terminalConfigCommonEntity1.getConfigValue())){
				throw new AppException(TerminalMessageContent.AUTHORIZATION_IS_NULL);
			}
		});
	}

	/**
	 * 描述:  获取全部更新范围
	 * @createDate: 2021/11/6 18:17
	 * @author: gaojian
	 * @modify:
	 * @param
	 * @return: java.util.List<com.fosung.workbench.entity.terminal.TerminalVersionUpgradeRangeEntity>
	 */
	public List<TerminalVersionUpgradeRangeEntity> getAllUpgradeRange(){

		List<TerminalVersionUpgradeRangeEntity> result = new ArrayList<>();
		ApplicationQueryDto applicationQueryDto = new ApplicationQueryDto();
		List<Map<String,Object>> list = applicationQueryService.queryRegional(applicationQueryDto);
		list.forEach(stringObjectMap -> {
			TerminalVersionUpgradeRangeEntity terminalVersionUpgradeRangeEntity = new TerminalVersionUpgradeRangeEntity();
			String name = stringObjectMap.get(GlobalVariableKey.DICT_LABEL) == null ? "" : stringObjectMap.get(GlobalVariableKey.DICT_LABEL).toString();
			terminalVersionUpgradeRangeEntity.setUpgradeRangeName(name);
			String value = stringObjectMap.get(GlobalVariableKey.DICT_VALUE) == null ? "" : stringObjectMap.get(GlobalVariableKey.DICT_VALUE) .toString();
			terminalVersionUpgradeRangeEntity.setUpgradeRangeValue(value);
			result.add(terminalVersionUpgradeRangeEntity);
		});
		return result;
	}

}
