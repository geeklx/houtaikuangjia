package com.fosung.system.support.system.dto.sys;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;

/**
 * @version V1.0
 * @Description：应用系统分类标签Dto
 */
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