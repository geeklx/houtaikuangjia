package com.fosung.system.support.system.dict;


import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("optLogType")
public enum OptLogType implements AppRuntimeDict {

    ADD("新增"),

    UPDATE("修改"),

    SAVE("保存"),

    DELETE("删除"),

    BATCH_ENABLED("批量启用/禁用"),

    IMPORT("导入"),

    EXPORT("导出");

    public String remark;
}