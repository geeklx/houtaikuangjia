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
@AppDict("noticeStyle")
public enum NoticeStyle implements AppRuntimeDict{
        titleContent( "标题+正文"),
        titleSummary( "标题+摘要");

    public String remark;
    }


