package com.fosung.system.support.system.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("apptype")
public enum AppType implements AppRuntimeDict{

    pcWeb("网页系统"),android("安卓应用"),ios("ios应用"),h5("h5"),pcClient("pc客户端"),BOX("盒子");

    public String remark;
}
