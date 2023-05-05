package com.fosung.system.support.system.entity.sys;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.system.support.system.dict.AppType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


/**
 *
 * 类的描述
 * 
 * @author liuke
 * @date  2021/12/13 9:29
 * @version 
*/
@Entity
@Table(name = "sys_app_terminal")
@Setter
@Getter
public class AuthClientEntity extends AppJpaBaseEntity {

    /**
     * 终端名称
     */
    @Column(name = "name")
    private String name ;

    /**
     * 认证协议
     */
    @Column(name = "auth_name")
    private String authName;

    @Column(name = "code")
    private String code;

    @Column(name = "type")
    private String type;

    /**
     * 访问地址
     */
    @Column(name = "url")
    private String url ;

    /**
     * 资源id
     */
    @Column(name = "client_id")
    private String clientId ;

    /**
     * 密钥
     */
    @Column(name = "client_secret")
    private String clientSecret ;

    /**
     * 授权类型
     */
    @Column(name = "authorized_grant_types")
    private String authorizedGrantTypes = "authorization_code,password,refresh_token,client_credentials" ;

    /**
     * 回调地址
     */
    @Column(name = "web_server_redirect_uri")
    private String webServerRedirectUri ;

    /**
     * token有效期
     */
    @Column(name = "access_token_validity")
    private Integer accessTokenValidity = 3600 ;

    /**
     * 刷新token 有效期
     */
    @Column(name = "refresh_token_validity")
    private Integer refreshTokenValidity = 36000 ;

    /**
     * 允许认证
     */
    @Column(name = "autoapprove")
    private String autoApprove = "true" ;

    /**
     * 范围
     */
    @Column(name = "scope")
    private String scope = "login";

    /**
     * 授权资源
     */
    @Column(name = "authorities")
    private String authorities ;

    /**
     * 其它信息
     */
    @Column(name = "additional_information")
    private String additionalInformation ;

    /**
     * 图标
     */
    @Column(name = "icon")
    private String icon;

    /**
     * 部署环境
     */
    @Column(name = "environment")
    private String environment;

    /**
     * 项目编码
     */
    @Column(name = "project_code")
    private String projectCode;

    /**
     * 项目id
     */
    @Column(name = "project_id")
    private Long projectId;

    /**
     * 项目名称
     */
    @Column(name = "project_name")
    private String projectName;

    /**
     *项目应用id
     */
    @Column(name = "project_app_id")
    private Long projectAppId;

    @Transient
    private String appName;

    @Column(name = "app_id")
    private Long appId;

    @Transient
    private String categoryName;

    @Transient
    private String categoryCode;

    @Transient
    private Integer num;

    @Transient
    @Enumerated(EnumType.STRING)
    private AppType appType;
}
