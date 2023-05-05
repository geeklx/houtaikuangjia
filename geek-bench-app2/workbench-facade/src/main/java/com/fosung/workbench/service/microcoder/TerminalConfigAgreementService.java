package com.fosung.workbench.service.microcoder;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fosung.workbench.entity.microcoder.TerminalConfigAgreementEntity;
import org.springframework.stereotype.Service;
import com.fosung.framework.common.support.service.AppBaseDataService;

public interface TerminalConfigAgreementService extends AppBaseDataService<TerminalConfigAgreementEntity, Long> {


    List<TerminalConfigAgreementEntity> getTerminalConfigAgreements(Long terminalId);

}

