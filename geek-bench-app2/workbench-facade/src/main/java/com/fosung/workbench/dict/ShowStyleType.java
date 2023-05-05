package com.fosung.workbench.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 描述：
 *
 * @author 付昊
 * @date 2021/11/6 15:47
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("showStyleType")
public enum ShowStyleType implements AppRuntimeDict {
    word("文字"),pic("图片"),word_pic("文字+图片");

    private String remark;
}
