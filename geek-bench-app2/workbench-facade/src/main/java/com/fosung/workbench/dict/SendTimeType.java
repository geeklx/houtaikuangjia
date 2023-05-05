package com.fosung.workbench.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 　　* @description: 公告类型
 * 　　* @param
 * 　　* @return
 * 　　* @throws
 * 　　* @author lihuiming
 * 　　* @date $ $
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("sendTimeType")
public enum SendTimeType implements AppRuntimeDict{
    immediately( "立即发送"),
    timing( "定时发送");

    public String remark;
    }


