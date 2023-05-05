package com.fosung.system.support.system.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("authtype")
public enum AuthType implements AppRuntimeDict{

    authorization_code("授权码模式"),password("密码模式"),client_credentials("凭证模式"),refresh_token("刷新token");

    public String remark;
}
