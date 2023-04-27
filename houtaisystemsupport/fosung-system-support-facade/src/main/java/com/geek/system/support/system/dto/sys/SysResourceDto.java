package com.geek.system.support.system.dto.sys;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @version V1.0
 * @Description：资源Dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysResourceDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 父节点id
	 */
	private Long parentId ;
   /**
	 * 名称
	 */
	private String menuName ;
   /**
	 * code
	 */
	private String menuCode ;
   /**
	 * 路由地址
	 */
	private String path ;
   /**
	 * 组件路径
	 */
	private String component ;
   /**
	 * 菜单图标
	 */
	private String icon ;
   /**
	 * 类型 菜单\按钮\模块
	 */
	private String menuType ;
   /**
	 * 排序
	 */
	private Integer num ;
   /**
	 * 应用id
	 */
	private Long appId ;
   /**
	 * 菜单状态（0显示 1隐藏）
	 */
	private Integer status ;
   /**
	 * 权限字符串
	 */
	private String perms ;

	/**
	 *
	 * 面包屑
	 * @param null 
	 * @author liuke
	 * @date 2021/8/10 10:21
	 * @return 
	 */
	private String redirect ;

	private Long userId;

	private String appCode;

	private Long roleId;


}