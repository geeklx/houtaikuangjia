package com.fosung.workbench.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Description 发布方式
 * @Author gaojian
 * @Date 2021/10/25 9:50
 * @Version V1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("publishType")
public enum PublishType implements AppRuntimeDict {
    staticFile("静态文件"), remoteLink("远程链接");
    public String remark;
}
