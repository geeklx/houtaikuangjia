package com.fosung.system.pbs.dto.support.cloudapp;

import lombok.Data;

import java.util.List;

/**
 * @Desc
 * @Author zyy
 * @Date 2021/5/31 10:22
 */
@Data
public class CloudAdminParam {

    private String appId;
    /**
     * 组织id
     */
    private List<String> orgId;

    /**
     * 角色id
     */
    private List<String> roleId;

    /**
     * 人员id
     */
    private String userId;
}
