package com.geek.workbench.entity.microcoder;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;
import com.geek.workbench.dict.AssociationType;
import com.geek.workbench.dict.ShowStyleType;
import com.geek.workbench.dict.StatusType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 终端导航配置实体对象
 */
@Entity
@Table(name="terminal_config_navigation_btm")
@Setter
@Getter
public class TerminalConfigNavigationBtmEntity extends AppJpaBaseEntity implements AppJpaSoftDelEntity  {
	/**
     * 是否删除
     */
    @Column
    private Boolean del = Boolean.FALSE ;

	/**
	 * 导航名称
	 */
	@Column(name="navigation_name")
	@NotBlank(message = "导航名称不能为空")
	@Size(min = 0, max = 16, message = "导航名称长度不能超过16个字符")
	private String navigationName ;


	/**
	 * 导航分类
	 */
//	@Column(name="navigation_category")
//	@Enumerated(EnumType.STRING)
//	private NavigationType navigationCategory ;

	@Column(name="show_style")
	@Enumerated(EnumType.STRING)
	private ShowStyleType showStyle;

	/***
	 * 描述: 默认显示
	 * @author fuhao
	 * @date 2021/11/6 16:01
	 **/
	@Column(name = "default_show")
	private Boolean defaultShow;

	/**
	 * 链接地址
	 */
	@Column(name="navigation_url")
	private String navigationUrl ;

	/***
	 * 描述: 关联对象
	 * @author fuhao
	 * @date 2021/11/5 16:03
	 **/
	@Column(name = "associated_object")
	@Enumerated(EnumType.STRING)
	private AssociationType associatedObject;


	/**
	 * 导航图标（选中）
	 */
	@Column(name="navigation_logo_checked")
	private String navigationLogoChecked ;


	/**
	 * 导航图标（未选中）
	 */
	@Column(name="navigation_logo_no_checked")
	private String navigationLogoNoChecked ;



	/**
	 * 排序
	 */
	@Column(name="num")
	private Integer num ;


	/**
	 * 状态
	 */
	@Column(name="status")
	@Enumerated(EnumType.STRING)
	private StatusType status ;


	/**
	 * 终端id
	 */
	@Column(name="terminal_id")
	@NotNull(message = "终端主键不能为空")
	private Long terminalId ;

	/**
	 * 是否强制登录
	 */
	@Column(name = "force_login")
	private String forceLogin;


	/**
	 * 描述: 地域
	 * @author fuhao
	 * @date 2021/11/8 10:00
	 **/
	@Column(name = "area")
	private String area;

	/**
	 * 描述: Integer 类型自增主键
	 **/
	@Column(name = "int_id" ,insertable = false , updatable = false)
	private Integer intId;

	@Transient
	@Enumerated(EnumType.STRING)
	private AssociationType oldAssociatedObject;

	@Transient
	private String oldNavigationUrl;

}