package com.geek.workbench.entity.common;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Description 地市实体类表
 * @Author gaojian
 * @Date 2021/10/30 15:56
 * @Version V1.0
 */
@Entity
@Table(name="cities")
@Setter
@Getter
public class CitiesEntity extends AppJpaBaseEntity {

    private String c_name;

    private String c_pinyin;

    private String c_code;

    private String c_province;
}
