package com.geek.system.support.system.entity.sys;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.geek.system.support.system.dict.OptLogType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 描述：
 *
 * @author 付昊
 * @date 2022/1/13 8:57
 */
@Entity
@Table(name="sys_log_manage")
@Setter
@Getter
public class SysLogManageEntity extends AppJpaBaseEntity {

    /** 操作模块 */
    @Column(name="opt_module")
    private String optModule;

    /** 功能接口 */
    @Column(name="opt_name")
    private String optName;

    /** 业务类型 */
    @Column(name="opt_type")
    @Enumerated(EnumType.STRING)
    private OptLogType optType;

    /** 类型值 */
    @Column(name="opt_value")
    private String optValue;

    /** 请求方法 */
    @Column(name="method")
    private String method;

    /** 请求方式 */
    @Column(name="request_method")
    private String requestMethod;

    /** 操作人员 */
    @Column(name="oper_name")
    private String operName;

    /** 请求url */
    @Column(name="oper_url")
    private String operUrl;

    /** 操作地址 */
    @Column(name="oper_ip")
    private String operIp;

    /** 操作地点 */
    @Column(name="oper_location")
    private String operLocation;

    /** 请求参数 */
    @Column(name="oper_param",columnDefinition="TEXT")
    private String operParam;

    /** 返回参数 */
    @Column(name="json_result",columnDefinition="TEXT")
    private String jsonResult;

    /** 操作状态 */
    @Column(name="status")
    private Boolean status;

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "org_name")
    private String orgName;

    @Column(name = "project_id")
    private Long projectId;

    /** 操作时间 */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @Column(name="oper_time")
//    private Date operTime;
}
