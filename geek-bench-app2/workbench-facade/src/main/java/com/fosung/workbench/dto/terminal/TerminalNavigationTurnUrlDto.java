package com.fosung.workbench.dto.terminal;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import com.fosung.workbench.dict.AssociationType;
import lombok.Data;

/**
 * 描述:  终端导航跳转地址表Dto
 * @createDate: 2021/11/05 15:06
 * @author: gaojian
 * @version V1.0
 */
@Data
public class TerminalNavigationTurnUrlDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 终端主键
	 */
	private Long terminalId ;
   /**
	 * 导航跳转地址
	 */
	private String navigationTurnUrl ;
   /**
	 * 关联类型
	 */
	private AssociationType associationType ;
   /**
	 * 跳转名称
	 */
	private String turnName ;

}