package com.geek.workbench.entity.terminal;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;
import com.geek.workbench.dict.ConfigType;
import com.geek.workbench.dict.StatusType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @Description 终端对接第三方配置
 * @Author gaojian
 * @Date 2022/2/28 15:41
 * @Version V1.0
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name="terminal_third_party_config")
public class TerminalThirdPartyConfigEntity extends AppJpaBaseEntity implements AppJpaSoftDelEntity {

    /**
     * 是否删除
     */
    @Column
    private Boolean del = Boolean.FALSE ;

    /**
     * 终端ID
     */
    @Column(name = "terminal_id")
    @NotNull(message = "终端主键信息不能为空！")
    private Long terminalId;

    /**
     * 第三方配置类型
     */
    @Column(name="config_type")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "配置类型信息不能为空！")
    private ConfigType configType;

    /**
     * 终端配置信息
     */
    @Column(name = "config_info")
    private String configInfo;

    /**
     * 状态信息
     */
    @Column(name="status_type")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "状态类型信息不能为空！")
    private StatusType statusType;

}
