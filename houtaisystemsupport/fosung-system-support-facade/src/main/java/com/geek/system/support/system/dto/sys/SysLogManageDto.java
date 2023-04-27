package com.geek.system.support.system.dto.sys;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import com.geek.system.support.system.dict.OptLogType;
import com.geek.system.support.system.util.ProjectConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

/**
 * 描述：
 *
 * @author 付昊
 * @date 2022/1/13 15:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysLogManageDto  extends AppBasePageParam {

    // 系统模块
    private String optModule;

    // 操作人
    private String operName;

    private String operNameEQ;

    // 操作类型
    @Enumerated(EnumType.STRING)
    private OptLogType optType;

    // 状态
    private Boolean status;

    /*
     * 描述: 开始时间
     * @author fuhao
     **/
    @DateTimeFormat(pattern= ProjectConstant.FORMAT_SECOND)
    private Date startTime;

    /*
     * 描述: 结束时间
     * @author fuhao
     **/
    @DateTimeFormat(pattern= ProjectConstant.FORMAT_SECOND)
    private Date endTime;
}
