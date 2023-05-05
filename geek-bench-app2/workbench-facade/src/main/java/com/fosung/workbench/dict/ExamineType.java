package com.fosung.workbench.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 * 审核状态
 *
 * @author liuke
 * @date  2021/9/26 15:13
 * @version
*/
@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("examineType")
public enum ExamineType implements AppRuntimeDict {
    DRAFT("未提交"), TO_AUDIT("待审核"), OFF_AUDIT("已审核"), AUDIT_REFUSED("审核不通过");
    public String remark;

}
