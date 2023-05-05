package com.fosung.workbench.entity.application;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * 描述:  应用配置信息基本信息
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@MappedSuperclass
@Getter
@Setter
public class ApplicationBaseConfigEntity extends AppJpaBaseEntity {

    /**
     * 是否删除
     */
    @Column
    private Boolean del = Boolean.FALSE ;

    /**
     * 应用id
     */
    @Column(name="app_id")
    @NotNull(message = "应用主键不能为空")
    private Long appId ;

    /**
     * 版本号
     */
    @Column(name="version_num")
    private Integer versionNum ;

    /**
     * 版本名称
     */
    @Column(name="version_name")
    private String versionName ;

    /**
     * 描述:  版本大小
     * @createDate: 2021/10/21 15:27
     * @author: gaojian
     */
    @Transient
    private String versionSize;

    /**
     * 描述:  包名
     * @createDate: 2021/10/21 15:27
     * @author: gaojian
     */
    @Transient
    private String packageName;

    /**
     * 描述:  启动类名
     * @createDate: 2021/10/21 15:27
     * @author: gaojian
     */
    @Transient
    private String startName;

    /**
     * 描述:  启动参数
     * @createDate: 2021/10/21 15:27
     * @author: gaojian
     */
    @Transient
    private String startParams;

    /**
     * 描述:  文件名称
     * @createDate: 2021/10/21 15:27
     * @author: gaojian
     */
    @Transient
    private String fileName;

    /**
     * 描述:  版本说明
     * @createDate: 2021/10/21 15:27
     * @author: gaojian
     */
    @Column(name="remark")
    private String remark;

}
