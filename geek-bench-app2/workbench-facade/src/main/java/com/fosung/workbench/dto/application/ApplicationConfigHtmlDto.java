package com.fosung.workbench.dto.application;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import com.fosung.workbench.dict.PublishType;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @version V1.0
 * @Description：h5页面高级配置Dto
 */
@Data
public class ApplicationConfigHtmlDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 发布方式
	 */
	@Enumerated(EnumType.STRING)
	private PublishType publishType;
   /**
	 * 前端url
	 */
	private String frontUrl ;
   /**
	 * 安装包地址
	 */
	private String appPackagePath ;
   /**
	 * 版本号
	 */
	private String versionNum ;
   /**
	 * 应用id
	 */
	private String appId ;
   /**
	 * 下载地址
	 */
	private String downloadPath ;
   /**
	 * 应用版本主键
	 */
	private String appVersionId ;

	private String userName;
   /**
	 * 是否删除
	 */
	private Boolean del ;

}