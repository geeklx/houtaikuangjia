package com.geek.system.support.mq;

import com.fosung.framework.common.support.AppRuntimeDict;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Desc
 * @Author zyy
 * @Date 2020/12/29 18:32
 */
@Getter
@AllArgsConstructor
public enum BusinessSourceEnum implements AppRuntimeDict {

    dt("灯塔党建系统"),
    PEOPLE("村居管理系统"),
    CADRE("干部管理系统"),
    SSO("用户信息库系统");

    public String remark;
}
