package com.geek.workbench.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 * 应用类型
 *
 * @author liuke
 * @date  2021/9/26 15:13
 * @version
*/
@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("appType")
public enum AppType implements AppRuntimeDict {
    appNative("app原生"),
    android("安卓"),
    ios("IOS"),
    h5("h5");
    public String remark;

}
