package com.geek.system.support.system.dto.sys;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @version V1.0
 * @Description：应用系统分类标签Dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysApplicationCategoryDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 分类名称
	 */
	private String categoryName ;
   /**
	 * 分类类型
	 */
	private String categoryType ;
   /**
	 * 排序
	 */
	private Integer num ;

}