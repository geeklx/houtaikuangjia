package com.geek.workbench.entity.sys;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author lihuiming
 * @className DictEntity
 * @description: 字典信息
 * @date 2022/5/614:17
 */
@Entity
@Table(name="sys_dict")
@Setter
@Getter
public class SysDictEntity extends AppJpaBaseEntity implements AppJpaSoftDelEntity {
    /**
     * 是否删除
     */
    @Column
    private Boolean del = Boolean.FALSE ;

    /**
     * 字典名称
     */
    @Column(name="dict_name" , nullable = false)
    private String dictName;
    /**
     * 字典名称
     */
    @Column(name="dict_code" , nullable = false)
    private String dictCode;

    /**
     * 字典值
     */
    @Column(name="dict_value" )
    private String dictValue;

    /**
     * 字典值
     */
    @Column(name="remark"  )
    private String remark;

    /**
     * 排序
     */
    @Column(name="num"  )
    private Integer num;
    /**
     * 字典值
     */
    @Column(name="status"  )
    private String status;

    /**
     * 父id
     */
    @Column(name="parent_id"  )
    private Long parentId;
}
