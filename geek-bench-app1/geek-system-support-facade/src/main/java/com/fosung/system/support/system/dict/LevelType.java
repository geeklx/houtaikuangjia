package com.fosung.system.support.system.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("levelType")
public enum LevelType implements AppRuntimeDict{

    COUNTRY("国家"),
    PROVINCE("省级"),
    CITY("市级"),
    COUNTY("区/县"),
    TOWN("镇/街道"),
    MANAGE_AREA("管区"),
    CITY_VILLAGE("城市/社区"),
    VILLAGE("社区/村居"),
    PARK("园区"),
    VILLAGE_ECONOMIC_PROJECT("村集体经济项目"),
    OTHER("其他");

    public String remark;
}
