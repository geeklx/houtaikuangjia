package com.geek.workbench.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 描述:  统一消息消息类型
 * @createDate: 2022/2/22 15:47
 * @author: gaojian
 * @modify:
 * @return:
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("umsMessageType")
public enum UmsMessageType implements AppRuntimeDict {

    login("登录"),
    loginout("登出"),
    notice("公告"),
    upgrade("升级"),
    busreturn("业务返回"),
    report("上报信息"),
    detail("详情");
    public String remark;

}
