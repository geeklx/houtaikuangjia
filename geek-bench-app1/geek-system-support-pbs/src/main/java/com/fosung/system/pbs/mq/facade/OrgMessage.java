package com.fosung.system.pbs.mq.facade;

import lombok.Data;

@Data
public class OrgMessage {

	/**
	 * ID
	 */
	private String id;

	/**
	 * 组织名称
	 */
	private String name;

	/**
	 * 父级ID
	 */
	private String parentId;

	/**
	 * 排序
	 */
	private String sort;

	/**
	 * 党组织名称
	 */
	private String orgName;

	/**
	 * 组织code
	 */
	private String orgCode;

	/**
	 * 层级
	 */
	private Integer level;

	/**
	 * 层级编码
	 */
	private String levelType;

	/**
	 * 党组织id
	 */
	private String outCode;

	/**
	 * 操作类型 addOrg-新增组织 updateOrg-更新组织  deleteOrg-删除组织
	 */
	private String operateType;

	/**
	 * 对应工单服务生产者消息记录id
	 */
	private Long uniqueId;

	/**
	 * 消息创建时间
	 */
	private Long bornTimestamp;
}
