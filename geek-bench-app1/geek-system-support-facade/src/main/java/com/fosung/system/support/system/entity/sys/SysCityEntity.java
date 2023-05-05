package com.fosung.system.support.system.entity.sys;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 描述：
 *
 * @author 付昊
 * @date 2022/1/14 11:11
 */
@Entity
@Table(name="cities")
@Setter
@Getter
public class SysCityEntity extends AppJpaBaseEntity {

    @Column(name = "c_name")
    private String cityName;

    @Column(name = "c_pinyin")
    private String cPinyin;

    @Column(name = "c_code")
    private String cityCode;

    @Column(name = "c_province")
    private String cityProvince;


}
