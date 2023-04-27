package com.geek.system.support.system.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("projectStatus")
public enum ProjectStatus implements AppRuntimeDict {
    test("测试"),pilot("试点"),pro("正式环境");

    public String remark;

}
