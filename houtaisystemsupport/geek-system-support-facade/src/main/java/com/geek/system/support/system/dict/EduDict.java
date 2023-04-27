package com.geek.system.support.system.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("edu")
public enum EduDict implements AppRuntimeDict{

    博士研究生("博士研究生"),硕士研究生("硕士研究生"),本科("本科"),专科("专科"),高中("高中"),初中("初中"),小学("小学"),其他("其他");

    public String remark;
}
