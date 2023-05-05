package com.fosung.system.support.system.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 描述：
 *
 * @author 付昊
 * @date 2021/12/20 10:26
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("roleSource")
public enum RoleSource  implements AppRuntimeDict {
    user("用户管理"),role("角色管理");
    public String remark;
}
