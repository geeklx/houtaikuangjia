package com.geek.system.pbs.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class CloudUserDto {

	// 手机号
	private String telephone;

	// 性别 WOMAN-女  MAN-男
	private String sex;

	// 身份证号hash
	private String hash;

	// 身份证号
	private String identifyId;

	// 身份证和姓名hash
	private String nameCardHash;

	// 授权码
	private String authorizationCode;

	// 真实姓名
	private String realName;

	// 组织id
	private String orgId;

	// 组织name
	private String orgName;


}
