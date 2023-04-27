package com.geek.workbench.dto.terminal;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @version V1.0
 * @Description：终端应用选择配置Dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TerminalApplicationBindDto extends AppBasePageParam {

    /**
     * id
     */
    private Long id;

    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 终端id
     */
    private Long terminalId;
    /**
     * 专区id
     */
    private Long zoneId;
    /**
     * 应用id
     */
    private Long appId;

    /**
     * 发布状态
     */
    private String status;

    /**
     * 应用版本id
     */
    private Long appVersionId;

    /**
     * 终端应用配置id
     */
    private Long appConfigId;

}