package com.geek.workbench.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;

/**
 * 　　* @description: 绑定类型
 * 　　* @param
 * 　　* @return
 * 　　* @throws
 * 　　* @author lihuiming
 * 　　* @date $ $
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("bindCategoryType")
public enum BindCategoryType implements AppRuntimeDict {
    directoryTree("目录树"),
    contacts("通讯录"),
    regional("地域"),
    personnel("人员");
    public String remark;
}
