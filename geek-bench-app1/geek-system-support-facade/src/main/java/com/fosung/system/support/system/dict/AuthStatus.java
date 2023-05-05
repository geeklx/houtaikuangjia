package com.fosung.system.support.system.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("auth_status")
public enum AuthStatus implements AppRuntimeDict {
    AUTH("已激活"),NOT_AUTH("未激活");
    public String remark;

    @Override
    public String getRemark() {
        return remark;
    }
}
