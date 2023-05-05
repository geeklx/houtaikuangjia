package com.fosung.system.support.system.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("roleStatus")
public enum RoleStatus implements AppRuntimeDict {
    normal("正常"),stop("停用");

    public String remark;

}
