package com.geek.system.pbs.dto.entity;

import com.fosung.framework.common.config.AppConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 组织机构实体对象
 */
@Setter
@Getter
public class AppSysOrgEntity {

	private Long id;
	/**
     * 是否删除
     */
    private Boolean del = Boolean.FALSE ;

	/**
	 * 机构名称
	 */
	private String orgName ;


	/**
	 * 机构编码
	 */
	private String orgCode ;


	/**
	 * 父节点
	 */
	private Long parentId;

	/**
	 * 父节点名称
	 */
	private String parentName ;


	/**
	 * 排序
	 */
	private Integer num = 0;


	/**
	 * 是否有子节点
	 */
	private Boolean hasChildren ;


	/**
	 * 层级
	 */
	private String level ;




	/**
	 * 组织状态
	 */
	@Enumerated(EnumType.STRING)
	private AppOrgStatus status;


	/**
	 * 组织类型
	 */
	@Enumerated(EnumType.STRING)
	private AppOrgType orgType ;


	/**
	 * 系统id
	 */
	private Long projectId ;

	/**
	 * 项目名称
	 */
	private String projectName;

	/**
	 * 描述
	 */
	private String remark;

	/**
	 * 描述: 是否最下级
	 * @author fuhao
	 * @date 2022/1/18 10:10
	 **/
	private Boolean leaf = Boolean.FALSE;

	/**
	 * 描述: 组织来源code
	 * @author fuhao
	 * @date 2022/1/18 10:11
	 **/
	private String source;

	/**
	 * 描述: 组织来源名称
	 * @author fuhao
	 * @date 2022/1/18 10:11
	 **/
	private String sourceName;

	/**
	 * 描述: 组织地域类型
	 * @author fuhao
	 * @date 2022/1/18 10:11
	 **/
	//@Enumerated(EnumType.STRING)
	private String levelType;

	private Boolean checkedFlag;

	/**
	 * 灯塔组织id
	 */
	private String dtOrgId;

	/**
	 * 灯塔党组织名称
	 */
	private String dtOrgName;

	/**
	 * 灯塔党组织编码
	 */
	private String dtOrgCode;

	private String createUserId ;


	private String createDatetime ;

	private String lastUpdateUserId ;

	private String lastUpdateDatetime ;

}