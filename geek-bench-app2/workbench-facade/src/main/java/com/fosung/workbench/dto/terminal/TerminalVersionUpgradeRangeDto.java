package com.fosung.workbench.dto.terminal;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;

/**
 * @version V1.0
 * @Description：终端版本与发布记录关系表Dto
 */
@Data
public class TerminalVersionUpgradeRangeDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 终端版本id
	 */
	private String terminalVersionId ;
   /**
	 * 记录表id
	 */
	private String logId ;

}