package com.geek.workbench.dto.application;
import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @version V1.0
 * @Description：应用系统分类标签Dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ApplicationCategoryDto extends AppBasePageParam {
	
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
   /**
	 * 是否删除
	 */
	private Boolean del ;

}