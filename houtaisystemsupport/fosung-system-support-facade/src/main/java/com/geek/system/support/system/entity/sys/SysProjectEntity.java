package com.geek.system.support.system.entity.sys;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;
import java.util.Map;


@Entity
@Table(name = "sys_project")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysProjectEntity extends AppJpaBaseEntity implements AppJpaSoftDelEntity{

    /**
     * 删除标志
     */
    @Column(name = "del")
    private Boolean del = Boolean.FALSE;

    /**
     * 项目名称
     */
    @Column(name = "project_name",nullable = false)
    private String projectName;

    /**
     * 项目描述
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 项目责任人
     */
    @Column(name = "project_manager")
    private String projectManager;

    /**
     * 项目编码
     */
    @Column(name = "project_code")
    private String projectCode;

    /**
     * 描述: 状态
     * @author fuhao
     * @date 2021/11/25 10:19
     **/
//    @Column(name = "status")
//    @Enumerated(EnumType.STRING)
//    private ProjectStatus status;

    /**
     * 描述: 是否存在关联应用
     * @author fuhao
     * @date 2021/12/3 9:54
     **/
    @Column(name = "exist_apps")
    private Boolean existApps;

    /**
     * 是否校验灯塔用户
     *
     **/
    @Column(name = "check_dt_user")
    private Boolean checkDtUser = false;

    /**
     * 描述: 项目绑定应用id
     * @author fuhao
     * @date 2021/12/2 15:19
     **/
    @Transient
    private List<SysProjectApp> apps;

    /**
     * 描述: 项目绑定应用信息
     * @author fuhao
     * @date 2021/12/2 15:19
     **/
    @Transient
    private List<Map<String, Object>> bindApps;

    /**
     * 项目配置信息
     */
    @Transient
    private SysProjectConfigEntity sysProjectConfigEntity;

}
