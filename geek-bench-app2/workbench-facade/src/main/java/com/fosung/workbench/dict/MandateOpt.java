package com.fosung.workbench.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Description 授权类型操作
 * @Author gaojian
 * @Date 2021/10/15 9:37
 * @Version V1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("mandateOpt")
public enum MandateOpt implements AppRuntimeDict {
    DELETE("删除"), ADD("新增");
    public String remark;

}