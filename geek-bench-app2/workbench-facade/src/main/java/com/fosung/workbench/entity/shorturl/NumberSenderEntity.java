package com.fosung.workbench.entity.shorturl;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Description 短 URL 映射实体类
 * @Author gaojian
 * @Date 2022年1月17日11:11:07
 * @Version V1.0
 */
@Entity
@Table(name="number_sender")
@Setter
@Getter
public class NumberSenderEntity extends AppJpaBaseEntity {

    @NotBlank(message = "发号器KEY")
    @Column(name="number_sender_key" , nullable = false)
    private String numberSenderKey;

    @NotNull(message = "当前号")
    @Column(name="number" , nullable = false)
    private Long number;
}
