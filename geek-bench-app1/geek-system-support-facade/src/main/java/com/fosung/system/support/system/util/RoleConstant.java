package com.fosung.system.support.system.util;

/**
 * 描述：角色管理
 *
 * @author 付昊
 * @date 2021/11/25 10:39
 */
public interface RoleConstant {

    // 检查主键
    String CHECK_ID = "角色id不能为空!";

    // 检查角色名称
    String CHECK_ROLE_NAME = "在此租户下角色名称已存在!";

    //检查角色编码
    String CHECK_ROLE_CODE = "角色编码已存在!";

    // 角色编码前缀
    String ROLE_CODE_PREFIX = "fs-user-role-";

}
