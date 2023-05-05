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
@AppDict("configType")
public enum ConfigType implements AppRuntimeDict {
    authorized("认证服务"),
    resource("资源服务"),
    ums("消息服务"),
    unisearch("统一搜索"),
    oss("统一存储"),
    immsg("即时通讯");
    public String remark;

}
