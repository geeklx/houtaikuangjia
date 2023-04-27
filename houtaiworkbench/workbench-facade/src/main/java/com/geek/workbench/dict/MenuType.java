package com.geek.workbench.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author gaojian
 * @Date 2021/11/8 9:42
 * @Version V1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("menuType")
public enum  MenuType implements AppRuntimeDict {
    custom("自定义"), external("外部");
    public String remark;
}
