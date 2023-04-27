package com.geek.workbench.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Description 终端查询应用类型
 * @Author gaojian
 * @Date 2021/12/15 8:52
 * @Version V1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("terminalQueryType")
public enum TerminalQueryType implements AppRuntimeDict {

    all("查询全部"),
    shelves("查询授权");
    public String remark;

}