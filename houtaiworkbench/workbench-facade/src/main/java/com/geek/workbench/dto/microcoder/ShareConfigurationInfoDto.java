package com.geek.workbench.dto.microcoder;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @version V1.0
 * @Description：分享页面配置Dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ShareConfigurationInfoDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 终端id
	 */
	private String terminalId ;
   /**
	 * 客户端logo
	 */
	private String clientLogo ;
   /**
	 * 底部logo
	 */
	private String bottomLogo ;
   /**
	 * 安卓下载地址
	 */
	private String androidDownloadAddress ;
   /**
	 * ios下载地址
	 */
	private String iosDownloadAddress ;
   /**
	 * Android URL Schemes
	 */
	private String androidUrlSchemes ;
   /**
	 * Android hosts
	 */
	private String androidHosts ;
   /**
	 * AppStore appid
	 */
	private String appstoreAppid ;
   /**
	 * IOS URL Schemes
	 */
	private String iosUrlSchemes ;
   /**
	 * 分享链接域名
	 */
	private String shareUrlDomainName ;
   /**
	 * 分享渠道
	 */
	private String shareChannel ;

}