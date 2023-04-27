package com.geek.workbench.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Description 广告页、引导页字典
 * @Author gaojian
 * @Date 2021/10/25 18:00
 * @Version V1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("imageType")
public enum ImageType implements AppRuntimeDict {
    advert("广告"), guide("引导");
    public String remark;
}
