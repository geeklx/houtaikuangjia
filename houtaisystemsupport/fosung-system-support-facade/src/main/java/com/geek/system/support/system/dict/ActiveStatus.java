package com.geek.system.support.system.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 状态
 * @author chenxh
 */
@AppDict("activeStatus")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum ActiveStatus implements AppRuntimeDict {

    UN_ACTIVE("未激活"),
    NON_ACTIVE("已失效"),
    ACTIVE("已激活");
    private String remark;
}
