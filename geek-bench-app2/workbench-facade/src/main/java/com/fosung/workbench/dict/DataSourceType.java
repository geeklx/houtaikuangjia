package com.fosung.workbench.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Description 数据源类型
 * @Author gaojian
 * @Date 2021/10/18 15:52
 * @Version V1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("dataSourceType")
public enum DataSourceType implements AppRuntimeDict {
    workbench("统一工作台"), unicomCenter("联通中心");
    public String remark;
}
