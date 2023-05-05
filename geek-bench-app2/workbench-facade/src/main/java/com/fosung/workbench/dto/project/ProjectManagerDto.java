package com.fosung.workbench.dto.project;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;

/**
 * 描述:  项目管理员Dto
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Data
public class ProjectManagerDto extends AppBasePageParam {

	/**
	 * id
	 */
   private  Long id;
	
   /**
	* 用户主键
	*/
   private String userId ;

   /**
	* 用户电话
	*/
   private String userPhone ;

   /**
	* 用户名
	*/
   private String userName ;

   /**
	* 项目主键
	*/
   private Long projectId ;

}