package com.geek.workbench.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author gaojian
 * @Date 2021/10/18 21:16
 * @Version V1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("auditStatusType")
public enum  AuditStatusType implements AppRuntimeDict {
    pass("审核通过"), noPass("审核不通过");
    public String remark;
}
