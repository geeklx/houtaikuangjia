package com.geek.system.pbs.dto.entity;

import com.fosung.framework.common.support.AppRuntimeDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum AppAuthType implements AppRuntimeDict{

    authorization_code("授权码模式"),password("密码模式"),client_credentials("凭证模式"),refresh_token("刷新token");

    public String remark;
}
