package com.geek.workbench.dto.terminal;

import com.geek.workbench.dict.StatusType;
import com.geek.workbench.dict.TerminalType;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Description
 * @Author gaojian
 * @Date 2021/12/1 14:26
 * @Version V1.0
 */
@Data
@Valid
public class TerminalApplicationQueryDto implements Serializable {

    /**
     * 描述:  包名
     */
    @NotBlank(message = "终端包名不能为空")
    private String terminalPackageName;

    /**
     * 描述:  终端类型
     */
    @NotNull(message = "终端类型不能为空")
    @Enumerated(EnumType.STRING)
    private TerminalType terminalType;

    /**
     * 描述:  应用配置主键
     */
    private Long appConfId;

    /**
     * 描述:  应用状态
     */
    @Enumerated(EnumType.STRING)
    private StatusType appStatus;
}
