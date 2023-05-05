package com.fosung.system.support.system.entity.sys;

import com.fosung.system.support.system.anno.SysDict;
import com.fosung.system.support.system.dict.PostStatus;
import com.fosung.system.support.system.util.Constant;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;

/**
 * 岗位表实体对象
 */
@Entity
@Table(name="sys_post")
@Setter
@Getter
public class SysPostEntity extends AppJpaBaseEntity implements AppJpaSoftDelEntity   {

	/**
	 * 是否删除
	 */
	@Column
	private Boolean del = Boolean.FALSE ;

	/**
	 * 岗位名称
	 */
	@Column(name="post_name",nullable = false)
	private String postName ;


	/**
	 * 岗位编码
	 */
	@Column(name="post_code",nullable = false)
	private String postCode ;


	/**
	 * 排序
	 */
	@Column(name="num",nullable = false)
	private Integer num ;


	/**
	 * 状态（0正常 1停用）
	 */
	@Column(name="status",nullable = false)
	@Enumerated(EnumType.STRING)
	private PostStatus status = PostStatus.normal;


	/**
	 * 备注
	 */
	@Column(name="remark")
	private String remark ;


	/**
	 * 租户id
	 */
	@Column(name="project_id",nullable = false)
	private Long projectId ;

	/**
	 * 描述: 租户名称
	 * @author fuhao
	 * @date 2022/2/8 14:41
	 **/
	@Transient
	private String projectName;

	/**
	 * 描述: 岗位类型
	 * @author fuhao
	 * @date 2022/2/8 14:43
	 **/
	@Column(name="post_type")
	private String postType;

	/**
	 * 描述: 身份属性
	 * @author fuhao
	 * @date 2022/2/8 14:45
	 **/
	@SysDict(dictType = Constant.IDATTRS)
	@Column(name = "identify")
	private String identify;


}