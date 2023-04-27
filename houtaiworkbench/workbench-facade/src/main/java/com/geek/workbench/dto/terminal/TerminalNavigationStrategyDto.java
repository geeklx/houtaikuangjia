package com.geek.workbench.dto.terminal;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 描述:  终端导航策略Dto
 * @createDate: 2021/11/05 15:06
 * @author: gaojian
 * @version V1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TerminalNavigationStrategyDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 策略名称
	 */
	private String strategyName ;
   /**
	 * 底部导航id
	 */
	private Long navigationBtmId ;
   /**
	 * 策略地域
	 */
	private String strategyArea ;
   /**
	 * 策略地址
	 */
	private String strategyUrl ;
   /**
	 * 终端id
	 */
	private Long terminalId ;
   /**
	 * 是否删除
	 */
	private Boolean del ;

}