package com.fosung.workbench.dto.application;
import com.fosung.framework.common.config.AppConstants;
import com.fosung.framework.common.dto.params.AppBasePageParam;
import com.fosung.workbench.dict.TerminalType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;


/**
 * @version V1.0
 * @Description：应用版本信息Dto
 */
@Data
public class ApplicationVersionDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 版本号
	 */
	private String versionNum ;

   /**
	 * 是否兼容老版本
	 */
	private Boolean compatibleOldVersion ;

   /**
	 * 版本说明
	 */
	private String remark ;

   /**
	 * 应用id
	 */
	private String appId ;

	/**
	 * 应用类型
	 */
	@Enumerated(EnumType.STRING)
	private TerminalType appType;

	/**
	 * 应用类型字符串
	 */
	private String appTypeStr;

   /**
	 * 审核状态
	 */
	private String auditStatus ;

   /**
	 * 审核说明
	 */
	private String auditRemark ;

   /**
	 * 创建人名称
	 */
	private String createUserName ;

   /**
	 * 下载量
	 */
	private Integer downloadNum ;

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
	 * 版本名称
	 */
	private String versionName ;

	/**
	 * 应用名称
	 */
	private String appName ;
}