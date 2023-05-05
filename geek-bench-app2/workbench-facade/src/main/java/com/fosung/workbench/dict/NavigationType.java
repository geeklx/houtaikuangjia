package com.fosung.workbench.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 描述：导航枚举类
 *
 * @author fuhao
 * @date 2021/11/5 16:04
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("navigationType")
public enum NavigationType implements AppRuntimeDict {

    // 导航类型
    general("普通导航"),

    regional("地域导航");

    public String remark;


}
