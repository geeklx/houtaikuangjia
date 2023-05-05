package com.fosung.workbench.service.terminal;

import com.fosung.framework.common.support.service.AppBaseDataService;
import com.fosung.workbench.entity.terminal.TerminalUpdateLogEntity;
import com.fosung.workbench.entity.terminal.TerminalVersionEntity;

/**
 * 描述:  终端版本更新记录服务
 * @createDate: 2022/2/23 17:20
 * @author: gaojian
 */
public interface TerminalUpdateLogService extends AppBaseDataService<TerminalUpdateLogEntity, Long> {
	

    /**
     * 描述:  保存更改记录
     * @createDate: 2022/1/27 15:25
     * @author: gaojian
     * @modify:
     * @param terminalVersionEntity
     * @return: void
     */
	void saveUpdateLog(TerminalVersionEntity terminalVersionEntity);

	/**
	 * 描述:  删除版本范围更新历史记录
	 * @createDate: 2022/1/27 15:25
	 * @author: gaojian
	 * @modify:
	 * @param terminalVersionEntity
	 * @return: void
	 */
	void deleteHistoryLog(TerminalVersionEntity terminalVersionEntity);

}

