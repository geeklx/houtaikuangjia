package com.geek.workbench.dto.common;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description 操作日志表Dto
 * @Author gaojian
 * @Date 2021/10/15 11:27
 * @Version V1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ManageOptLogDto extends AppBasePageParam {

   /**
 	* id
    */
   private  Long id;
	
   /**
	* 业务主键
	*/
   private String optBusinessId ;

   /**
	* 操作模块
	*/
   private String optModule ;

   /**
	* 操作名称
	*/
   private String optName ;

   /**
	* 操作类型
	*/
   private String optType ;

   /**
	* 请求ip
	*/
   private String reqIp ;

   /**
	* 请求方法
	*/
   private String reqMethod ;

   /**
	* 请求uri
	*/
   private String reqUri ;

   /**
	* 请求参数
	*/
   private String reqParam ;

   /**
	* 返回参数
	*/
   private String resParam ;

   /**
	* 是否删除
	*/
	private Boolean del = Boolean.FALSE;

}