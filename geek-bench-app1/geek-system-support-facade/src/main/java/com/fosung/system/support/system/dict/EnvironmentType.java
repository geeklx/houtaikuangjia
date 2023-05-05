package com.fosung.system.support.system.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("environmentType")
public enum EnvironmentType implements AppRuntimeDict{

    def("默认配置"),pro("生产环境"),pre("预发布环境"),test("测试环境"),dev("开发环境");

    public String remark;

}
