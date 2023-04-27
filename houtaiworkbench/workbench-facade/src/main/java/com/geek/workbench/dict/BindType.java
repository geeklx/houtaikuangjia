package com.geek.workbench.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author gaojian
 * @Date 2021/10/25 9:22
 * @Version V1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("bindType")
public enum BindType implements AppRuntimeDict {

    organ("组织目录树"),
    administration("行政目录树"),
    contacts("通讯录类型"),
    regional("地域类型"),
    personnel("人员类型");
    public String remark;
}
