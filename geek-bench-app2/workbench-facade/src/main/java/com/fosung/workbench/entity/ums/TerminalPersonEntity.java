package com.fosung.workbench.entity.ums;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @Description
 * @Author gaojian
 * @Date 2022/2/22 16:16
 * @Version V1.0
 */
@Entity
@Table(name="terminal_person")
@Data
public class TerminalPersonEntity extends AppJpaBaseEntity implements AppJpaSoftDelEntity {

    /**
     * 是否删除
     */
    @Column
    private Boolean del = Boolean.FALSE ;

    /**
     * 终端id
     */
    @Column(name="terminal_id")
    @NotNull(message = "终端主键不能为空")
    private Long terminalId ;

    /**
     * 用户id
     */
    @Column(name="user_id")
    @NotNull(message = "用户主键不能为空")
    private String userId ;

    /**
     * 用户名称
     */
    @Column(name="user_name")
    private String userName ;
}
