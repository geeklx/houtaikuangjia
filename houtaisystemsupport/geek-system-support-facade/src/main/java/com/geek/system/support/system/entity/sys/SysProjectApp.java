package com.geek.system.support.system.entity.sys;

import java.util.List;
import java.util.Map;

import com.geek.system.support.system.dict.AppType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;

/**
 * 应用项目关联表实体对象
 */
@Entity
@Table(name="sys_project_app")
@Setter
@Getter
public class SysProjectApp extends AppJpaBaseEntity  {

	/**
	 * 租户id
	 */
	@Column(name="project_id")
	private Long projectId ;

	/**
	 * 描述: 租户名称
	 * @author fuhao
	 * @date 2021/12/31 10:16
	 **/
	@Transient
	private String projectName;


	/**
	 * 应用id
	 */
	@Column(name="app_id")
	private Long appId ;

	/**
	 * 分类编码
	 */
	@Column(name="category_code")
	private String categoryCode ;

	/**
	 * 分类编码
	 */
	@Column(name="category_name")
	private String categoryName ;

	/**
	 * 描述: 应用名称
	 * @author fuhao
	 * @date 2021/12/15 14:56
	 **/
	@Transient
	private String appName;

	/**
	 * 描述: 是否选中
	 * @author fuhao
	 * @date 2021/12/15 14:57
	 **/
	@Transient
	private Boolean checkFlag;

	/**
	 * 描述: 应用下关联的资源
	 * @author fuhao
	 * @date 2021/12/3 8:53
	 **/
	@Transient
	private List<Map<String, Object>> roleResources;

	@Transient
	@Enumerated(EnumType.STRING)
	private AppType appType;

}