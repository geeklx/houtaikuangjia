package com.geek.workbench.dto.application;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @version V1.0
 * @Description：ios高级配置Dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ApplicationConfigIosDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 应用id
	 */
	private String appId ;
   /**
	 * AppStore ID
	 */
	private String appStoreId ;
   /**
	 * 版本号
	 */
	private String versionNum ;
   /**
	 * 调起应用
	 */
	private String startApp ;
   /**
	 * Bundle ID
	 */
	private String bundleId ;
   /**
	 * IOS URL Schemes
	 */
	private String iosUrlScemes ;
   /**
	 * 应用版本id
	 */
	private String appVersionId ;
   /**
	 * 是否删除
	 */
	private String del ;

}