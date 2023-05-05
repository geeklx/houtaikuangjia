package com.fosung.workbench.dto.config;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;

/**
 * 描述:  认证配置表dto
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Data
public class ConfigCertificationDto extends AppBasePageParam {

    /**
     * id
     */
    private  Long id;

    /**
     * 认证地址
     */
	private String authorizedAddr ;

    /**
     * 用户注册地址
     */
	private String registeredAddr ;

    /**
     * 忘记密码地址
     */
	private String forgotPwdAddr ;

    /**
     * 刷新token地址
     */
	private String refreshTokenAddr ;

    /**
     * 退出登录地址
     */
	private String exitAddr ;

    /**
     * 手机验证码地址
     */
	private String verificationCodeAddr ;

    /**
     * 图片验证码地址
     */
	private String verificationImgAddr ;

    /**
     * 认证名称
     */
	private String authorizedName ;

    /**
     * 认证签名AK
     */
    private String authorizedAk;

    /**
     * 认证签名SK
     */
    private String authorizedSk;

}