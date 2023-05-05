package com.fosung.workbench.dto.terminal;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import com.fosung.workbench.entity.microcoder.*;
import lombok.Data;

/**
 * 终端基础配置dto
 *
 * @author fuhao
 * @version V1.0
 * @date 2021/10/9 10:56
 */
@Data
public class TerminalConfigurationInfoDto extends AppBasePageParam {


    private TerminalConfigAgreementEntity agreementInfo;

}
