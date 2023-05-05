package com.fosung.workbench.dto.terminal;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;

/**
 * @version V1.0
 * @Description：终端分类应用信息表Dto
 */
@Data
public class TerminalCategoryAppDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 终端id
	 */
	private Long terminalId ;
   /**
	 * 分类编码
	 */
	private String categoryCode ;
   /**
	 * 应用配置id
	 */
	private Long appConfigId ;
   /**
	 * 分类类型（常用、其他）
	 */
	private String categoryType ;

	private String area;

	/***
	 * 描述: 等于应用版本id
	 * @param null
	 * @return
	 * @author fuhao
	 * @date 2021/11/4 11:27
	 **/
	private String configId;

}