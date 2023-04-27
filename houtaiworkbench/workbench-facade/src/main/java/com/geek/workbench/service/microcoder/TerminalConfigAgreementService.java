package com.geek.workbench.service.microcoder;

import java.util.List;

import com.geek.workbench.entity.microcoder.TerminalConfigAgreementEntity;
import com.fosung.framework.common.support.service.AppBaseDataService;

public interface TerminalConfigAgreementService extends AppBaseDataService<TerminalConfigAgreementEntity, Long> {


    List<TerminalConfigAgreementEntity> getTerminalConfigAgreements(Long terminalId);

}

