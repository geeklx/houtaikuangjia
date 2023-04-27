package com.geek.system.support.system.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("nation")
public enum NationDict implements AppRuntimeDict{
    汉族("汉族"),
    蒙古族("蒙古族"),
    回族("回族"),
    藏族("藏族"),
    维吾尔族("维吾尔族"),
    苗族("苗族"),
    彝族("彝族"),
    壮族("壮族"),
    布依族("布依族"),
    朝鲜族("朝鲜族"),
    满族("满族"),
    侗族("侗族"),
    瑶族("瑶族"),
    白族("白族"),
    土家族("土家族"),
    哈尼族("哈尼族"),
    哈萨克族("哈萨克族"),
    傣族("傣族"),
    黎族("黎族"),
    傈僳族("傈僳族"),
    佤族("佤族"),
    畲族("畲族"),
    高山族("高山族"),
    拉祜族("拉祜族"),
    水族("水族"),
    东乡族("东乡族"),
    纳西族("纳西族"),
    景颇族("景颇族"),
    柯尔克孜族("柯尔克孜族"),
    土族("土族"),
    达斡尔族("达斡尔族"),
    仫佬族("仫佬族"),
    羌族("羌族"),
    布朗族("布朗族"),
    撒拉族("撒拉族"),
    毛难族("毛难族"),
    仡佬族("仡佬族"),
    锡伯族("锡伯族"),
    阿昌族("阿昌族"),
    普米族("普米族"),
    塔吉克族("塔吉克族"),
    怒族("怒族"),
    乌孜别克族("乌孜别克族"),
    俄罗斯族("俄罗斯族"),
    鄂温克族("鄂温克族"),
    崩龙族("崩龙族"),
    保安族("保安族"),
    裕固族("裕固族"),
    京族("京族"),
    塔塔尔族("塔塔尔族"),
    独龙族("独龙族"),
    鄂伦春族("鄂伦春族"),
    赫哲族("赫哲族"),
    门巴族("门巴族"),
    珞巴族("珞巴族"),
    基诺族("基诺族"),
    其他("其他"),
    外国血统中国人士("外国血统中国人士");

    public String remark;
}
