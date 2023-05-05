package com.fosung.system.support.system.util;

/**
 * 描述：人员管理
 *
 * @author 付昊
 * @date 2021/11/25 10:39
 */
public interface UserConstant {

    // 格式：年月日时分秒
    String FORMAT_SECOND = "yyyy-MM-dd HH:mm:ss";

    // 格式：年月日
    String FORMAT_DATE = "yyyy-MM-dd";

    // 检查主键
    String CHECK_ID = "用户id不能为空!";

    // 检查项目名称
    String CHECK_ID_CARD = "身份证已被注册!";

    //检查项目编码
    String CHECK_TELEPHONE = "手机号已被注册!";

    // 检查用户名
    String CHECK_USER_NAME = "用户名已被注册!";

    // 身份证信息不合法
    String CHECK_ILLEGAL_ID_CARD = "身份证信息不合法!";

    // 身份证信息合法
    String CHECK_LEGAL_ID_CARD = "身份证信息合法!";


}
