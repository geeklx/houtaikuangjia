package com.fosung.system.support.system.util;

/**
 * 描述：项目管理
 *
 * @author 付昊
 * @date 2021/11/25 10:39
 */
public interface ProjectConstant {

    // 格式：年月日时分秒
    String FORMAT_SECOND = "yyyy-MM-dd HH:mm:ss";

    // 格式：年月日
    String FORMAT_DATE = "yyyy-MM-dd";

    // 检查主键
    String CHECK_ID = "租户ID不能为空!";

    // 检查项目名称
    String CHECK_PROJECT_NAME = "租户名称已存在!";

    //检查项目编码
    String CHECK_PROJECT_CODE = "租户编码已存在!";

    // 项目编码前缀
    String PROJECT_CODE_PREFIX = "fs-user-project-";

}
