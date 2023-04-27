package com.geek.system.pbs.dto.support.cloudapp;

import lombok.Data;

import javax.persistence.Column;

/**
 * @Description: TODO
 * @Author byb
 * @Date 2020/3/3 21:09
 * @Version V1.0
 **/
@Data
public class CloudAppRoleAdmin {

    /**
     * 应用id
     */
    private Long appId;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 人员id
     */
    private String userId;

    /**
     * 管理员姓名
     */
    private String realName;

    /**
     * 管理员手机号
     */
    private String telephone;

    /**
     * 管理员激活状态
     */
    private String authStatus ;

    /**
     * 管理范围id
     */
    private String controlScopeId;

    /**
     * 管理范围代码
     */
    private String controlScopeCode ;

    /**
     * 管理范围名称
     */
    private String controlScopeName ;

    /**
     * 管理范围上级id
     */
    private String controlScopeParentId ;
}
