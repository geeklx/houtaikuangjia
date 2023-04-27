package com.geek.workbench.dto.terminal;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @version V1.0
 * @Description：终端发布记录表Dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TerminalUpdateLogDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 发布内容
	 */
	private String updateContent ;

	/**
	 * 终端版本id
	 */
	private Long terminalVersionId;

}