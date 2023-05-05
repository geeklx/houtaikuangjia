package com.fosung.workbench.dto.terminal;

import com.fosung.framework.common.config.AppConstants;
import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @version V1.0
 * @Description：终端基本信息Dto
 */
@Data
public class TerminalBasicDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 项目id
	 */
	private Long projectId ;
   /**
	 * 终端名称
	 */
	private String terminalName ;
   /**
	 * 终端类型
	 */
	private String terminalType ;
   /**
	 * 终端编码
	 */
	private String terminalCode ;
   /**
	 * 部署网络
	 */
	private String deployNetwork ;
   /**
	 * 终端版本号
	 */
	private String terminalVersion ;
   /**
	 * 备注说明
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

	private List<Long> projectList;
}