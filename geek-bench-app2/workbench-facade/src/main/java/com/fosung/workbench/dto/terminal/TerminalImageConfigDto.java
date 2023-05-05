package com.fosung.workbench.dto.terminal;

import com.fosung.framework.common.config.AppConstants;
import com.fosung.framework.common.dto.params.AppBasePageParam;
import com.fosung.workbench.dict.ImageType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

/**
 * 描述:  广告页、引导页配置表Dto
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Data
public class TerminalImageConfigDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 图片地址
	 */
	private String imgUrl ;
   /**
	 * 链接地址
	 */
   private String linkUrl ;
   /**
	 * 开始时间
	 */
   @DateTimeFormat(pattern = AppConstants.DATE_PATTERN)
   private Date startTime ;

   /**
	 * 结束时间
	 */
   @DateTimeFormat(pattern = AppConstants.DATE_PATTERN)
   private Date endTime ;

   /**
	 * 顺序
	 */
	private Integer num ;
   /**
	 * 类型（advert、guide）
	 */
   @Enumerated(EnumType.STRING)
	private ImageType imageType ;
   /**
	 * 终端id
	 */
	private Long terminalId ;

}