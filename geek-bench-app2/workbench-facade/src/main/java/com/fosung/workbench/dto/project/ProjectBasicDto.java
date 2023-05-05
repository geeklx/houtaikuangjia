package com.fosung.workbench.dto.project;

import com.fosung.framework.common.config.AppConstants;
import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 描述:  项目基本信息Dto
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Data
public class ProjectBasicDto extends AppBasePageParam {

	/**
	 * id
	 */
   private  Long id;
	
   /**
	* 项目名称
	*/
   private String projectName ;

   /**
	* 项目描述
	*/
   private String remark ;

   /**
	* 项目状态
	*/
   private String status ;

   /**
	* 项目负责人
	*/
   private String person ;

   /**
	* 项目编码
	*/
   private String projectCode ;

   /**
	* 是否删除
	*/
   private Boolean del ;

	/**
	 * 开始时间
	 */
	@DateTimeFormat(pattern = AppConstants.DATE_PATTERN)
	private Date startTime;

	/**
	 * 结束时间
	 */
	@DateTimeFormat(pattern = AppConstants.DATE_PATTERN)
	private Date endTime;

}