package com.fosung.system.support.system.entity.sys;

import com.fosung.system.support.system.dict.OrgStatus;
import com.fosung.system.support.system.dict.OrgType;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;

/**
 * 组织机构实体对象
 */
@Entity
@Table(name="sys_org")
@Setter
@Getter
public class SysOrgEntity extends AppJpaBaseEntity implements AppJpaSoftDelEntity  {

	@Column(name="id")
	private Long id;
	/**
     * 是否删除
     */
    @Column
    private Boolean del = Boolean.FALSE ;

	/**
	 * 机构名称
	 */
	@Column(name="org_name",nullable = false)
	private String orgName ;


	/**
	 * 机构编码
	 */
	@Column(name="org_code",nullable = false,unique = true)
	private String orgCode ;


	/**
	 * 父节点
	 */
	@Column(name="parent_id")
	private Long parentId;

	/**
	 * 父节点名称
	 */
	@Transient
	private String parentName ;


	/**
	 * 排序
	 */
	@Column(name="num",nullable = false)
	private Integer num = 0;


	/**
	 * 是否有子节点
	 */
	@Column(name="has_children" ,nullable = false)
	private Boolean hasChildren ;


	/**
	 * 层级
	 */
	@Column(name="level")
	private String level ;




	/**
	 * 组织状态
	 */
	@Column(name="status",nullable = false)
	@Enumerated(EnumType.STRING)
	private OrgStatus status  = OrgStatus.VALID;


	/**
	 * 组织类型
	 */
	@Column(name="org_type")
	@Enumerated(EnumType.STRING)
	private OrgType orgType = OrgType.administration;


	/**
	 * 系统id
	 */
	@Column(name="project_id",nullable = false)
	private Long projectId ;

	/**
	 * 项目名称
	 */
	@Transient
	private String projectName;

	/**
	 * 描述
	 */
	@Column(name = "remark")
	private String remark;

	/**
	 * 描述: 是否最下级
	 * @author fuhao
	 * @date 2022/1/18 10:10
	 **/
	@Column(name = "leaf")
	private Boolean leaf = Boolean.FALSE;

	/**
	 * 描述: 组织来源code
	 * @author fuhao
	 * @date 2022/1/18 10:11
	 **/
	@Column(name = "source")
	private String source;

	/**
	 * 描述: 组织来源名称
	 * @author fuhao
	 * @date 2022/1/18 10:11
	 **/
	@Column(name = "source_name")
	private String sourceName;

	/**
	 * 描述: 组织地域类型
	 * @author fuhao
	 * @date 2022/1/18 10:11
	 **/
	@Column(name = "level_type",nullable = true)
	//@Enumerated(EnumType.STRING)
	private String levelType;

	@Transient
	private Boolean checkedFlag;

	/**
	 * 灯塔组织id
	 */
	@Column(name = "dt_org_id")
	private String dtOrgId;

	/**
	 * 灯塔党组织名称
	 */
	@Column(name = "dt_org_name")
	private String dtOrgName;

	/**
	 * 灯塔党组织编码
	 */
	@Column(name = "dt_org_code")
	private String dtOrgCode;

}