package com.geek.system.support.system.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("roletype")
public enum RoleType implements AppRuntimeDict{

    USER("普通用户"),ADMIN("管理员"),superadmin("超级管理员");

    public String remark;
}
