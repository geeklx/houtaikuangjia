package com.geek.workbench.entity.microcoder;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;
import com.geek.workbench.dict.MenuType;
import com.geek.workbench.dict.StatusType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 描述:  顶部导航栏表实体对象
 * @createDate: 2021/11/04 10:25
 * @author: gaojian
 */
@Entity
@Table(name="terminal_config_menu_top")
@Setter
@Getter
public class TerminalConfigMenuTopEntity extends AppJpaBaseEntity implements AppJpaSoftDelEntity  {
	/**
     * 是否删除
     */
    @Column
    private Boolean del = Boolean.FALSE ;

	/**
	 * 图标路径
	 */
	@Column(name="img")
	private String img ;


	/**
	 * 名称
	 */
	@Column(name="name")
	@NotBlank(message = "栏目名称不能为空")
	@Size(min = 0, max = 255, message = "栏目名称长度不能超过255个字符")
	private String name ;


	/**
	 * 地址
	 */
	@Column(name="url")
	private String url ;


	/**
	 * 是否启用
	 */
	@Column(name="status")
	@Enumerated(EnumType.STRING)
	private StatusType status;


	/**
	 * 终端id
	 */
	@Column(name="terminal_id")
	@NotNull(message = "终端主键不能为空")
	private Long terminalId ;


	/**
	 * 父id
	 */
	@Column(name="parent_id")
	private Long parentId ;


	/**
	 * 地域
	 */
	@Column(name="area")
	@NotBlank(message = "地域名称不能为空")
	@Size(min = 0, max = 255, message = "地域名称长度不能超过255个字符")
	private String area ;


	/**
	 * 排序
	 */
	@Column(name="num")
	private Integer num ;


	/**
	 * 底部导航栏配置ID
	 */
	@Column(name="navigation_btm_id")
	private Long navigationBtmId ;

	/**
	 * 导航id
	 */
	@Column(name="navigation_id")
	private Long navigationId ;

	/**
	 * 菜单类型
	 */
	@Column(name="menu_type")
	@Enumerated(EnumType.STRING)
	private MenuType menuType ;

	/**
	 * 描述: 是否末级菜单
	 */
	@Column(name="is_end")
	private Boolean isEnd;

}