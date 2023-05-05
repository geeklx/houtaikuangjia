package com.fosung.workbench.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 终端类型描述
 *
 * @author fuhao
 * @version V1.0
 * @date 2021/10/9 13:59
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("terminalType")
public enum TerminalType implements AppRuntimeDict {
    android("安卓"), ios("IOS"), h5("h5"),pcClient("pc客户端"),pcWeb("pc网页");
    public String remark;

}
