package com.geek.workbench.dto.microcoder;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @version V1.0
 * @Description：协议配置信息Dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TerminalConfigAgreementDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 协议名称
	 */
	private String agreementName ;
   /**
	 * 协议类型
	 */
	private String agreementType ;
   /**
	 * 协议内容
	 */
	private String agreementContent ;
	/**
	 * 协议url
	 */
	private String agreementUrl;
   /**
	 * 协议id
	 */
	private String terminalId ;

}