package com.fosung.system.pbs.dto.support.cloudapp;

import lombok.Data;

/**
 * @Desc
 * @Author zyy
 * @Date 2021/9/14 18:11
 */
@Data
public class CloudAppRole {

    /**
     * 应用id
     */
    private Long appId;

    /**
     * 角色id
     */
    private Long id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色code
     */
    private String code;

    /**
     * 人员id
     */
    private String userId;

    /**
     * 人员姓名
     */
    private String realName;
}
