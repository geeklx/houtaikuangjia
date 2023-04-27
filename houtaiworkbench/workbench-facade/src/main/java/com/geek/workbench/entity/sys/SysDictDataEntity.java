package com.geek.workbench.entity.sys;

import com.geek.workbench.dict.DictStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;

/**
 * 字典数据实体对象
 */
@Entity
@Table(name="sys_dict_data")
@Setter
@Getter
public class SysDictDataEntity extends AppJpaBaseEntity  {


	/**
	 * 排序
	 */
	@Column(name="num" )
	private Integer num ;


	/**
	 * 字典标签
	 */
	@Column(name="dict_label" ,nullable = false)
	private String dictLabel ;


	/**
	 * 字典键值
	 */
	@Column(name="dict_value" ,nullable = false)
	private String dictValue ;


	/**
	 * 字典类型
	 */
	@Column(name="dict_type" , nullable = false)
	private String dictType ;


	/**
	 * 是否字典默认值
	 */
	@Column(name="dict_default")
	private Boolean dictDefault ;


	/**
	 * 状态
	 */
	@Column(name="status" ,nullable = false)
	@Enumerated(EnumType.STRING)
	private DictStatus status = DictStatus.normal;


	/**
	 * 备注
	 */
	@Column(name="remark")
	private String remark ;


	/**
	 * 系统id
	 */
	@Column(name="project_id",nullable = false)
	private Long projectId ;

	/**
	 * 描述: 字典项名称（肥城项目）
	 * @author fuhao
	 * @date 2022/2/21 9:52
	 **/
	@Transient
	private String name;

	/**
	 * 描述: 字典项编码（肥城项目）
	 * @author fuhao
	 * @date 2022/2/21 9:52
	 **/
	@Transient
	private String code;


}