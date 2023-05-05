package com.fosung.system.support.system.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 目录树类型
 * @author chenxh
 */
@AppDict("treeType")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum TreeType implements AppRuntimeDict {

    ADMIN_TREE("行政目录树"),
    ORG_TREE("组织目录树"),
    OUT_TREE("外部目录树");
    private String remark;
}
