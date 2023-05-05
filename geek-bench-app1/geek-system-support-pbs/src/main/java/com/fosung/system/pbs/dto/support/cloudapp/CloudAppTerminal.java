package com.fosung.system.pbs.dto.support.cloudapp;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class CloudAppTerminal implements Serializable {

    private static final long serialVersionUID = 1L ;

    // 应用终端id
    private Long id ;

    // 应用终端所属应用id
    private Long appId ;

    // 终端名称
    private String name ;

    // 终端编码
    private String code ;

    // 认证id
    private String clientId ;

    // 认证密钥
    private String clientSecret;

    // 范围
    private String scope ;

    // 授权类型
    private String authorizedGrantTypes;

    // token有效期
    private Integer accessTokenValidity;

    // 回调地址
    private String webServerRedirectUri ;

    // 刷新token 有效期
    private Integer refreshTokenValidity ;

    // 允许认证
    private String autoApprove ;

    // 终端图标
    private String icon ;

    // 终端url
    private String url ;

    //应用于（类型）
    private String type;
}
