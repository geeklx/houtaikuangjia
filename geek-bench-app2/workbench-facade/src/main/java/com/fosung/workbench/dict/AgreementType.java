package com.fosung.workbench.dict;

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
@AppDict("agreementType")
public enum AgreementType implements AppRuntimeDict {
    user("用户"),
    app("应用"),
    privacy("隐私");
    public String remark;

}
