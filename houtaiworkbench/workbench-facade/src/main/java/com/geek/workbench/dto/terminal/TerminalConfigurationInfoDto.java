package com.geek.workbench.dto.terminal;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import com.geek.workbench.entity.microcoder.*;
import com.geek.workbench.entity.microcoder.TerminalConfigAgreementEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 终端基础配置dto
 *
 * @author fuhao
 * @version V1.0
 * @date 2021/10/9 10:56
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TerminalConfigurationInfoDto extends AppBasePageParam {


    private TerminalConfigAgreementEntity agreementInfo;

}
