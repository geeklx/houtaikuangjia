package com.geek.system.pbs.dto.entity;

import com.fosung.framework.common.support.AppRuntimeDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum AppOrgType implements AppRuntimeDict{

    party("党组织"),administration("行政组织");

    public String remark;
}
