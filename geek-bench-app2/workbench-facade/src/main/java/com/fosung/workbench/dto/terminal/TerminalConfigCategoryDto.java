package com.fosung.workbench.dto.terminal;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;

/**
 * @version V1.0
 * @Description：工作台应用分类Dto
 */
@Data
public class TerminalConfigCategoryDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 分类编码
	 */
	private String code ;
   /**
	 * 分类名称
	 */
	private String name ;
   /**
	 * 状态
	 */
	private Boolean status ;
   /**
	 * 排序
	 */
	private String num ;
   /**
	 * 分类类型
	 */
	private String type ;
   /**
	 * 图标
	 */
	private String logoUrl ;

	/**
	 * 终端id
	 */
	private Long terminalId;

	private String remark;

	/**
	 * 地域
	 */
	private String area;
	/**
	 * 底部导航
	 */
	private String navigationBtmId;
}