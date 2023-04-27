package com.geek.workbench.dict;

import com.fosung.framework.common.support.anno.AppDict;

/**
 * 　　* @description: TODO
 * 　　* @param
 * 　　* @return
 * 　　* @throws
 * 　　* @author lihuiming
 * 　　* @date $ $
 *
 */

@AppDict("cityLevel")
public enum CityLevel   {
    province("1","省"),
    city("2","城市"),
    regional("3","地域类型"),
    street("4","街道");
    private String value;

    private String text;

    CityLevel(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
