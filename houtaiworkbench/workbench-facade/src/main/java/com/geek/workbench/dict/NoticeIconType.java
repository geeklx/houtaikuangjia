package com.geek.workbench.dict;

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
@AppDict("noticeIconType")
public enum NoticeIconType implements AppRuntimeDict{
    idefault( "默认"),
    icustom( "自定义");

    public String remark;
    }


