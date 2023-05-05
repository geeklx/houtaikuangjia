package com.fosung.system.support.system.entity.sys;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Map;

@Entity
@Table(name = "sys_resource_app")
@Data
public class SysResourceAppEntity extends AppJpaBaseEntity implements AppJpaSoftDelEntity{

    /**
     * 删除标识
     */
    @Column(name = "del")
    private Boolean del;

    /**
     *应用id
     */
    @Column(name = "app_id")
    private Long appId;

    /**
     * 资源路径
     */
    @Column(name = "resource_url")
    private String resourceUrl;

    /**
     * 查询参数预留
     */
    @Column(name = "param1")
    private String param1;

    /**
     * 查询参数预留
     */
    @Column(name = "param2")
    private String param2;

    /**
     * 查询参数预留
     */
    @Column(name = "param3")
    private String param3;


    /**
     * 查询参数预留
     */
    @Column(name = "param4")
    private String param4;


    /**
     * 查询参数预留
     */
    @Column(name = "param5")
    private String param5;


    /**
     * 资源名称
     */
    @Column(name = "resource_name")
    private String resourceName;
}
