package com.fosung.system.support.system.dto.sys;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import com.fosung.system.support.system.dict.RoleType;
import com.fosung.system.support.system.entity.sys.SysRoleScopEntity;
import com.fosung.system.support.system.util.ProjectConstant;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;
import java.util.List;

/**
 * @version V1.0
 * @Description：角色表Dto
 */
@Data
public class SysRoleDto extends AppBasePageParam {
	
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
	@DateTimeFormat(pattern= ProjectConstant.FORMAT_SECOND)
	private Date startTime;

	/*
	 * 描述: 结束时间
	 * @author fuhao
	 * @date 2021/11/25 10:44
	 **/
	@DateTimeFormat(pattern= ProjectConstant.FORMAT_SECOND)
	private Date endTime;

	/**
	 * 描述: 项目id
	 * @author fuhao
	 * @date 2021/11/27 10:11
	 **/
	private Long projectId ;

	/**
	 * 描述: 角色范围
	 * @author fuhao
	 * @date 2021/11/27 11:06
	 **/
	private List<SysRoleScopEntity> roles;
}