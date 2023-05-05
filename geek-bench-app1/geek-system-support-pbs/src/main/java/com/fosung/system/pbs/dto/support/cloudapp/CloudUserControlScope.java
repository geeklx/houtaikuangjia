package com.fosung.system.pbs.dto.support.cloudapp;

import lombok.Data;

/**
 * @Description: TODO
 * @Author byb
 * @Date 2019/12/6 9:36
 * @Version V1.0
 **/
@Data
public class CloudUserControlScope {

    /*角色ID*/
    private String roleCode;
    /*角色名称*/
    private String roleName;

    /*组织结构id*/
    private String orgId;

    // 角色类型 USER：普通用户角色  ADMIN：管理员角色
    private String roleType;

}
