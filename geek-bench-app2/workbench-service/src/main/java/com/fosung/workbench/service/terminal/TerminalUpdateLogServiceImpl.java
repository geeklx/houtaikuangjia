package com.fosung.workbench.service.terminal;

import java.util.*;

import com.fosung.workbench.common.GlobalVariableKey;
import com.fosung.workbench.dao.terminal.TerminalUpdateLogDao;
import com.fosung.workbench.dict.RangeType;
import com.fosung.workbench.dict.StatusType;
import com.fosung.workbench.dto.application.ApplicationQueryDto;
import com.fosung.workbench.entity.terminal.TerminalUpdateLogEntity;
import com.fosung.workbench.entity.terminal.TerminalVersionEntity;
import com.fosung.workbench.entity.terminal.TerminalVersionUpgradeRangeEntity;
import com.fosung.workbench.service.application.ApplicationQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;

@Service
public class TerminalUpdateLogServiceImpl extends AppJPABaseDataServiceImpl<TerminalUpdateLogEntity, TerminalUpdateLogDao>
	implements TerminalUpdateLogService {

	@Autowired
	private TerminalUpdateLogService updateLogService;

	@Autowired
	private TerminalVersionUpgradeRangeService upgradeRangeService;

	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("terminalVersionId","terminalVersionId:EQ");
				put("del","del:BOOLEANQE");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}


	/**
	 * 描述:  保存更改记录
	 *
	 * @param terminalVersionEntity
	 * @createDate: 2022年2月23日17:32:47
	 * @author: gaojian
	 * @modify:
	 * @return: void
	 */
	@Override
	public void saveUpdateLog(TerminalVersionEntity terminalVersionEntity) {

		// 1. 获取当前应用范围
		Map<String,Object> searchParams = new HashMap<>(8);
		searchParams.put("terminalVersionId",terminalVersionEntity.getId());
		List<TerminalVersionUpgradeRangeEntity> upgradeRangeValues = upgradeRangeService.queryAll(searchParams);

		// 2. 如果当前更新范围为空或updateIndex为1则证明是首次发布
		if (StatusType.noRelease.equals(terminalVersionEntity.getStatus())){
			realSaveUpdateLog(terminalVersionEntity.getId(),"首次发布");
			return;
		}

		// 3、获取上一次发布状态历史应用范围
		List<TerminalVersionUpgradeRangeEntity> updateResultList = queryUpgradeRange(terminalVersionEntity);
		updateResultList = updateResultList == null ? new ArrayList<>() : updateResultList;
		String content = "修改[更新范围]——";
		String contentInsert = "新增：";
		String contentDel = "删除：";
		Integer insertNum = 0;
		Integer delNum = 0;

		// 4. 判断有无新增范围
		for (TerminalVersionUpgradeRangeEntity upgradeRange : upgradeRangeValues) {
			boolean flag = true;
			for (TerminalVersionUpgradeRangeEntity update : updateResultList) {
				if (upgradeRange.getUpgradeRangeValue().equals(update.getUpgradeRangeValue())) {
					flag = false;
					break;
				}
			}
			if (flag) {
				contentInsert += upgradeRange.getUpgradeRangeName();
				insertNum++;
			}
		}

		// 5. 判断有无删除范围
		for (TerminalVersionUpgradeRangeEntity upgradeRange : updateResultList) {
			boolean flag = true;
			for (TerminalVersionUpgradeRangeEntity update : upgradeRangeValues) {
				if (upgradeRange.getUpgradeRangeValue().equals(update.getUpgradeRangeValue())) {
					flag = false;
					break;
				}
			}
			if (flag) {
				contentDel += upgradeRange.getUpgradeRangeName();
				delNum++;
			}
		}

//		// 获取当前版本范围和上一个版本范围的差集
//		List<TerminalVersionUpgradeRangeEntity> InsertDifferentList = currentUpgradeRangeValues.stream().filter((item) ->
//				!previousUpgradeRangeValues.stream().map((item2) ->
//						item2.getUpgradeRangeName()).collect(Collectors.toList()).contains(item.getUpgradeRangeName())).collect(Collectors.toList());
//
//		List<TerminalVersionUpgradeRangeEntity> delDifferentList = previousUpgradeRangeValues.stream().filter((item) ->
//				!currentUpgradeRangeValues.stream().map((item2) ->
//						item2.getUpgradeRangeName()).collect(Collectors.toList()).contains(item.getUpgradeRangeName())).collect(Collectors.toList());

		// 6. 拼装发布内容
		if (insertNum != 0) {
			content += contentInsert + ",";
		}
		if (delNum != 0) {
			content += contentDel + ",";
		}

		// 7. 保存发布记录
		if (insertNum != 0 || delNum != 0) {
			realSaveUpdateLog(terminalVersionEntity.getId(), content.substring(0, content.length() - 1));
		}else{
			realSaveUpdateLog(terminalVersionEntity.getId(),"发布");
		}
	}

	/**
	 * 描述:  删除此版本历史范围记录
	 * @createDate: 2022/2/23 17:10
	 * @author: gaojian
	 * @modify:
	 * @param terminalVersionEntity
	 * @return: void
	 */
	@Override
	public void deleteHistoryLog(TerminalVersionEntity terminalVersionEntity) {

		// 1. 查询版本当前范围
		Map<String,Object> searchParams = new HashMap<>(8);
		searchParams.put("terminalVersionId",terminalVersionEntity.getId());
		List<TerminalVersionUpgradeRangeEntity> upgradeRangeValues = upgradeRangeService.queryAll(searchParams);

		// 2. 将版本当前范围设置为初始范围
		for( TerminalVersionUpgradeRangeEntity terminalVersionUpgradeRangeEntity : upgradeRangeValues){
			terminalVersionUpgradeRangeEntity.setUpdateIndex(1);
		}
		upgradeRangeService.update(upgradeRangeValues,Arrays.asList("updateIndex"));

		// 3. 删除此版本历史范围修改记录
		upgradeRangeService.deleteHistoryLog(terminalVersionEntity.getId());
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
		Map<String,Object> search = new HashMap<>(16);
		search.put("terminalVersionId",versionId);
		search.put("updateIndex",1);
		List<TerminalVersionUpgradeRangeEntity> upgradeRangeList = upgradeRangeService.queryRealAll(search);
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
	public void realSaveUpdateLog(Long terminalVersionId,String content){
		TerminalUpdateLogEntity terminalUpdateLogEntity = new TerminalUpdateLogEntity();
		terminalUpdateLogEntity.setTerminalVersionId(terminalVersionId);
		terminalUpdateLogEntity.setUpdateContent(content);
		updateLogService.save(terminalUpdateLogEntity);
	}
}
