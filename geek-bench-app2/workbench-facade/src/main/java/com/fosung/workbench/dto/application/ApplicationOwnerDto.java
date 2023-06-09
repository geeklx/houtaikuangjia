package com.fosung.workbench.dto.application;
import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;

/**
 * @version V1.0
 * @Description：我的应用Dto
 */
@Data
public class ApplicationOwnerDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 用户id
	 */
	private String userId ;
   /**
	 * app主键
	 */
	private Integer appId ;
   /**
	 * 终端id
	 */
	private Integer terminalId ;
   /**
	 * 是否删除
	 */
	private Boolean del ;

}