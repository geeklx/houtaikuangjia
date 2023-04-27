package com.geek.workbench.service.terminal;

import java.util.List;
import java.util.Map;

import com.fosung.framework.web.http.ResponseParam;
import com.geek.workbench.entity.terminal.TerminalConfigCommonEntity;
import com.fosung.framework.common.support.service.AppBaseDataService;

public interface TerminalConfigCommonService extends AppBaseDataService<TerminalConfigCommonEntity, Long> {
	
    List<TerminalConfigCommonEntity> getAllCommonConfigurationInfo(TerminalConfigCommonEntity TerminalConfigCommonEntity);
	
    void updateConfigValue(TerminalConfigCommonEntity TerminalConfigCommonEntity);

    List<TerminalConfigCommonEntity> getTerminalConfigCommonByTerminalId(Long id);

    ResponseParam saveCommonConfiguration(Map<String,Object> params);
}

