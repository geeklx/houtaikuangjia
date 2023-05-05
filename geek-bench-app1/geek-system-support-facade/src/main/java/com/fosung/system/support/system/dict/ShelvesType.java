package com.fosung.system.support.system.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 描述：
 *
 * @author 付昊
 * @date 2021/12/6 9:22
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("shelvesType")
public enum ShelvesType implements AppRuntimeDict {
    resource("资源授权"),role("角色授权");

    public String remark;
}
