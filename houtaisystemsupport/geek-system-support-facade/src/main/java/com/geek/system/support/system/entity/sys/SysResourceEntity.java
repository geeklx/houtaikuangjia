package com.geek.system.support.system.entity.sys;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.config.AppProperties;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;

/**
 * 资源实体对象
 */
@Entity
@Table(name="sys_resource")
@Setter
@Getter
public class SysResourceEntity extends AppJpaBaseEntity    {

	@Column
	private Long id;


	/**
	 * 父节点id
	 */
	@Column(name="parent_id")
	private Long parentId = 0L;

	/**
	 * 父节点
	 */
	@Transient
	private String parentName ;


	/**
	 * 名称
	 */
	@Column(name="menu_name",nullable = false)
	private String menuName ;


	/**
	 * code
	 */
	@Column(name="menu_code",nullable = false,unique = true)
	private String menuCode ;


	/**
	 * 路由地址
	 */
	@Column(name="path")
	private String path ;


	/**
	 * 组件路径
	 */
	@Column(name="component")
	private String component ;




	/**
	 * 菜单图标
	 */
	@Column(name="icon")
	private String icon ;


	/**
	 * 类型 菜单\按钮\模块
	 */
	@Column(name="menu_type",nullable = false)
	private String menuType ;


	/**
	 * 排序
	 */
	@Column(name="num",nullable = false)
	private Integer num ;


	/**
	 * 应用id
	 */
	@Column(name="app_id",nullable = false)
	private Long appId ;

	/**
	 * 应用名称
	 */
	@Transient
	private String appName;


	/**
	 * 菜单状态（0显示 1隐藏）
	 */
	@Column(name="status",nullable = false)
	private Integer status ;


	/**
	 * 权限字符串
	 */
	@Column(name="perms")
	private String perms ;

	/**
	 * 面包屑
	 */
	@Column(name="redirect")
	private String redirect ;

	/*
	 * 描述: 是否显示面包屑
	 * @author fuhao
	 * @date 2021/11/29 15:24
	 **/
	@Column(name = "show_redirect")
	private String showRedirect;

	/**
	 * 描述: 是否选中
	 * @author fuhao
	 * @date 2021/11/29 16:38
	 **/
	@Transient
	private Boolean checkFlag;

	/**
	 * 描述: 层级
	 * @author fuhao
	 * @date 2021/12/14 17:09
	 **/
	@Column(name = "level")
	private Integer level;
}