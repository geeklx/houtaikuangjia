package com.fosung.workbench.service.application;

import java.util.List;
import java.util.Map;

import com.fosung.workbench.dto.terminal.TerminalApplicationConfigDto;
import com.fosung.workbench.entity.application.ApplicationBasicEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.fosung.framework.common.support.service.AppBaseDataService;

public interface ApplicationService extends AppBaseDataService<ApplicationBasicEntity, Long> {


    Page<ApplicationBasicEntity> queryByPage(Map<String,Object> searchParam, Pageable pageable);

    ApplicationBasicEntity getAppDetail(Long id);

    Page<ApplicationBasicEntity> queryNewApp(TerminalApplicationConfigDto terminalApplicationConfigDto, Pageable pageable);

    List<ApplicationBasicEntity> queryCategory(TerminalApplicationConfigDto terminalApplicationConfigDto);

}

