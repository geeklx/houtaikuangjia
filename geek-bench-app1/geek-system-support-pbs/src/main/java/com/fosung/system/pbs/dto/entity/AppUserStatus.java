package com.fosung.system.pbs.dto.entity;

import com.fosung.framework.common.support.AppRuntimeDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum AppUserStatus implements AppRuntimeDict{

    VALID("有效"),INVALID("无效");

    public String remark;
}
