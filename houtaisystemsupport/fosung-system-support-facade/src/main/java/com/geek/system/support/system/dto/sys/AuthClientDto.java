package com.geek.system.support.system.dto.sys;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
public class AuthClientDto extends AppBasePageParam {
    /**
     * 终端名称
     */
    private String name ;

    /**
     * 资源id
     */
    private String clientId ;

    /**
     * 授权类型
     */
    private String authorizedGrantTypes = "authorization_code" ;

    /**
     * 允许认证
     */
    private String autoApprove = "true" ;

    /**
     * 项目编码
     */
    private String projectCode;

    /**
     * 项目id
     */
    private Long projectId;

    private String appName;


}
