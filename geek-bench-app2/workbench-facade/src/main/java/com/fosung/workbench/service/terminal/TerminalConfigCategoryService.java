package com.fosung.workbench.service.terminal;

import com.fosung.framework.common.support.service.AppBaseDataService;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.workbench.dto.config.AppSearchParamDto;
import com.fosung.workbench.dto.terminal.TerminalCategoryAppDto;
import com.fosung.workbench.entity.terminal.TerminalConfigCategoryEntity;

import java.util.List;

public interface TerminalConfigCategoryService extends AppBaseDataService<TerminalConfigCategoryEntity, Long> {


    /**
     * 功能描述: 最新排序
     *
     * @param terminalConfigCategory
     * @return java.lang.Integer
     * @author fuhao
     * @date 2021/10/21 17:56
     */
	Integer getMaxNum(TerminalConfigCategoryEntity  terminalConfigCategory);

    /**
     * 查询分类绑定跟未绑定的应用
     * @param terminalConfigCategory
     * @return ResponseParam
     * @author fuhao
     * @date 2021/10/21 17:56
     */
    ResponseParam queryCategoryApp(TerminalCategoryAppDto terminalConfigCategory);

    /**
     * 查询分类
     * @param shelvesSearchParamDto
     * @return
     */
    List<TerminalConfigCategoryEntity> getCacheAppByTerminal(AppSearchParamDto shelvesSearchParamDto);

    /**
     * 查询工作台分类
     * @param terminalConfigCategory
     * @return ResponseParam
     * @author fuhao
     * @date 2021/10/21 17:56
     */
    ResponseParam queryCategory(TerminalCategoryAppDto terminalConfigCategory);


    /**
     * 描述: 保存工作台分类信息
     * @param terminalConfigCategory
     * @return void
     * @author fuhao
     * @date 2021/10/28 10:41
     **/
    void saveInfo(TerminalConfigCategoryEntity  terminalConfigCategory);
}

