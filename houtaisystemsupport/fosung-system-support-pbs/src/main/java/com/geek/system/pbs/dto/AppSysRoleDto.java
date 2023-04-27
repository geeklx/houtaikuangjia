package com.geek.system.pbs.dto;

import com.fosung.framework.common.config.AppConstants;
import com.fosung.framework.common.dto.params.AppBasePageParam;
import com.geek.system.pbs.dto.entity.RoleType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;
import java.util.List;

/**
 * @version V1.0
 * @Description：角色表Dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AppSysRoleDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 角色名
	 */
	private String roleName ;
   /**
	 * 角色编码
	 */
	private String roleCode ;
   /**
	 * 角色描述
	 */
	private String remark ;
   /**
	 * 帐号状态（0正常 1删除）
	 */
	private Boolean del ;
   /**
	 * 父节点
	 */
	private Long parentId ;
   /**
	 *   角色状态（0正常 1停用）
	 */
	private String status ;
   /**
	 * 系统id
	 */
	private String appId ;
   /**
	 * 层级
	 */
	private String level ;
   /**
	 * 排序
	 */
	private String num ;

	/***
	 * 描述: 角色类型
	 * @author fuhao
	 * @date 2021/11/24 15:18
	 **/
	@Enumerated(EnumType.STRING)
	private RoleType roleType;

	/*
	 * 描述: 开始时间
	 * @author fuhao
	 * @date 2021/11/25 10:44
	 **/
	private String startTime;

	/*
	 * 描述: 结束时间
	 * @author fuhao
	 * @date 2021/11/25 10:44
	 **/
	private String endTime;

	/**
	 * 描述: 项目id
	 * @author fuhao
	 * @date 2021/11/27 10:11
	 **/
	private Long projectId ;

}