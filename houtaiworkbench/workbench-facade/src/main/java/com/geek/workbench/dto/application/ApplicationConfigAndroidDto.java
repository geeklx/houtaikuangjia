package com.geek.workbench.dto.application;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @version V1.0
 * @Description：安卓高级页面配置Dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ApplicationConfigAndroidDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 应用包存储路径
	 */
	private String appPackagePath ;
   /**
	 * 版本号
	 */
	private String versionNumber ;
   /**
	 * 调起应用
	 */
	private String startApp ;
   /**
	 * Bundle ID
	 */
	private String bundleId ;
   /**
	 * 下载地址
	 */
	private String downloadPath ;
   /**
	 * 应用id
	 */
	private String appId ;
   /**
	 * AppStore ID 
	 */
	private String appStoreId ;
   /**
	 * 是否删除
	 */
	private Boolean del ;
   /**
	 * 应用版本id
	 */
	private String appVersionId ;

}