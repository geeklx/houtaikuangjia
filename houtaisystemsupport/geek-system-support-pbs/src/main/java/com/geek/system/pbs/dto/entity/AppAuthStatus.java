package com.geek.system.pbs.dto.entity;

import com.fosung.framework.common.support.AppRuntimeDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum AppAuthStatus implements AppRuntimeDict {
    AUTH("已激活"),NOT_AUTH("未激活");
    public String remark;

    @Override
    public String getRemark() {
        return remark;
    }
}
