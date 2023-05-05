package com.fosung.workbench.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Description 搜索操作类型
 * @Author gaojian
 * @Date 2021/12/24 9:48
 * @Version V1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("searchOperateType")
public enum SearchOperateType implements AppRuntimeDict {

    add("新增"),
    upd("修改"),
    del("删除");

    private String remark;
}
