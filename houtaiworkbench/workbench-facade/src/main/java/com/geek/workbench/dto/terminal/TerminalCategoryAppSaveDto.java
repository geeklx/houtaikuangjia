package com.geek.workbench.dto.terminal;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import com.geek.workbench.entity.terminal.TerminalCategoryAppEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @version V1.0
 * @Description：终端分类应用信息表Dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TerminalCategoryAppSaveDto extends AppBasePageParam {
	

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

	/**
	 * 描述:禁用排序
	 * @author fuhao
	 * @date 2021/11/8 13:56
	 **/
	private Boolean disabledNum;

	/**
	 * 描述:  集合
	 * @createDate: 2021/11/5 20:22
	 * @author: gaojian
	 */
	private List<TerminalCategoryAppEntity> list;
}