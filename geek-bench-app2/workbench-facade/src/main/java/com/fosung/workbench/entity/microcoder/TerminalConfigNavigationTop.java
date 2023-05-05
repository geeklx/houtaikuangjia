package com.fosung.workbench.entity.microcoder;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;
import com.fosung.workbench.dict.MenuType;
import com.fosung.workbench.dict.StatusType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Description 顶部导航表实体对象
 * @Author gaojian
 * @Date 2021/11/04 10:25
 * @Version V1.0
 */
@Entity
@Table(name="terminal_config_navigation_top")
@Setter
@Getter
public class TerminalConfigNavigationTop extends AppJpaBaseEntity implements AppJpaSoftDelEntity  {

	/**
     * 是否删除
     */
    @Column
    private Boolean del = Boolean.FALSE ;

	/**
	 * 导航名称
	 */
	@NotBlank(message = "导航名称不能为空")
	@Size(min = 0, max = 255, message = "导航名称长度不能超过255个字符")
	@Column(name="navigation_name")
	private String navigationName ;


	/**
	 * 是否启用
	 */
	@Column(name="status")
	@Enumerated(EnumType.STRING)
	private StatusType status;

	/**
	 * 地域
	 */
	@Column(name="area")
	@NotBlank(message = "地域名称不能为空")
	@Size(min = 0, max = 255, message = "地域名称长度不能超过255个字符")
	private String area ;

	/**
	 * 底部导航id
	 */
	@Column(name="navigation_btm_id")
	@NotNull(message = "底部导航主键不能为空")
	private Long navigationBtmId ;

	/**
	 * 终端主键
	 */
	@Column(name="terminal_id")
	@NotNull(message = "终端主键不能为空")
	private Long terminalId;

	/**
	 * 菜单类型
	 */
	@Column(name="menu_type")
    @Enumerated(EnumType.STRING)
	private MenuType menuType ;


}