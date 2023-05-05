package com.fosung.workbench.service.terminal;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fosung.framework.web.http.ResponseParam;
import com.fosung.workbench.dto.terminal.TerminalCategoryAppSaveDto;
import com.fosung.workbench.entity.terminal.TerminalCategoryAppEntity;
import org.springframework.stereotype.Service;
import com.fosung.framework.common.support.service.AppBaseDataService;

public interface TerminalCategoryAppService extends AppBaseDataService<TerminalCategoryAppEntity, Long> {


    Set<Long> getCacheAppByTerminal(Long terminalId, String code);

    /**
     * 描述: 保存分类绑定应用信息
     * @param terminalCategoryAppSaveDto
     * @return com.fosung.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2021/10/27 16:44
     **/
    ResponseParam saveInfo(TerminalCategoryAppSaveDto terminalCategoryAppSaveDto);
}

