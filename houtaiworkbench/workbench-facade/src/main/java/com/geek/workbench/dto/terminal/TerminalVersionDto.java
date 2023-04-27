package com.geek.workbench.dto.terminal;

import com.fosung.framework.common.config.AppConstants;
import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @version V1.0
 * @Description：终端版本号控制Dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TerminalVersionDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 终端版本号
	 */
	private String terminalVersion ;

	/**
	 * 项目id
	 */
	private Long projectId;
   /**
	 * 终端id
	 */
	private Long terminalId ;
   /**
	 * 终端版本号（纯数字）
	 */
	private String terminalVersionNum ;
   /**
	 * 版本说明
	 */
	private String remark ;
   /**
	 * 更新范围
	 */
	private String upgradeRange ;
	/**
	 * 开始时间
	 */
	@DateTimeFormat(pattern = AppConstants.DATE_TIME_PATTERN)
	private Date startTime;

	/**
	 * 结束时间
	 */
	@DateTimeFormat(pattern = AppConstants.DATE_TIME_PATTERN)
	private Date endTime;

	/**
	 * 状态
	 */
	private String status;


}