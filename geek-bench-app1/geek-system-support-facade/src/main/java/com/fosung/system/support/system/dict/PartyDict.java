package com.fosung.system.support.system.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("party")
public enum PartyDict implements AppRuntimeDict{

    党员("党员"),群众("群众");

    public String remark;
}
