package com.fosung.workbench.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Description 搜索状态类型
 * @Author gaojian
 * @Date 2021/12/24 9:48
 * @Version V1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("searchStatusType")
public enum SearchStatusType implements AppRuntimeDict {

    enable("有效"),
    disable("无效");

    private String remark;
}
