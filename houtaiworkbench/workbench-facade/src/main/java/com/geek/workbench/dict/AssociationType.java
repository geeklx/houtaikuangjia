package com.geek.workbench.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 描述：导航枚举类
 *
 * @author fuhao
 * @date 2021/11/5 16:04
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("associationType")
public enum AssociationType implements AppRuntimeDict {

    // 关联模块
    local("本地模块"),

    remote("远程链接");

    public String remark;


}
