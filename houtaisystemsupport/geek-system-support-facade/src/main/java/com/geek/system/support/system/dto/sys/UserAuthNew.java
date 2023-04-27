package com.geek.system.support.system.dto.sys;

import com.fosung.framework.common.secure.auth.support.UserAuth;
import com.geek.system.support.system.dict.RoleType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 描述：
 *
 * @author 付昊
 * @date 2021/12/21 17:01
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserAuthNew extends UserAuth {
    private Long userId;

    private String appCode;

    private Long roleId;

    private RoleType roleType;
}
