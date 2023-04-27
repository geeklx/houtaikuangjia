package com.geek.workbench.entity.config;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 描述:  认证配置表实体对象
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Entity
@Table(name="config_certification")
@Setter
@Getter
public class ConfigCertification extends AppJpaBaseEntity {

	/**
	 * 认证地址
	 */
	@Column(name="authorized_addr")
	@NotBlank(message = "认证地址不能为空")
	@Size(min = 0, max = 255, message = "认证地址长度不能超过255个字符")
	@URL
	private String authorizedAddr ;


	/**
	 * 用户注册地址
	 */
	@Column(name="registered_addr")
	@NotBlank(message = "用户注册地址不能为空")
	@Size(min = 0, max = 255, message = "用户注册地址长度不能超过255个字符")
	@URL
	private String registeredAddr ;


	/**
	 * 忘记密码地址
	 */
	@Column(name="forgot_pwd_addr")
	@NotBlank(message = "忘记密码地址不能为空")
	@Size(min = 0, max = 255, message = "忘记密码地址长度不能超过255个字符")
	@URL
	private String forgotPwdAddr ;


	/**
	 * 刷新token地址
	 */
	@Column(name="refresh_token_addr")
	@NotBlank(message = "刷新token地址不能为空")
	@Size(min = 0, max = 255, message = "刷新token地址长度不能超过255个字符")
	@URL
	private String refreshTokenAddr ;


	/**
	 * 退出登录地址
	 */
	@Column(name="exit_addr")
	@NotBlank(message = "退出登录地址不能为空")
	@Size(min = 0, max = 255, message = "退出登录地址长度不能超过255个字符")
	@URL
	private String exitAddr ;


	/**
	 * 验证码地址
	 */
	@Column(name="verification_code_addr")
	@NotBlank(message = "短信验证码地址不能为空")
	@Size(min = 0, max = 255, message = "短信验证码地址长度不能超过255个字符")
	@URL
	private String verificationCodeAddr ;


	/**
	 * 是否删除
	 */
	@Column(name="del")
	private Boolean del = Boolean.FALSE;


	/**
	 * 验证图片地址
	 */
	@Size(min = 0, max = 255, message = "图形验证码地址长度不能超过255个字符")
	@Column(name="verification_img_addr")
	private String verificationImgAddr ;


	/**
	 * 认证名称
	 */
	@NotBlank(message = "认证名称不能为空")
	@Size(min = 0, max = 32, message = "认证名称长度不能超过32个字符")
	@Column(name="authorized_name")
	private String authorizedName ;

	/**
	 * 认证签名AK
	 */
	@NotBlank(message = "认证签名AK不能为空")
	@Size(min = 0, max = 255, message = "认证签名AK不能超过255个字符")
	@Column(name="authorized_ak")
	private String authorizedAk;

	/**
	 * 认证签名SK
	 */
	@NotBlank(message = "认证签名SK不能为空")
	@Size(min = 0, max = 255, message = "认证签名SK不能超过255个字符")
	@Column(name="authorized_sk")
	private String authorizedSk;


}