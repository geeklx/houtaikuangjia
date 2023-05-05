package com.fosung.system.pbs.dto.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 用户表实体对象
 */
@Setter
@Getter
public class SysAppUserRole {

   private String id;

   private String roleName;

   private String roleCode;

   private String roleType;

   private String createTime;

   private List<SysAppUserRoleScop> scopOrg;


}