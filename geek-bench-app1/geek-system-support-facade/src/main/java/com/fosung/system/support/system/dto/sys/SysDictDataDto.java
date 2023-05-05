package com.fosung.system.support.system.dto.sys;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;

/**
 * @version V1.0
 * @Description：字典数据Dto
 */
@Data
public class SysDictDataDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 排序
	 */
	private String num ;
   /**
	 * 字典标签
	 */
	private String dictLabel ;
   /**
	 * 字典键值
	 */
	private String dictValue ;
   /**
	 * 字典类型
	 */
	private String dictType ;
   /**
	 * 是否字典默认值
	 */
	private Boolean dictDefault ;
   /**
	 * 状态
	 */
	private String status ;
   /**
	 * 备注
	 */
	private String remark ;
   /**
	 * 系统id
	 */
	private String appId ;

	/**
	 * 项目id
	 */
	private String projectId ;

}