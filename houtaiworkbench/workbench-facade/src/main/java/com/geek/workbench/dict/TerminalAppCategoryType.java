package com.geek.workbench.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 * 应用类型
 *
 * @author liuke
 * @date  2021/9/26 15:13
 * @version
*/
@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("terminalAppCategoryType")
public enum TerminalAppCategoryType implements AppRuntimeDict {
    me("我的"),
    all("全部"),
    routine("常规"),
    regional("本地");
    public String remark;

}
