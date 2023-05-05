package com.fosung.system.support.system.entity.sys;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.config.AppProperties;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;

/**
 * 用户与岗位关联表实体对象
 */
@Entity
@Table(name="sys_user_post")
@Setter
@Getter
public class SysUserPostEntity extends AppJpaBaseEntity    {


	/**
	 * 用户id
	 */
	@Column(name="user_id",nullable = false)
	private Long userId ;


	/**
	 * 岗位id
	 */
	@Column(name="post_id",nullable = false)
	private Long postId ;

	@Transient
	private String postName;

	/**
	 * 系统id
	 */
	@Column(name="project_id",nullable = false)
	private Long projectId ;

	public SysUserPostEntity(Long userId, Long postId, Long projectId) {
		this.userId = userId;
		this.postId = postId;
		this.projectId = projectId;
	}

	public SysUserPostEntity() {
	}
}