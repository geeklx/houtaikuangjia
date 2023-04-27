package com.geek.system.pbs.mq.facade;

import lombok.Data;

@Data
public class PersonnelMessage {

	/**
	 * ID
	 */
	private String id;

	/**
	 * 真实姓名
	 */
	private String realName;

	/**
	 * 注册手机号
	 */
	private String telephone;

	/**
	 * 激活状态，AUTH:已认证, NOT_AUTH:未认证
	 */
	private String authStatus;

	/**
	 * 政治面貌
	 */
	private String political;

	/**
	 * 来源
	 */
	private String businesSource;

	/**
	 * 组织id
	 */
	private String orgId;

	/**
	 * 组织code
	 */
	private String orgCode;

	/**
	 * 组织名称
	 */
	private String orgName;

	/**
	 * 职务名称
	 */
	private String positionInfoName;

	/**
	 * 职务同步区分所属还是任职组织，0：所属，1：任职
	 */
	private String type;

	/**
	 * 身份证号
	 */
	private String idCard;

	/**
	 * 头像
	 */
	private String headUrl;

	/**
	 * 性别
	 */
	private String sex;

	/**
	 * 民族
	 */
	private String nation;

	/**
	 * 学历
	 */
	private String education;

	/**
	 * 联系方式
	 */
	private String contactDetails;

	/**
	 * 身份
	 */
	private String identity;

	/**
	 * 职位
	 */
	private String post;

	/**
	 * 用户状态，VALID：启用, INVALID：禁用
	 */
	private String userStatus;

	/**
	 * 操作类型 addUser-新增用户 updateUser-更新用户  deleteUser-删除用户 active-激活用户  updatePhone-修改注册手机号
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
