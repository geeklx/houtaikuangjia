package com.fosung.workbench.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Description 终端公共配置类型字典
 * @Author gaojian
 * @Date 2021/10/15 10:10
 * @Version V1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("terminalCommonConfigType")
public enum  TerminalCommonConfigType implements AppRuntimeDict {

    short_style("短期样式"),
    authorization("授权配置"),
    language("语言配置"),
    theme("主题配置"),
    adverts("广告页配置"),
    share("分享也配置"),
    phone("电话");
    public String remark;

}