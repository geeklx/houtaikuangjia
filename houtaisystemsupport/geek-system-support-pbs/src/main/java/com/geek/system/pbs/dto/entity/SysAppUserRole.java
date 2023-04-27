package com.geek.system.pbs.dto.entity;

import com.fosung.framework.common.config.AppConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
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