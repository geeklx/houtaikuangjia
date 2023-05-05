package com.fosung.workbench.dto.application;
import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;

/**
 * @version V1.0
 * @Description：应用审核Dto
 */
@Data
public class ApplicationExamineDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 所属应用
	 */
	private String appId ;
   /**
	 * 所属版本
	 */
	private String versonId ;
   /**
	 * 审核状态
	 */
	private String examineStatus ;
   /**
	 * 版本描述
	 */
	private String remark ;
   /**
	 * 审核人
	 */
	private String examineName ;
   /**
	 * 审核时间
	 */
	private String examineTime ;
   /**
	 * 审核类型 app，版本
	 */
	private String examineType ;
   /**
	 * 提交人
	 */
	private String submitterName ;
   /**
	 * 驳回原因/通过描述
	 */
	private String examineReason ;

}